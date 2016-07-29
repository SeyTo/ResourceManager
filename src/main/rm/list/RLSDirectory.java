package rm.list;

import rm.list.exceptions.InvalidRLSException;

/**
 * Created by rj on 7/27/2016.
 */
public class RLSDirectory extends RLS2 {

    protected RLSDirectory(String syntax) throws InvalidRLSException {
        super(syntax);
    }

    @Override
    public String[] getValue(ResourceList res) throws Exception{
        return new String[] {getSyntax()};
    }
}
