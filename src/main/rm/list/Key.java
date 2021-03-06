package rm.list;

import java.util.Arrays;
import java.util.Vector;

/**
 * <p>Keys are generated using given tags. The tags are best ordered from (left to right) most significant tag
 * to least significant tag. This helps searching algorithm to first look into most significant tag then the later
 * ones. </p>
 * <p>All tags are & must be unique and lower cases. Use ResourceList.Utils().generateTag() to generate unique tags.</p>
 * <p>RLS containing '*' are/should be preceded with 'p?' e.g 'p?-tag-tag'. Since these type of resources usually points towards other </p>
 * <p>Keys also have pre tags that help to quickly determine the type. Which is one of the following :</p>
 * <ol>
 *     <li>d? -> directory</li>
 *     <li>f? -> files</li>
 *     <li>h? -> http</li>
 *     <li>p? -> files pointer</li>
 *     <li>pp? -> files & directory pointer</li>
 * </ol>
 * Created by rj on 7/17/2016.
 */
public class Key implements Comparable {

    private final String[] tags;

    /**
     * <p>Creates a Key using given tags. The tags are ordered like this : </p>
     * <p>Most Significant Tag - Tag - Tag - .... - Least Significant Tag</p>
     * <p>This ordering helps the sorting and searching of tag based key.</p>
     * @param tags
     */
    public Key(RLSType type, String... tags) {
        String[] newTag = new String[tags.length + 1];
        newTag[0] = type.getTag();
        for (int i = 0; i < tags.length; i++) {
            newTag[i+1] = tags[i].toLowerCase();
        }
        this.tags = newTag;
    }

    public Key(RLSType type, String tags) {
        this(type, tags.split("-"));
    }

    private Key(String tags) {
        this.tags = tags.split("-");
    }

    /**
     * <p>Assuming that pre-tag RLSType has already been added in the beginning. Use this constructor if
     * the tags were generated by other constructors of this class.</p>
     * @param tags
     */
    public static Key newKey(String tags) {
        return new Key(tags);
    }

    @Deprecated
    public Key(Vector<String> tags) {
        String[] _stringTag = new String[tags.size()];
        tags.copyInto(_stringTag);
        this.tags = _stringTag;
    }


    public String[] getTags() {
        return tags;
    }

    public static RLSType getType(String pre_key) {
        return RLSType.getType(pre_key.split("-")[0]);
    }

    //_____________________________________________________________________//

    @Override
    public int compareTo(Object o) {
        Key _key = (Key)o;
        int _compare = 0;
        for (int i = 0; i < tags.length; i++) {
            try {
                if ((_compare = tags[i].compareTo(_key.getTags()[i])) == 0) {
                    if (i == tags.length - 1 && _key.getTags().length > tags.length) return -1;
                } else {
                    return _compare;
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                return 1;
            }
        }
        return 0;
    }

    //_____________________________________________________________________//

    @Override
    public String toString() {
        String _tem = new String(tags[0]);

        for (int i = 1; i < tags.length; i++) {
            _tem = _tem + "-" + tags[i];
        }

        return _tem;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Key key = (Key) o;

        if (key.toString().equalsIgnoreCase(toString())) return true;

        return false;
    }

    @Override
    public int hashCode() {
        return tags != null ? Arrays.hashCode(tags) : 0;
    }
}
