package gitlet;

/** Represent a gitlet branch object.
 *
 *  A list of a bunch of commits, store the current commit in this branch.
 *
 *  @Author Violet
 */
public class Branch {
    /** HEAD pointer, represent current branch which is invariant. */
    private static String HEAD;
    /** The name of branch which is unique. */
    private String name;
    /** Current commit in this branch. */
    private Commit curCommit;

    /** Initialize this branch. */
    public Branch(Commit cur, String name) {
        if (HEAD == null) {
            HEAD = name;
        }
        this.curCommit = cur;
        this.name = name;
    }
}
