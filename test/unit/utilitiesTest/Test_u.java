package unit.utilitiesTest;

import org.junit.Test;
import rm.u;

/**
 * Created by rj on 7/29/2016.
 */
public class Test_u {

    @Test
    public void copyTest() throws Exception {
        String _fromPath = "D:/temp/";
        String _toPath = "D:/new/";

        u.copy(_fromPath, _toPath);
    }
}
