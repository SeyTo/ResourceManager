package unit;

import static org.junit.Assert.*;
import org.junit.Test;
import rm.list.*;

/**
 * Created by rj on 7/26/2016.
 */
public class Test_RLS2 {

    @Test
    public void typeCastingTest() throws Exception{
        assertTrue(RLS2.getInstance("A:/dir/dir/") instanceof RLSDirectory);
        assertTrue(RLS2.getInstance("A:/dir/") instanceof RLSDirectory);
        assertTrue(RLS2.getInstance("A:/") instanceof RLSDirectory);
        assertTrue(RLS2.getInstance("B:/dir/dir") instanceof RLSFile);
        assertTrue(RLS2.getInstance("B:/dir.txt") instanceof RLSFile);
        assertTrue(RLS2.getInstance("B:/dir/dir/file.file.ext") instanceof RLSFile);
        assertTrue(RLS2.getInstance("C:/dir/dir/*") instanceof RLSFiles);
        assertTrue(RLS2.getInstance("C:/dir/*") instanceof RLSFiles);
        assertTrue(RLS2.getInstance("C:/*") instanceof RLSFiles);
        assertTrue(RLS2.getInstance("D:/dir/dir/**") instanceof RLSFilesNDirs);
        assertTrue(RLS2.getInstance("D:/**") instanceof RLSFilesNDirs);
        assertTrue(RLS2.getInstance("http://anything") instanceof RLSHttp);
        assertTrue(RLS2.getInstance("D:/key-key1?") instanceof RLSKeyPointer);
        assertTrue(RLS2.getInstance("D:/dir/key?") instanceof RLSKeyPointer);
        assertTrue(RLS2.getInstance("D:/key1-key2?") instanceof RLSKeyPointer);
    }

    @Test
    public void getResourceTest() throws Exception {

    }
}
