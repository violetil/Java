package gitlet;

import java.io.File;
import static gitlet.Utils.*;

/** Represents a gitlet repository.
 *
 *  @author Violet
 */
public class Repository {
    /** The current working directory. */
    public static final File CWD = new File(System.getProperty("user.dir"));
    /** The .gitlet directory. */
    public static final File GITLET_DIR = join(CWD, ".gitlet");
    /** The .gitlet/objects directory, stores blobs. */
    public static final File OBJECTS_DIR = join(GITLET_DIR, "objects");
    /** The .gitlet/commits directory, stores commit. */
    public static final File COMMITS_DIR = join(GITLET_DIR, "commits");
    /** The .gitlet/refs directory, stores branches info. */
    public static final File REFS_DIR = join(GITLET_DIR, "refs");



    /** Usage: java gitlet.Main init
     *  Description: Creates a new Gitlet version-control system in the current directory.
     *  This system will automatically start with one commit: a commit that contains no files
     *  and has the commit message initial commit (just like that, with no punctuation).
     *  It will have a single branch: master, which initially points to this initial commit, and master will be the current branch.
     *  The timestamp for this initial commit will be 00:00:00 UTC, Thursday, 1 January 1970
     *  in whatever format you choose for dates (this is called “The (Unix) Epoch”, represented internally by the time 0.)
     *  Since the initial commit in all repositories created by Gitlet will have exactly the same content,
     *  it follows that all repositories will automatically share this commit (they will all have the same UID)
     *  and all commits in all repositories will trace back to it.
     */
    public static void init() {
        if (GITLET_DIR.exists()) {
            System.out.println("A Gitlet version-control system already exists in current directory.");
            System.exit(0);
        }

        // Create .gitlet folder and other necessary folder.
        GITLET_DIR.mkdirs();
        OBJECTS_DIR.mkdirs();
        COMMITS_DIR.mkdirs();
        REFS_DIR.mkdirs();

        // Create initial commit.
        Commit initCommit = new Commit("initial commit", null);

        // Create a master branch.
        Branch master = new Branch(initCommit, "master");

    }
}
