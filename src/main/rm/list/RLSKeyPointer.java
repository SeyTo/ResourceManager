package rm.list;

import rm.list.exceptions.DuplicateKeyException;
import rm.list.exceptions.InvalidRLSException;

/**
 * Created by rj on 7/27/2016.
 */
public class RLSKeyPointer extends RLS2 {

    protected RLSKeyPointer(String syntax) {
        super(syntax);
    }

    /**
     * <p>Returns transformed values using the key. These files, directories and url linked files do not exists.</p>
     * @param res
     * @return
     */
    @Override
    public String[] getValue(ResourceList res) throws Exception{
        String[] _temp = null;
        String _path = syntax.substring(0, syntax.lastIndexOf('/') + 1);
        String _keyName = syntax.substring(syntax.lastIndexOf('/') + 1, syntax.length() - 1);

        RLS2 rls = res.get(Key2.newKey(_keyName));
        if (rls instanceof RLSFile) {
            //get the file name
            //replace the key name with filename
            //copy the file to new place
        } else if (rls instanceof RLSDirectory) {
            //get the directory name
            //replace the key name with dir name
            //copy the directory to the new place
        } else if (rls instanceof RLSFiles) {
            //get the last dir names
            //create new path with that dir name
            //copy all files to the new place
        } else if (rls instanceof RLSFilesNDirs) {
            //get the last dir/file names
            //create new path with that dir and file names
            //copy all files to the new place
        } else if (rls instanceof RLSHttp) {
            //download the given file
            //put file in the _path
            //create path with given name
            //if (fail) then null
        } else if (rls instanceof RLSKeyPointer) {
            try {
                throw new Exception("RLSKeyPointer.getValue()'s keyPointer has been reached. Never should reach here");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return _temp;
    }
}
