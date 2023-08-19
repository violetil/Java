package gitlet;

import java.io.File;
import static gitlet.Utils.*;

/** Represents a gitlet repository.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author Violet
 */
public class Repository {
    /**
     * TODO: add instance variables here.
     *
     * List all instance variables of the Repository class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided two examples for you.
     */

    /** The current working directory. */
    public static final File CWD = new File(System.getProperty("user.dir"));
    /** The .gitlet directory. */
    public static final File GITLET_DIR = join(CWD, ".gitlet");

    /** Usage: java gitlet.Main init
     *
     *  Create a new Gitlet version-control system in current directory.
     *  (Create a folder named .gitlet if is not exists in current directory
     *  to store the metadata)
     *
     *  this system will automatically start with one commit: a commit that contains
     *  no files and has the commit message initial commit
     */
    public static void initHandle() {
        if (GITLET_DIR.exists()) {
            System.out.println("A Gitlet version-control system already exists in current directory.");
        }
        GITLET_DIR.mkdirs();
    }
}
