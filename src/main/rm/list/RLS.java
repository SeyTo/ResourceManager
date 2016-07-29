package rm.list;

import rm.list.exceptions.DuplicateKeyException;
import rm.list.exceptions.InvalidRLSException;
import rm.u;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * <p>Resource Locator Syntax represents one of 7 types of path defining strings.</p>
 * <ol>
 *     <li><b>"C:/base/"</b> , which represents a directory</li>
 *     <li><b>"C:/base/file.txt"</b> , which represents a file</li>
 *     <li><b>"C:/base/*"</b> , which represents all files only in the directory</li>
 *     <li><b>"C:/base/**"</b>, which represents all files and directories within the directory.</li>
 *     <li><b>"http://www.res.com/blabla.png"</b> , which represents a URL</li>
 *     <li><b>"C:/key-key2-key3?"</b> , which means, copy resource indicated by 'key-key2-key3' to 'C:/'. There cannot be more than 1 keys in this type. </li>
 * </ol>
 * Created by rj on 7/24/2016.
 */
public class RLS {

    private final String syntax;
    private RLSType type;

    public RLS(String syntax) throws InvalidRLSException{
        this.syntax = check(syntax);
    }

    /**
     * <p>Checks if given string matches with one of given type.</p>
     * @param syntax a string to check for matching with given type.
     * @return null if does not match
     * @throws InvalidRLSException if given string does not match any of given types.
     */
    private String check(String syntax) throws InvalidRLSException {
        syntax = syntax.replace('\\', '/');

        if (syntax.startsWith("http:")) {   //type 5
            type = RLSType.http;
        } else {
            syntax = syntax.toLowerCase();
            if (syntax.matches("[a-zA-Z]:/([^/*?]+[/]?)+[?]")) {   //matches C:/key-key2? or similar (type 6)
                type = RLSType.keyPointer;
            } else if (syntax.matches("[a-zA-Z]:/([^/?*]+/)*[*]$")) {    //matches C:/base/* or similar (type 3)
                type = RLSType.files;
            } else if (syntax.matches("[a-zA-Z]:/([^/?*]+/)*[*]{2}$")) {      //matches C:/base/** or similar (type 4)
                type = RLSType.filesNDirs;
            } else if (syntax.matches("[a-zA-Z]:/([^/?*]+[/])*")) {      //matches C:/base/ or similar (type 1)
                type = RLSType.directory;
            } else if (syntax.matches("[a-zA-Z]:(/[^/?*]+)*")) {      //matches C:/base.txt or similar (type 2)
                type = RLSType.file;
            } else throw new InvalidRLSException("Does not match any given expressions. See docs.");
        }

        return syntax;
    }

    /**
     * <p>Returns file path compatible string version of RLS. Converts RLS types to usable paths.</p>
     * <p>RLS containing '*' returns all files within the directory</p>
     * <p>RLS containing '**' returns all files and directory and sub-files and sub-directories.</p>
     * <p>RLS containing tags will NOT <code>ResourceList.Utils().process()</code>. It will simply retrieve the resources denoted by the tags</p>
     * @return
     */
    public String[] get(ResourceList res) {
        /*String[] _temp = null;
        switch (type) {
            case file:
            case http:
            case directory:
                _temp = new String[]{syntax};
                break;
            case files:
                File _fpath = new File(syntax.substring(0, syntax.lastIndexOf('/')));
                if (_fpath.exists()) {
                    Vector<String> _vec = new Vector<>();
                    File[] files = _fpath.listFiles();
                    if (files != null && files.length != 0)
                        for (File file : files) {
                            if (file.isFile()) {
                                _vec.add(file.getPath());
                            }
                        }
                    _vec.trimToSize();
                    if (_vec.size() != 0) {
                        _temp = new String[_vec.size()];
                        _vec.toArray(_temp);
                    }
                }
                break;
            case filesNDirs:
                File _fPath = new File(syntax.substring(0, syntax.lastIndexOf('/')));
                if (_fPath.exists() && _fPath.isDirectory()) {
                    List<String> fileList = new ArrayList<>();
                    u.list(_fPath, fileList);
                    if (fileList.size() != 0) {
                        _temp = new String[fileList.size()];
                        fileList.toArray(_temp);
                    }
                }
                break;
            case keyPointer:
                String _path = syntax.substring(0, syntax.lastIndexOf('/') + 1);
                String _keyName = syntax.substring(syntax.lastIndexOf('/') + 1, syntax.length());

                RLSType keyType = Key.getType(_keyName);
                _temp = res.getValues(Key.newKey(_keyName));
                switch (keyType) {
                    case directory:
                        _temp[0] = _temp[0].substring(_temp[0].length()-1);
                    case file:
                        _temp[0] = _path + _temp[0].substring(_temp[0].lastIndexOf('/') + 1, _temp[0].length());
                        break;
                    case files:
                        for (int i = 0; i < _temp.length; i++) {
                            _temp[i] = _path + _temp[i].substring(_temp[i].lastIndexOf('/') + 1, _temp[i].length());
                        }
                        break;
                    case filesNDirs:
                        for (int i = 0; i < _temp.length; i++) {
                            _temp[i] = _temp[i].substring(_temp[0].length() - 1);
                            _temp[i] = _path + _temp[i].substring(_temp[i].lastIndexOf('/') + 1, _temp[i].length());
                        }
                        break;
                    case keyPointer:
                        try {
                            throw new DuplicateKeyException("A key references another key.");
                        } catch (DuplicateKeyException e) {
                            e.printStackTrace();
                            break;
                        }
                    case http:
                    default:
                }

        }
*/
        return null;
    }

    public RLSType getType() {
        return type;
    }

    public String getSyntax() {
        return syntax;
    }

    /**
     * <p>Returns actual string version of RLS</p>
     * @return
     */
    @Override
    public String toString() {
        return "RLS{}";
    }

}
