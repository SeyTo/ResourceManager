package unit;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;
import rm.list.Key;
import rm.list.RLSType;

/**
 * Created by rj on 7/25/2016.
 */
public class Test_Key {

    @Ignore ("Till something new is added")
    public void testNew() {
        Key key1 = new Key(RLSType.directory, "Tag1", "Tag2", "Tag3");
        String[] tags = key1.getTags();

        assertEquals("Unexpected length", 4, tags.length);
        assertEquals("unexpected pre-tag", RLSType.directory.getTag(), tags[0]);
        assertEquals("unexpected tag", "Tag1", tags[1]);
        assertEquals("unexpected tag", "Tag2", tags[2]);
        assertEquals("unexpected tag", "Tag3", tags[3]);

        //Testing second constructor
        Key key2 = new Key(RLSType.directory, "Tag1-Tag2-Tag3");
        tags = key2.getTags();

        assertEquals("Unexpected length (using second constructor)", 4, tags.length);
        assertEquals("unexpected pre-tag (using second constructor)", RLSType.directory.getTag(), tags[0]);
        assertEquals("unexpected tag (using second constructor)", "Tag1", tags[1]);
        assertEquals("unexpected tag (using second constructor)", "Tag2", tags[2]);
        assertEquals("unexpected tag (using second constructor)", "Tag3", tags[3]);

        assertTrue("Key with similar tag should be equal", key1.equals(key2));

        assertEquals("toString()", "d?-Tag1-Tag2-Tag3",key1.toString());
    }

    @Test
    public void testCompare() {
        Key key1 = new Key(RLSType.directory, "AA", "AA");
        Key key2 = new Key(RLSType.directory, "AB", "AA");

        assertTrue(key1.compareTo(key2) < 0);

        key1 = new Key(RLSType.directory, "AA");
        key2 = new Key(RLSType.directory, "AA", "AA");

        assertTrue(key1.compareTo(key2) < 0);

        key1 = new Key(RLSType.directory, "AB", "AA");
        key2 = new Key(RLSType.directory, "AB");

        assertTrue(key1.compareTo(key2) > 0);

        key1 = new Key(RLSType.directory, "AA");
        key2 = new Key(RLSType.directory, "AA");

        assertTrue(key1.compareTo(key2) == 0);

        key1 = new Key(RLSType.file, "AA", "AA");
        key2 = new Key(RLSType.directory, "AA", "AA");

        assertTrue(key1.compareTo(key2) > 0);
    }

}
