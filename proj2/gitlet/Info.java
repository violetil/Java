package gitlet;

import java.io.Serializable;
import java.util.*;

/** Information about branches and HEAD pointer, provide some useful methods for
 *  change or get them.
 *
 * @author Violet
 */
public class Info implements Serializable {
    /* The HEAD pointer which always point the current commit. */
    private Commit HEAD;
    /* Current branch. */
    private String currentBranch;
    /* All existed branches. */
    private Map<String, String> branches;

    public Info() {
        branches = new HashMap<>();
    }

    /** Create a new branch with a commit which it will point.
     *
     * @param name - branch name
     * @param commit - branch point commit when it was created
     */
    public void createBranch(String name, Commit commit) {
        branches.put(name, commit.getId());
    }

    /** Remove a branch with given name if it exists.
     *
     * @param name
     */
    public void removeBranch(String name) {
        if (!containBranch(name)) return;
        branches.remove(name);
    }

    /** Make the current branch head point to this commit.
     *
     * @param commit
     */
    public void setCurrentBranchHeadCommit(Commit commit) {
        setBranchHeadCommit(currentBranch, commit);
    }

    /** Make the specific branch head commit point to the commit.
     *
     * @param branchName
     * @param commit
     */
    private void setBranchHeadCommit(String branchName, Commit commit) {
        branches.put(branchName, commit.getId());
    }

    /** Return if branch with that name exists return false otherwise.
     *
     * @param branch
     */
    public boolean containBranch(String branch) {
        return branches.containsKey(branch);
    }

    /** Return the head commit of branch, return null otherwise.
     *
     * @param branch
     */
    public Commit getCommit(String branch) {
        if (!containBranch(branch)) return null;
        String commitID = branches.get(branch);
        return Utils.readObject(Utils.join(Repository.COMMITS, commitID), Commit.class);
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer("=== Branches ===\n*");
        buffer.append(this.getCurrentBranch() + "\n");

        List<String> keys = new ArrayList<>(branches.keySet());
        Collections.sort(keys);

        for (String branch : keys) {
            if (!branch.equals(this.getCurrentBranch())) {
                buffer.append(branch);
                buffer.append("\n");
            }
        }
        return buffer.toString();
    }

    /****************************   Setter and getter methods   ****************************/
    public Commit getHEAD() {
        return HEAD;
    }

    public void setHEAD(Commit HEAD) {
        this.HEAD = HEAD;
    }

    public String getCurrentBranch() {
        return currentBranch;
    }

    public void setCurrentBranch(String currentBranch) {
        this.currentBranch = currentBranch;
    }

    public Map<String, String> getBranches() {
        return branches;
    }

    public void setBranches(Map<String, String> branches) {
        this.branches = branches;
    }
}
