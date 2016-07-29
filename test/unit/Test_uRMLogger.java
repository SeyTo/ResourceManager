package unit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import rm.uRMLogger;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.Assert.*;

public class Test_uRMLogger {

    static uRMLogger ulogger;
    final String fileName = "file.txt";

    @Before
    public void setUp() throws Exception {
        Logger logger = Logger.getLogger("Name");
        logger.setLevel(Level.INFO);
        ulogger = new uRMLogger(logger, fileName);
    }

    @Test
    public void testLog() throws Exception {
        ulogger.log("This is the first message");
        ulogger.log(null);
    }

    @After
    public void fileExists() {
        File file = new File(fileName);
        if (!file.exists()) {
            fail(fileName + " does not exists.");
            file.delete();
        }
    }

}