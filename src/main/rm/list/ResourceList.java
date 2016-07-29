package rm.list;

import rm.list.exceptions.DuplicateKeyException;
import rm.list.exceptions.SelfReferencingResourceException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * Created by rj on 7/24/2016.
 */
public class ResourceList extends HashMap<Key2, RLS2>{

    private U u;

    public ResourceList() {
        super();
        u = new U();
    }

    /**
     * <p>Loads resource from given property formatted.</p>
     * @param properties
     */
    public void loadResource(Properties properties) {}

    /**
     * <p>Tests if all files remain in local repository. RLS containing *s will not be up for  </p>
     */
    public void testIntegrity() {}

    public void reiterate() {}

    /**
     * <p>Putting key and RLS through this method will perform one of the following action depending on the type of RLS.</p>
     * <ol>
     *     <li>if type <b>RLSFile</b> then checks if the file exists. if not then IOException is thrown. </li>
     *     <li>if type <b>RLSDirectory</b> then checks if the directory exists. if not then IOException is thrown. </li>
     *     <li>if type <b>RLSHttp</b> then checks if the url exists. if not then IOException is thrown. </li>
     *     <li>if type <b>RLSFiles</b> then checks if the directory exists. if not then IOException is thrown. </li>
     *     <li>if type <b>RLSFilesNDirs</b> then checks if the directory exists. if not then IOException is thrown. </li>
     *     <li>if type <b>RLSKeyPointer</b> then checks if the directory and the key exists. if not then IOException is thrown. If key references
     *     to itself then SelfReferencingResourceException is thrown.</li>
     * </ol>
     * @param key
     * @param value
     * @return
     * @throws SelfReferencingResourceException
     * @throws DuplicateKeyException if
     * @throws IOException
     */
    private RLS2 process(Key2 key, RLS2 value) throws SelfReferencingResourceException, DuplicateKeyException, IOException{

        return null;
    }

    public RLS2 putRes(Key2 key, RLS2 value) throws SelfReferencingResourceException, DuplicateKeyException{
        if (get(key) != null)
            throw new DuplicateKeyException("A similar key already exists.");

        return edit(key, value);
    }

    /**
     * THIS METHOD IS A NO GO. USE putRes(Key, RLS).
     * @param key
     * @param value
     * @return
     */
    @Override
    @Deprecated
    public RLS2 put(Key2 key, RLS2 value) {
        return null;
    }

    /**
     * <p>Allows to override existing key with new value or simply put a new value without any duplicate checking.</p>
     * @param key
     * @param value
     * @return
     */
    public RLS2 edit(Key2 key, RLS2 value) throws SelfReferencingResourceException{
        if (SelfReferencingResourceException.check(key, value))
            throw new SelfReferencingResourceException();
        return super.put(key, value);
    }

    /**
     * <p>Gets RLS in local path or url string format. This also creates files and directories and downloads if given files do not exist.</p>
     * @param key
     * @return
     */
    public String[] getValues(Key2 key) throws Exception{
        return get(key).getValue(this);
    }

    /**
     * <p>Sets logger to send resourceList's operations. Operations such as addition, removal, deletion etc.</p>
     * @param logger
     */
    public void setLogger(Logger logger) {}

    //_____________________________ResourceList's Utils_________________________________//

    public U utils() {
        return u;
    }

    /**
     * <p>Utility class designed to work with this list only.</p>
     */
    public class U {

        /**
         * <p>Processes RLS. Type 6,7 are processed for copying or downloading. The rest are ignored.</p>
         * @param key
         * @return
         */
        public boolean process(Key2 key) {

            return false;
        }

        /**
         * <p>Physically copies files denoted by key to path.</p>
         * @param key
         * @param path
         * @return
         */
        public boolean copy(Key2 key, String path) {
            return false;
        }

        /**
         * <p>Physically moves files denoted by key.</p>
         * @param key
         * @param path
         * @return
         */
        public boolean move(Key2 key, String path) {
            return false;
        }

        /**
         * <p>Generate a unique key for this list. Following the convention </p>
         * @param rls resource locator syntax
         * @param tagStart slash index from which tagging starts. e.g C:/base1/base2. if tagStart = 2 then tag becomes f?-base1-base2
         * @return
         */
        public Key2 generateKey(RLS2 rls, int tagStart) {
            return null;
        }

        /**
         * <p>Creates files or directories or downloads http resource to specified location.</p>
         * @param key
         */
        public void generate(Key2 key) {}

        /**
         * <p>Searches for tag in list of keys in the given resource list.</p>
         */
        public Key2[] search() { return null; }

        /**
         * <p>Sorts the resource list by tags in alphabetical order. Start indexes of pre-tags are stored in variables for faster
         * searching.</p>
         */
        public void sort() {}
        private int directoryStart = 0;
        private int fileStart = 0;
        private int httpStart = 0;
        private int pointerStart = 0;
        private int pointer2Start = 0;
    }
}
