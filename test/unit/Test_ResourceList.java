package unit;

import org.junit.Test;
import rm.list.Key;
import rm.list.RLS;
import rm.list.RLSType;
import rm.list.ResourceList;

/**
 * Created by rj on 7/27/2016.
 */
public class Test_ResourceList {

    @Test
    public void main() throws Exception {
        Key[] keys = new Key[] {
                new Key(RLSType.file, "shader-simple.vertex"),
                new Key(RLSType.keyPointer, "shader-vertex-simple"),
                new Key(RLSType.keyPointer, "self-referencing")
        };

        RLS[] rlss = new RLS[] {
                new RLS("D:/tempRes/Shader/simple.vertex"),
                new RLS("D:/tempShaders/shader-simple.vertex?"),
                new RLS("D:/self-referencing?")
        };

        ResourceList res = new ResourceList();

    }
}
