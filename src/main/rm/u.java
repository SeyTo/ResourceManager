package rm;

import java.io.*;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.*;
import java.util.*;

/**
 * Created by rj on 7/14/2016.
 */
public class u {

    /**
     * <p>Recursively lists all files in the given file directory into the given list. </p>
     * @param file the directory to search within
     * @param list the list where all found files are added
     */
    public static void list(File file, List<String> list) {
        if (file.isDirectory()) {
            list.add(file.getPath().replace('\\', '/') + '/');
            File[] files = file.listFiles();
            if (files != null)
                for (File tem : files) {
                    list(tem, list);
                }
        } else if (file.isFile()) {
            list.add(file.getPath().replace('\\', '/'));
        }
    }

    /**
     * <p>Recursively lists all files in the given file directory into the given list. </p>
     * @param file the directory to search within
     * @param list the list where all found files are added
     */
    public static void listFiles(File file, List<File> list) {
        if (file.isDirectory()) {
            list.add(file);
            File[] files = file.listFiles();
            if (files != null)
                for (File tem : files) {
                    listFiles(tem, list);
                }
        } else if (file.isFile()) {
            list.add(file);
        }
    }

    /**
     * <p>Recursively lists all files in the given file directory into the given list. </p>
     * @param file the directory to search within
     * @param list the list where all found files are added
     */
    @Deprecated
    public static void search(File file, List<File> list) {
        if (file.isDirectory()) {
            list.add(file);
            try {
                for (File tem : file.listFiles()) {
                    search(tem, list);
                }
            } catch (NullPointerException e) {
                list.add(file);
            }

        } else if (file.isFile()) {
            list.add(file);
        }
    }

    /**
     * <p>Splits a path by "/" symbol. Expected type of paths as examples :</p>
     * <p>"C:/Dir1/Dir2/file2.txt" -> "C:", "Dir1", "Dir2", "file2.txt"</p>
     * <p>"C:/Dir1/http://www.website.com/dir1/dir2/file.png" -> "C:", "Dir1", "http://www.website.com", "dir1", "dir2", "file.png", "http://"
     * The last element will help to recognize that this array contains http:// without having to iterate through all.</p>
     * <p>"C:/Dir1/Dir2/" -> "C:", "Dir1", "Dir2", " ". Element with space will mean that the second last is a directory.</p>
     * @param path
     * @return
     */
    public static String[] separateBySlash(String path) {
        String[] _split;
        if (path.contains("http://")) {
            int _httpStr = path.indexOf("http://");
            Vector<String> _combind = new Vector<>();
            _combind.addAll(Arrays.asList(path.substring(0, _httpStr - 1).split("/")));
            _combind.add(path.substring(_httpStr, _httpStr = path.indexOf("/", _httpStr + 7)));
            _combind.addAll(Arrays.asList(path.substring(_httpStr + 1, path.length()).split("/")));
            _combind.add("http://");
            _combind.trimToSize();
            _split = new String[_combind.size()];
            return _combind.toArray(_split);
        } else {
            if (path.lastIndexOf('/') == path.length() - 1) path += " ";
            _split = path.split("/");
            return _split;
        }
    }

    //TODO prototype : accept directories, accept RMZ type url
    @Deprecated
    public static boolean copy(File copyFrom, File copyTo) {
        if (copyFrom.isDirectory()) return false;
        if (copyFrom.toString().startsWith("https://")) {
            return HTTPCopy(copyFrom.toString(), copyTo.getAbsolutePath());
        }
        try {
            copyTo = new File(copyTo.getPath() + getFileName(copyFrom));
            FileReader reader = new FileReader(copyFrom);
            FileWriter writer = new FileWriter(copyTo);
            writer.write(reader.read());
            writer.flush();
            reader.close();
            writer.close();
            return true;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return false;
        }

    }

    /**
     * <p>Copies files, directories to given path. If a directory has multiple files and directory within then
     * all are copied. If just copying a single file or directory then use <code>nio.Files.copy</code></p>
     * @param fromPath the file
     * @param toPath
     * @return
     */
    public static boolean copy(String fromPath, String toPath) throws IOException{
        //TODO
        Path _fromPath = FileSystems.getDefault().getPath(fromPath);
        Path _toPath = FileSystems.getDefault().getPath(toPath);
        if (Files.exists(_fromPath) && Files.exists(_toPath)) {
            boolean _fromPathIsDir = Files.isDirectory(_fromPath);
            boolean _toPathIsDir = Files.isDirectory(_toPath);
            boolean _fromPathIsFile = Files.isRegularFile(_fromPath);
            boolean _toPathIsFile = Files.isRegularFile(_toPath);

            if (_fromPathIsDir && _toPathIsDir) {//from: D:/temp/shader ; to: D:/
                List<File> filesList = new ArrayList<>();
                listFiles(new File(fromPath), filesList);
                fromPath = (fromPath.charAt(fromPath.length()-1) == '/')? fromPath.substring(0, fromPath.length()-1):fromPath ;
                int _startIndex = fromPath.lastIndexOf("/");


                /*from :
                *   D:/temp/shader
                *
                *   D:/temp/shader/fragment
                *   D:/temp/shader/fragment/fragment.txt
                *   D:/temp/shader/simple.vertex.s
                *
                * to :
                *   D:/
                *
                *   D:/shader
                *   D:/shader/fragment
                *   D:/shader/fragment/fragment.txt
                *   D:/shader/simple.vertex.s
                */

                //start copying
                for (File file : filesList) {
                    if (file.isDirectory()) {
                        String _tem = file.getPath().substring(_startIndex);
                        String _create = toPath + _tem;
                        new File(_create).mkdirs();
                    } else {
                        String _tem = file.getPath().substring(_startIndex);
                        String _create = toPath + _tem;
                        new File(_create).createNewFile();
                    }
                }

            } else if (_fromPathIsDir && _toPathIsFile) {
                throw new IOException("Directory as given by \'toPath\' does not exist.");
            } else if (_fromPathIsFile && _toPathIsFile) {

            } else if (_fromPathIsFile && _toPathIsDir) {

            }
        } else
            throw new IOException("File/Directory path does not exist.");
        return false;
    }

    /**
     *
     * @param url
     * @param loc the location to place the file. The file name will be generated automatically.
     * @return
     */
    public static boolean HTTPCopy(String url, String loc) {
        String filename = url.substring(url.lastIndexOf('/') + 1);
        try {
            URL website = new URL(url);
            ReadableByteChannel rbc = Channels.newChannel(website.openStream());
            FileOutputStream fos = new FileOutputStream(loc + filename);
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static String getFileName(File filePath) {
        String path = filePath.getPath();
        int _index = path.lastIndexOf('\\');
        if (_index == -1) _index = path.lastIndexOf('/');
        return path.substring(_index);
    }


    public static void convertToBackSlashes(String... strings) {
        for (int i = 0; i < strings.length; i++) {
            strings[i] = strings[i].replace('\\', '/');
        }
    }

}
