package rm.list;

import rm.list.exceptions.InvalidRLSException;

import java.io.File;
import java.util.Vector;

/**
 * Created by rj on 7/27/2016.
 */
public class RLSFiles extends RLS2 {

    protected RLSFiles(String syntax) throws InvalidRLSException {
        super(syntax);
    }

    /**
     * <p>Returns all files within the directory.</p>
     * @param res
     * @return null if path does not exist, or String[0] or list of files within the given directory.
     */
    @Override
    public String[] getValue(ResourceList res) throws Exception{
        String[] _temp = null;
        File _path = new File(syntax.substring(0, syntax.lastIndexOf('/')));
        if (_path.exists()) {
            Vector<String> _vec = new Vector<>();
            File[] files = _path.listFiles();
            if (files != null && files.length != 0)
                for (File file : files) {
                    if (file.isFile()) {
                        _vec.add(file.getPath().replace('\\', '/'));
                    }
                }
            _vec.trimToSize();

            _temp = new String[_vec.size()];
            _vec.toArray(_temp);

        }
        return _temp;
    }
}
