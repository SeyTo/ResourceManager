package rm;

/**
 * Created by rj on 7/13/2016.
 */
public enum app_ConfigDefault {

    VERSION("0.1"),
    VERSION_NAME("Z"),
    NAME("Resource Manager"),
    CODE_NAME("RMZ"),
    CONFIGFILE_NAME("RMZ_Config.properties"),
    LOGFILE_NAME("RMZ_Log");

    private final String val;

    app_ConfigDefault(String val) {
        this.val = val;
    }

    public String getVal() {
        return val;
    }
}
