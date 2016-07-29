package rm.list;

/**
 * Created by rj on 7/26/2016.
 */
public enum RLSType {

    directory("$d"),
    file("$f"),
    http("$h"),
    files("$p"),
    filesNDirs("$pp"),
    keyPointer("$k");

    final String tag;
    RLSType(String tag) {
        this.tag = tag;
    }

    public String getTag() {
            return tag;
        }

    public static RLSType getType(String firstIndex) {
        for (RLSType type : values())
            if (type.getTag().equals(firstIndex))
                return type;
        return null;
    }
}
