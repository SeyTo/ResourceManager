package rm;

import rm.list.Key;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

/**
 * <p>Each loc inserted will be counted as resource and given a resourceID. ExternalResources is like a specialized
 * list with utilities, loggers & watchers.</p>
 * Created by rj on 7/13/2016.
 */
public class ExternalResources {

    private uRMLogger logger;

    /**
     * <p>The location of config file</p>
     */
    String rmConfigPath;

    final private ConcurrentHashMap<Key, String> resources = new ConcurrentHashMap<>();

    /**
     * Create a new ExternalResource list. If RM_config.properties file already exists then it is read.
     * @param location path to store the RM_config.properties file e.g "C:/dir/". To identify default location use null.
     * @param logger to automatically create a logger at default location use null.
     */
    public ExternalResources(String location, Logger logger) {
        this.rmConfigPath = (location != null? location + app_ConfigDefault.CONFIGFILE_NAME.getVal() :
                app_ConfigDefault.CONFIGFILE_NAME.getVal());

        File _rmConfigFile = new File(rmConfigPath);
        if (_rmConfigFile.exists()) {
            try {
                loadResources(_rmConfigFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        setLogger((logger == null? Logger.getLogger(app_ConfigDefault.LOGFILE_NAME.getVal()) : logger),
                    (location == null? app_ConfigDefault.LOGFILE_NAME.getVal() + ".log"
                            : location + app_ConfigDefault.LOGFILE_NAME.getVal() + ".log"));

    }

    public boolean containsKey(Key key) {
        return resources.containsKey(key);
    }

    public boolean containsLocation(String loc) {
        return resources.contains(loc);
    }

    public void addResources(Key[] keys, String[] locs) {
        for (int i = 0; i < keys.length; i++) {
            addResource(keys[i], locs[i]);
        }
    }

    /**
     * <p>Adds to resources without explicitly referring to a key. Usage : </p>
     * <p>ALWAYS USE BACKSLASHES.</p>
     * <p>C:/Base/Base2/uncommon1/uncommon2/file1.txt</p>
     * <p>C:/Base/Base2/uncommon3/uncommon4/file2.txt</p>
     * <p>C:/Base/Base2/uncommon5/uncommon6/file3.txt</p>
     * <p>Then the base path is C:/Base/Base2/ and remaining path is tagpath i.e uncommon1/uncommon2/file1.txt</p>
     * @param basePath
     * @param tagPath path from which tag is generated
     */
    public void addResource(String basePath, String tagPath) {
        addResource(generateKey(tagPath), basePath + tagPath);
    }

    public void addResource(Key key, String loc) {
        resources.put(key, loc);
        logger.logResourceAdded(key, loc);
    }

    /**
     * <p>ALWAYS USE BACKSLASHES.</p>
     * <p>For example : </p>
     * <p>Base path = "C:/Base1/base2/" and Tag Path = "base2/"</p>
     * <p>Then all files(EXCLUDING sub-directories and sub-files) inside base2 will be listed as resource and
     * given a tag name based on Tag Path. i.e. for file "file1.txt" will be given "base2-txt-file1".</p>
     * <p>The base directory is not counted into the resource list. Also, the reason why not all subdirectories
     * are searched into for resource listing is because there is no way to know which directory should and should not
     * be listed into resources.</p>
     * @param basePath the path from which all resources are to be added
     * @param tagPattern the path, beginning from which the tag is generated.
     */
    public void addResourcesWithin(String basePath, String tagPattern) {
        File _base = new File(basePath);
        if (!_base.isDirectory() || !_base.exists()) {
            System.out.println(basePath + " is either not a directory or does not exist.");
            return;
        }

        File[] list = _base.listFiles();
        if (list != null)
        for (File _path : list) {
            if (_path != null && _path.isFile()) {
                addResource(_path.getPath(), tagPattern + _path.getName());
            }
        }
    }

    /**
     *<p>Create new files, folders and downloads resources in specified locations. ALWAYS USE BACKSLASHES.</p>
     * <p> for example <br>
     *     basic = "D:/temp_ExternalResources/";
     *     "folder1/folder2/",
            "folder1/folder1/text.tmp",
            "txt.tmp.tmp/",         //is a directory
            "tmp.txt/tmp.txt",      //create directory and then a file
            "txt.tmp",               //is a file
            "http://www.pics.com/image.png", //downloads at the base with file name image.png
            "folder3/http://www.pics.com/image2.png",    //downloads at folder3
            "folder3/http://www.pics.com/img.png/"      //error
        </p>
     * @param loc
     * @throws IOException
     */
    public void generateResources(final Key[] keys, final String[] loc, boolean includeIntoResource) throws IOException{
        System.out.println("WARNING!! generating folders and files that not exist into system");
        for (int i = 0; i < loc.length; i++) {
            generateResource(keys[i], loc[i], includeIntoResource);
        }
    }

    public void generateResource(final Key key, final String loc, boolean includeIntoResource) throws IOException{
        boolean _ready = false;

        if (loc.toLowerCase().contains("http://")) {
            //Download file
            File _locToDownload = new File(loc.substring(0, loc.indexOf("http:") - 1));
            if (!(_ready = _locToDownload.exists())) {
                if (_locToDownload.mkdirs())
                    _ready = u.HTTPCopy(loc.substring(loc.indexOf("http:"), loc.length())
                                            , _locToDownload.getAbsolutePath());
            } else if (_ready) {
                _ready = u.HTTPCopy(loc.substring(loc.indexOf("http:"), loc.length())
                                        , _locToDownload.getAbsolutePath());
            }
        } else {
            if (loc.trim().charAt(loc.length()-1) == '/') {
                //Create directory
                File _loc = new File(loc);
                if (!(_ready = _loc.exists())) _ready = _loc.mkdirs();
            } else {
                //Create file
                File _loc = new File(loc.substring(0, loc.lastIndexOf("/")));
                if (!(_ready = _loc.exists())) {
                    if (_loc.mkdirs()) {
                        _ready = new File(loc).createNewFile();
                    }
                } else if (_ready) {
                    _ready = new File(loc).createNewFile();
                }
            }
        }

        if (_ready && includeIntoResource) resources.put(key, loc);
    }

    /**
     * loads resources from RMZ_Config.properties file. Override will occur if similar key already is loaded.
     * @param path
     * @throws IOException
     */
    public void loadResources(File path) throws IOException{
        Properties properties = new Properties();
        properties.load(new FileReader(path));

        /*for (Object key : properties.keySet()) {
            resources.put(new Key((String)key), (String)properties.getValues(key));
        }*/
    }

    /**
     * <p>Checks for files and directories according to main config file.</p>
     * @return list of all missing files.
     */
    public Vector<String> testIntegrity() {
        Properties properties = new Properties();
        Vector<String> dntExistList = new Vector<>();
        try {
            properties.load(new FileReader(rmConfigPath));
            for (String _property : properties.stringPropertyNames()) {
                if (!new File((String)properties.get(_property)).exists()) {
                    dntExistList.add(_property);
                }
            }
            dntExistList.trimToSize();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dntExistList;
    }

    /**
     * <p>Checks in all directories and subdirectories of the given main config file and then includes
     * new files(not directories)</p>
     * @param genID
     */
    private Vector<String> reiterate(boolean genID) {
        //Left out for version-Y. See project/features.txt
        return null;
    }

    /**
     * <p>Deletes the original file or directory and removes from the resources list as well.</p>
     * @param key
     * @return
     */
    public boolean deleteRes(Key key) {
        File file = new File(resources.get(key));
        removeFromResources(key);
        if (file.exists()) {
            return file.delete();
        }
        return false;
    }

    public void removeFromResources(Key key) {
        resources.remove(key);
        logger.logResourceRemoved(key, resources.get(key));
    }

    public String removeFromResources(String location) {
        Key _key;
        while (resources.keys().hasMoreElements()) {
            if (resources.get(_key = resources.keys().nextElement()).equals(location)) {
                return resources.remove(_key);
            }
        }
        return null;
    }

    public File getResourceAsFile(Key key) {
        return new File(resources.get(key));
    }

    public String getResourceValue(Key key) {
        return resources.get(key);
    }

    public Vector<String> getAllValues() {
        Vector<String> values = new Vector<>();

        resources.forEach((key, s) -> {
            values.add(s);
        });

        return values;
    }

    public int size() {
        return resources.size();
    }

    private boolean copyResource(Key key, String loc) {
        //Left out for version-Y
        return false;
    }

    private boolean moveRes(Key key, String loc) {
        //Left out for version-Y
        return false;
    }

    /**
     * <p>Publish all resources to RM_Config.xml. This requires that all resources has been added to the main list.</p>
     */
    @Deprecated
    public void flush() throws IOException{
        File file = new File(rmConfigPath);
        System.out.println("Flushing to " + rmConfigPath + " . File will be overwritten.");

        Properties properties = new Properties();
        //Store all the resources
        for (Key key : resources.keySet()) {
            properties.put(key.toString(), resources.get(key));
        }
        properties.store(new FileWriter(file), "File read by " + app_ConfigDefault.NAME.getVal() + "Code Name : " + app_ConfigDefault.CODE_NAME.getVal() +
                "For : " + rmConfigPath);
        logger.logFlush(rmConfigPath);
    }

    public void clear() {
        resources.clear();
    }

    public String getRmConfigPath() {
        return rmConfigPath;
    }

    /**
     * <p>Use this method only if another logger is supposed to be attached</p>
     * @param logur
     * @param patternLoc leave an empty string if location is at the base.
     */
    public void setLogger(Logger logur, String patternLoc) {
        logger = new uRMLogger(logur, patternLoc);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExternalResources resources1 = (ExternalResources) o;

        if (!resources.equals(resources1.resources)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return resources.hashCode();
    }

    //________________________Utilities__________________________//

    //TODO test
    private String u_goBackOneFolder(String path) {
        int i = path.length() - 1;
        do {
            i--;
        } while (!(path.charAt(i) == '/' || path.charAt(i) == '\\'));
        path = (String) path.subSequence(0, ++i);
        return path;
    }

    /**
     * <p>Generates unique key based on the given path. If uniqueness(based on the path) is not possible then 
     * additional number are put at the end of keys. For example : </p>
     * <p> "texture/2k/" becomes "texture-2k" <br> "scripts/chapter1.scr" becomes "scripts-scr-chapter1"
     * <br> "textures/4k/image.png" on duplicate found becomes "textures-4k-png-image-1" <br>
     *      "textures/4k/http://www.textures.com/volantis/image2.png" becomes "textures-4k-www.textures.com-png-image2"</p>
     * @param filePath the path excluding the base path
     * @return transformed key
     */
    public Key generateKey(String filePath) {
        u.convertToBackSlashes(filePath);
        Vector<String> key = new Vector<>();
        boolean _eol = false;
        int _strIndex = 0;
        do {
            int _endIndex = filePath.indexOf('/', _strIndex);
            if (_endIndex == -1) {
                String[] _split = filePath.substring(_strIndex, _endIndex = filePath.length()).split("[.]");
                for (int i = _split.length - 1; i >= 0 ; i--) {
                    key.add(_split[i]);
                }
            } else {
                String _substring = filePath.substring(_strIndex, _endIndex);
                if (_substring.equalsIgnoreCase("http:")) {
                    _substring = filePath.substring(_strIndex, filePath.length());
                    int _slashIndex = filePath.indexOf("//", _strIndex);
                    key.add(filePath.substring(_slashIndex+2, _endIndex = filePath.indexOf("/", _slashIndex + 2)));

                    //_endIndex = (_substring.lastIndexOf('/'));
                } else {
                    key.add(_substring);
                }

                _strIndex = _endIndex+1;
            }
            if (_endIndex >= filePath.length()-1) _eol = true;
        } while (!_eol);

        Key _tempKey = new Key(key);
        int i = 1;
        int lastIndex = key.size();

        while (resources.containsKey(_tempKey)) {
            try {
                key.set(lastIndex, String.valueOf(i));
            } catch (ArrayIndexOutOfBoundsException e) {
                key.add(String.valueOf(i));
            }
            i++;
            _tempKey = new Key(key);
        }

        return _tempKey;
    }

}
