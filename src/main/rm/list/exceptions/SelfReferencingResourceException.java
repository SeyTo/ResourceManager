package rm.list.exceptions;

import rm.list.Key2;
import rm.list.RLS2;
import rm.list.RLSKeyPointer;

/**
 * Created by rj on 7/25/2016.
 */
public class SelfReferencingResourceException extends Exception{

    public SelfReferencingResourceException(String message) {
        super(message);
    }

    public SelfReferencingResourceException() {
        super("The Key referenced is the Key of this RLS. Cyclic reference detected.");
    }

    public static boolean check(Key2 key, RLS2 value) {
        if (value instanceof RLSKeyPointer) {
            String _keyName = value.getSyntax().substring(value.getSyntax().lastIndexOf('/')+1, value.getSyntax().length()-1);
            if (Key2.newKey(_keyName).equals(key)) return true;
        }
        return false;
    }
}
