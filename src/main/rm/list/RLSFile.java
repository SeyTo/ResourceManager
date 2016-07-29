package rm.list;

import rm.list.exceptions.InvalidRLSException;

import java.io.File;
import java.io.IOException;

/**
 * Created by rj on 7/27/2016.
 */
public class RLSFile extends RLS2 {

    protected RLSFile(String syntax) {
        super(syntax);
    }

    private void process() throws IOException{
        File _file = new File(syntax);
        if (! _file.exists()) {
            File _parent = new File(_file.getParent());
            if (!_parent.exists()) {
                if (!_parent.mkdirs()) throw new IOException("Unable to create parent directories");
            }
            if (!_file.createNewFile()) throw new IOException("Unable to create file.");
        } else {
            System.out.println("FIle already exist.");
        }
    }

    @Override
    public String[] getValue(ResourceList res) throws IOException{
        return new String[] {getSyntax()};
    }
}
