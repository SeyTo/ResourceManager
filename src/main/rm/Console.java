package rm;

/**
 * Created by RaJU on 7/13/2016.
 */
public class Console{

    public enum Command {
        get
                ("Gets resource value given by key. e.g RM getValues 1 tag-tag"),
        remove
                ("Removes resource given by key. e.g RM remove 2 tag-tag-tag"),
        reiterate
                ("Adds new resources given by * or ** in the resource identifiers. e.g RM reiterate 2"),
        integrity
                ("Tests if files added to this list exists in local system. e.g java RM integrity 2"),
        ver
                ("Displays version information. e.g RM ver")
        ;

        private final String def;

        Command(String def) {
            this.def = def;
        }

        public String getDef() {
            return def;
        }
    }

    public static void process(String... command) {
        if (command.length == 0 || (command.length == 1 && command[0].isEmpty())) {
            help();
            return;
        }

        Command com = Command.valueOf(command[0]);

        switch (com) {

            case ver:
                version();
            default:
                help();
        }
    }

    public static void help() {
        System.out.println("use one of the following commands : ");
        for (Command command : Command.values()) {
            System.out.println(command.ordinal() + ". " + command.name() + " -> " + command.getDef());
        }
    }

    public static void version() {
        System.out.println("Name : " + app_ConfigDefault.NAME.getVal());
        System.out.println("Code Name : " + app_ConfigDefault.CODE_NAME.getVal());
        System.out.println("Version : " + app_ConfigDefault.VERSION.getVal());
        System.out.println("Version Name : " + app_ConfigDefault.VERSION_NAME.getVal());
    }

}
