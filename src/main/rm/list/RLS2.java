package rm.list;

import rm.list.exceptions.DuplicateKeyException;
import rm.list.exceptions.InvalidRLSException;
import rm.u;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * <p>Resource Locator Syntax represents one of 7 types of path defining strings.</p>
 * <ol>
 *     <li><b>"C:/base/"</b> , which represents a directory</li>
 *     <li><b>"C:/base/file.txt"</b> , which represents a file</li>
 *     <li><b>"C:/base/*"</b> , which represents all files only in the directory</li>
 *     <li><b>"C:/base/**"</b>, which represents all files and directories within the directory.</li>
 *     <li><b>"http://www.res.com/blabla.png"</b> , which represents a URL</li>
 *     <li><b>"C:/key-key2-key3?"</b> , which means, copy resource indicated by 'key-key2-key3' to 'C:/'. There cannot be more than 1 keys in this type. </li>
 * </ol>
 * Created by rj on 7/24/2016.
 */
public abstract class RLS2 {

    protected final String syntax;

    protected RLS2(String syntax) {
        this.syntax = syntax;
    }

    public static RLS2 getInstance(String syntax) throws InvalidRLSException{
        syntax = syntax.replace('\\', '/');

        if (syntax.startsWith("http:") || syntax.startsWith("file:")) {   //type 5
            return new RLSHttp(syntax);
        } else {
            if (syntax.matches("[a-zA-Z]:/([^/*?]+[/]?)+[?]")) {   //matches C:/key-key2? or similar (type 6)
                return new RLSKeyPointer(syntax);
            } else if (syntax.matches("[a-zA-Z]:/([^/?*]+/)*[*]$")) {    //matches C:/base/* or similar (type 3)
                return new RLSFiles(syntax);
            } else if (syntax.matches("[a-zA-Z]:/([^/?*]+/)*[*]{2}$")) {      //matches C:/base/** or similar (type 4)
                return new RLSFilesNDirs(syntax);
            } else if (syntax.matches("[a-zA-Z]:/([^/?*]+[/])*")) {      //matches C:/base/ or similar (type 1)
                return new RLSDirectory(syntax);
            } else if (syntax.matches("[a-zA-Z]:(/[^/?*]+)*")) {      //matches C:/base.txt or similar (type 2)
                return new RLSFile(syntax);
            } else throw new InvalidRLSException("Syntax does not match any given expressions. See docs.");

        }
    }

    /**
     * <p>Returns file path compatible string version of RLS. Converts RLS types to usable paths.</p>
     * <p>RLS containing '*' returns all files within the directory</p>
     * <p>RLS containing '**' returns all files and directory and sub-files and sub-directories.</p>
     * <p>RLS containing tags will NOT <code>ResourceList.Utils().process()</code>. It will simply retrieve the resources denoted by the tags</p>
     * @return
     */
    public abstract String[] getValue(ResourceList res) throws Exception;

    public String getSyntax() {
        return syntax;
    }


}
