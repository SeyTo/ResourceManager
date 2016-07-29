package unit;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import rm.list.*;

import java.io.File;

/**
 * Created by rj on 7/27/2016.
 */
public class Test_ResourceList2 {

    @Before
    public void create() throws Exception {
        new File("D:/temp/models").mkdirs();
        new File("D:/temp/shader").mkdirs();
        new File("D:/temp/shader/simple.vertex.s").createNewFile();
        new File("D:/temp/file.txt").createNewFile();
        new File("D:/temp/file2.txt").createNewFile();
    }

    @Test
    public void basic() throws Exception {
        RLS2 rls1 = RLS2.getInstance("D:/temp/models/");
        Key2 key1 = new Key2("dir");

        RLS2 rls2 = RLS2.getInstance("D:/temp/shader/simple.vertex.s");
        Key2 key2 = new Key2("shader-simple.vertex.s");

        RLS2 rls3 = RLS2.getInstance("D:/temp/*");
        Key2 key3 = new Key2("temp-allfiles");

        RLS2 rls4 = RLS2.getInstance("D:/temp/**");
        Key2 key4 = new Key2("temp-fnd");

        RLS2 rls5 = RLS2.getInstance("D:/temp/shader-simple.vertex.s?");
        Key2 key5 = new Key2("myKey");

        //TODO key not yet tested

        ResourceList list = new ResourceList();

        list.putRes(key1, rls1);
        list.putRes(key2, rls2);
        list.putRes(key3, rls3);
        list.putRes(key4, rls4);
        list.putRes(key5, rls5);

        assertEquals("D:/temp/models/", list.getValues(key1)[0]);
        assertEquals("D:/temp/shader/simple.vertex.s", list.getValues(key2)[0]);
        assertArrayEquals(new String[] {"D:/temp/file.txt", "D:/temp/file2.txt"}, list.getValues(key3));
        assertArrayEquals(new String[] {"D:/temp/file.txt", "D:/temp/file2.txt", "D:/temp/models/",
                                        "D:/temp/shader/", "D:/temp/shader/simple.vertex.s"}, list.getValues(key4));
        //list.getValues(key1);


    }

    @Ignore
    public void destroy() throws Exception {
        new File("D:/temp/models").delete();
        new File("D:/temp/shader").delete();
        new File("D:/temp/shader/simple.vertex.s").delete();
        new File("D:/temp/file.txt").delete();
        new File("D:/temp/file2.txt").delete();
    }
}
