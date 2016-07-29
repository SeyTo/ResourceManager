package rm.list;

import rm.list.exceptions.InvalidRLSException;
import rm.u;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rj on 7/27/2016.
 */
public class RLSFilesNDirs extends RLS2 {

    protected RLSFilesNDirs(String syntax) {
        super(syntax);
    }

    /**D:/temp/**
     *"D:/temp/file.txt",
     * "D:/temp/file2.txt",
     * "D:/temp/models/",
     "D:/temp/shader/simple.vertex.s",
     "D:/temp/shader/"
     * <p>Gets actual files and directory path. Does not create any directory or file unlike type 1 & 2.</p>
     * @param res
     * @return null if path is either a file or does not exists, or String[0] if nothing exists inside it, or list of all files and directories.
     */
    @Override
    public String[] getValue(ResourceList res) throws Exception{
        String[] _temp = null;
        File _fPath = new File(syntax.substring(0, syntax.lastIndexOf('/')));

        if (_fPath.exists() && _fPath.isDirectory()) {
            List<String> fileList = new ArrayList<>();
            u.list(_fPath, fileList);

            if (fileList.size() != 0) {
                fileList.remove(0);
                _temp = new String[fileList.size()];
                fileList.toArray(_temp);
            }

        }

        return _temp;

    }
}
