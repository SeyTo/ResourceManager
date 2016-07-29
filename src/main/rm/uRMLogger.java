package rm;

import rm.list.Key;

import java.util.logging.*;

/**
 * Created by rj on 7/14/2016.
 */
public class uRMLogger {

    private Logger logger;
    private String location;

    /**
     * <p>Assign a logger to put Level.INFO information regarding moving, copying, deleting, downloading of resources. </p>
     * @param logger logger to send output to. Put logger Level.INFO to listen.
     * @param patternLoc pattern as used by logger's handler.
     */
    public uRMLogger(Logger logger, String patternLoc) {
        this.logger = logger;
    }

    public void log(String msg) {
        logger.log(Level.INFO, msg);
    }

    public void logResourceAdded(Key key, String loc) {
        logger.log(Level.INFO, "New resource ADDED :\n Key : " + key + "  &  Location : " + loc);
    }

    public void logResourceRemoved(Key key, String loc) {
        logger.log(Level.INFO, "Resource REMOVED :\n Key : " + key + "   &   Location : " + loc);
    }

    public void logHTTPCopy(Key key, String loc, boolean didSucceeded) {
        logger.log(Level.INFO,
                (didSucceeded)?
                        ("New resource DOWNLOADED : \n Key : " + key + "   &   Location : " + loc) :
                        ("Resource download FAILED : \n Key : " + key + "   &   Location : " + loc)
        );
    }

    public void logFlush(String mainFileName) {
        logger.log(Level.INFO,
                    "Main config file was flushed : " + mainFileName
                );
    }

    public String getLogLocation() {
        return location;
    }

}
