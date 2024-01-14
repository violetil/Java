package gitlet;

import java.util.*;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/** Helper methods class, provide methods for Main class.
 * @author Violet
 */
public class Repository {
    /* Current working directory. */
    public static final File CWD = new File(System.getProperty("user.dir"));

    /* Repository directory. */
    public static final File REPOSITORY = Utils.join(CWD, ".gitlet");

    /* Commits directory */
    public static final File COMMITS = Utils.join(REPOSITORY, "commits");

    /* Staging area directory and file. */
    public static final File STAGING_AREA = Utils.join(REPOSITORY, "stagingArea");
    private static final File f_STAGING_AREA = Utils.join(STAGING_AREA, "stagingArea");

    /* Blobs directory. */
    public static final File BLOBS = Utils.join(REPOSITORY, "blobs");

    /* Information directory and file. */
    public static final File INFO = Utils.join(REPOSITORY, "info");
    private static final File f_INFO = Utils.join(INFO, "info");

    /* Instance of Info class. */
    private static Info info = new Info();
    /* Instance of StagingArea. */
    private static StagingArea stagingArea = new StagingArea();

    /** Usage: java gitlet.Main init
     *
     *  Description: Creates a new Gitlet version-control system in the current directory.
     *      .gitlet
     *          | -- commit
     *          | -- blobs
     *          | -- info (file: info)
     *          | -- stagingArea (file: stagingArea)
     */
    public static void init() {
        initialInspect("init");

        /* STEP 1: Create gitlet file system. */
        REPOSITORY.mkdirs();
        COMMITS.mkdir();
        BLOBS.mkdir();
        INFO.mkdirs();
        STAGING_AREA.mkdirs();

        /* STEP 2: Generate initial commit. */
        Commit commit = new Commit("initial commit", null);
        Utils.writeObject(Utils.join(COMMITS, commit.getId()), commit);

        /* STEP 3: Generate 'master' branch and set HEAD and current branch. */
        info.createBranch("master", commit);
        info.setCurrentBranch("master");
        info.setHEAD(commit);

        writePreInfo();
    }

    /** Usage: java gitlet.Main add [filename]
     *
     *  Description: Add a file to staging area, represent this file was staged, prepare
     *  for later commit.
     *
     * @param filename
     */
    public static void add(String filename) {
        initialInspect("add");
        readPreInfo();

        /* Check this file if exists in current work directory. */
        File file = Utils.join(CWD, filename);
        if (!file.exists()) {
            System.out.println("File does not exist.");
            System.exit(0);
        }

        addHelper(file, filename);

        writePreInfo();
    }

    private static void addHelper(File file, String filename) {
        /* STEP 1: Create a blob to store content for this file. */
        Blob blob = new Blob(file);

        /* STEP 2: Check this file version if already exists in staging area. */
        if (stagingArea.additionContainFile(filename, blob)) return;

        /* STEP 3: Check that this file version if already exists in HEAD commit. */
        if (info.getHEAD().containFile(filename, blob)) {
            if (stagingArea.additionContainFile(filename)) {
                stagingArea.rmFileFromAddition(filename);
            }
            return;
        }

        /* STEP 4: Remove from removal area if exists in it. */
        if (stagingArea.removeContainFile(filename)) {
            stagingArea.rmFileFromRemove(filename);
        }

        /* STEP 5: Write the file version into Gitlet repository. */
        Utils.writeObject(Utils.join(BLOBS, blob.getId()), blob);

        /* STEP 6: Stages this file for later commit. */
        stagingArea.addFileToAddition(filename, blob);
    }

    /** Usage: java gitlet.Main commit [message]
     *
     *  Description: Saves a snapshot of tracked files in the current commit and
     *  staging area so they can be restored at a later time, creating a new commit.
     *
     * @param message
     */
    public static void commit(String message) {
        initialInspect("commit");
        readPreInfo();

        /* Check that the message if is nothing. */
        if (message.isBlank() || message.isEmpty()) {
            System.out.println("Please enter a commit message.");
            System.exit(0);
        }

        /* Check that the staging area if is empty, nothing will be changes. */
        if (stagingArea.isClear()) {
            System.out.println("No changes added to the commit.");
            System.exit(0);
        }

        commitHelper(message);

        writePreInfo();
    }

    private static void commitHelper(String message) {
        /* STEP 1: Create a new commit from HEAD commit. */
        Commit commit = new Commit(info.getHEAD());

        /* STEP 2: Update the content of this new commit. */
        commit.setMessage(message); // update message
        commit.setFirstParentID(info.getHEAD().getId()); // update parent ID
        commit.updateTimestamp(); // update timestamp
        commit.updateFiles(stagingArea); // update file references

        /* STEP 3: Recalculate the commit SHA-1 ID and write it into Gitlet repository. */
        commit.refreshID();
        Utils.writeObject(Utils.join(COMMITS, commit.getId()), commit);

        /* STEP 4: Update the HEAD pointer and current branch head pointer. */
        info.setHEAD(commit);
        info.setCurrentBranchHeadCommit(commit);

        /* STEP 5: Clear the staging area. */
        stagingArea.clear();
    }

    /** Usage: java gitlet.Main rm [filename]
     *
     *  Description: Unstage the file if it is currently staged for addition.
     *  If the file is tracked in the current commit, stage it for removal
     *  and remove the file from the working directory if the user has not
     *  already done so.
     *  (do not remove it unless it is tracked in the current commit).
     *
     * @param filename
     */
    public static void rm(String filename) {
        initialInspect("rm");
        readPreInfo();

        /* Check that the file if was staged or was tracked by current commit. */
        if (!stagingArea.additionContainFile(filename) && !info.getHEAD().containFile(filename)) {
            System.out.println("No reason to remove the file.");
            System.exit(0);
        }

        /* STEP 1: Remove file from staging area if it was staged for addition. */
        if (stagingArea.additionContainFile(filename)) {
            stagingArea.rmFileFromAddition(filename);
        }

        /* STEP 2: Stage it for removal if it was tracked by HEAD commit and remove it
                   from current work directory if user has not already done so. */
        if (info.getHEAD().containFile(filename)) {
            stagingArea.addFileToRemove(filename);
            File file = Utils.join(CWD, filename);
            if (file.exists()) file.delete();
        }

        writePreInfo();
    }

    /** Usage: java gitlet.Main log
     *
     *  Description: Starting at the current head commit, display information about
     *  each commit backwards along the commit tree until the initial commit,
     *  following the first parent commit links, ignoring any second parents
     *  found in merge commits.
     */
    public static void log() {
        initialInspect("log");
        readPreInfo();

        Commit commit = info.getHEAD();
        while (commit != null) {
            System.out.println(commit);
            commit = commit.getFirstParent();
        }

        writePreInfo();
    }

    /** Usage: java gitlet.Main global-log
     *
     *  Description: Like log, except displays information about all commits ever made.
     */
    public static void global_log() {
        initialInspect("global-log");
        readPreInfo();

        /* STEP 1: Get the list of all commit SHA-1 IDs. */
        List<String> commitIDs = Utils.plainFilenamesIn(COMMITS);

        /* STEP 2: Display all commit information. */
        for (String commitID : commitIDs) {
            File file = Utils.join(COMMITS, commitID);
            Commit commit = Utils.readObject(file, Commit.class);
            System.out.println(commit);
        }

        writePreInfo();
    }

    /** Usage: java gitlet.Main find [commit message]
     *
     *  Description: Prints out the ids of all commits that have the given
     *  commit message, one per line. If there are multiple such commits,
     *  it prints the ids out on separate lines.
     *
     * @param message
     */
    public static void find(String message) {
        initialInspect("find");
        readPreInfo();

        boolean nothing = true;

        /* STEP 1: Get the list of all commit SHA-1 IDs. */
        List<String> commitIDs = Utils.plainFilenamesIn(COMMITS);

        /* STEP 2: Find and display all commits id which have the specific commit message. */
        for (String commitID : commitIDs) {
            File file = Utils.join(COMMITS, commitID);
            Commit commit = Utils.readObject(file, Commit.class);
            if (message.equals(commit.getMessage())) {
                System.out.println(commit.getId());
                nothing = false;
            }
        }

        /* STEP 3: Display error message if it finds nothing. */
        if (nothing) {
            System.out.println("Found no commit with that message.");
        }

        writePreInfo();
    }

    /** Usage: java gitlet.Main status
     *
     *  Description: Displays what branches currently exist, and marks
     *  the current branch with a *. Also displays what files have
     *  been staged for addition or removal.
     */
    public static void status() {
        initialInspect("status");
        readPreInfo();

        /* STEP 1: Display branches status. */
        branchesStatus(info);

        /* STEP 2: Display staging area status. */
        stagedStatus(stagingArea);

        /* STEP 3: Display current work directory files status. */
        CWDStatus(info, stagingArea);

        writePreInfo();
    }

    /** Display the branches status in command line.
     *
     * @param info
     */
    private static void branchesStatus(Info info) {
        System.out.println(info);
    }

    /** Display the staging area status in command line.
     *
     * @param stagingArea
     */
    private static void stagedStatus(StagingArea stagingArea) {
        System.out.println(stagingArea);
    }

    /** Display the current work directory status in command line.
     *
     * @param info
     * @param stagingArea
     */
    private static void CWDStatus(Info info, StagingArea stagingArea) {
        /* Display Modifications Not Staged files. */
        System.out.println("=== Modifications Not Staged For Commit ===");
        Map<String, String> modifiedFiles = getModifiedFiles(info.getHEAD().getFileMaps(),
                stagingArea.getAddition(), stagingArea.getRemove());

        List<String> filenames = new ArrayList<>(modifiedFiles.keySet());
        Collections.sort(filenames);

        for (String filename : filenames) {
            System.out.println(filename + " " + modifiedFiles.get(filename));
        }

        /* Display untracked files. */
        System.out.println("\n=== Untracked Files ===");
        List<String> untrackedFiles = getUntrackedFiles(info.getHEAD().getFileMaps(),
                stagingArea.getAddition());

        Collections.sort(untrackedFiles);
        for (String file : untrackedFiles) {
            System.out.println(file);
        }
    }

    /** Return files in current work directory which was modified but not
     *  was staged.
     *
     * @param head
     * @param addition
     * @param removal
     * @return A map of filename and file status. (Status: modified, deleted)
     */
    private static Map<String, String> getModifiedFiles(Map<String, Blob> head, Map<String, Blob> addition,
                                         Map<String, Blob> removal) {
        Map<String, String> files = new HashMap<>();

        /* STATUS 1: Tracked in the current commit, changed in working directory, but not staged. */
        /* STATUS 2: Not staged for removal, but tracked in current commit and delete from the working
                     directory. */
        for (String filename : head.keySet()) {
            File file = Utils.join(CWD, filename);
            if (!file.exists()) {
                if (!removal.containsKey(filename) || !addition.get(filename).equals(head.get(filename))) {
                    files.put(filename, "(deleted)");
                }
            } else {
                Blob blob = new Blob(file);
                if (!head.get(filename).equals(blob) &&
                        (!addition.containsKey(filename) || !addition.get(filename).equals(blob))) {
                    files.put(filename, "(modified)");
                }
            }
        }

        /* STATUS 3: Staged for addition, but with different contents than in working directory. */
        /* STATUS 4: Staged for addition, but delete in working directory. */
        for (String filename : addition.keySet()) {
            File file = Utils.join(CWD, filename);
            if (!file.exists()) {
                files.put(filename, "(deleted)");
            } else {
                Blob blob = new Blob(file);
                if (!addition.get(filename).equals(blob)) {
                    files.put(filename, "(modified)");
                }
            }
        }

        return files;
    }

    /** Return the list of untracked files, which is present in the current working directory
     *  but neither staged for addition nor tracked.
     *
     * @return List of untracked files.
     */
    private static List<String> getUntrackedFiles(Map<String, Blob> head, Map<String, Blob> addition) {
        List<String> filenames = Utils.plainFilenamesIn(CWD);
        List<String> res = new ArrayList<>(filenames);

        for (String filename : filenames) {
            File file = Utils.join(CWD, filename);
            Blob blob = new Blob(file);
            if (head.containsKey(filename) || addition.containsKey(filename)) {
                res.remove(filename);
            }
        }

        return res;
    }

    /** Usage: java gitlet.Main checkout -- [filename]
     *
     *  Description: Takes the version of the file as it exists in the head commit
     *  and puts it in the working directory, overwriting the version of the file
     *  that’s already there if there is one. The new version of the file is not staged.
     *
     * @param filename
     */
    public static void checkoutHeadFile(String filename) {
        initialInspect("checkout");
        readPreInfo();

        checkoutFile(info.getHEAD().getId(), filename);

        writePreInfo();
    }

    /** Usage: java gitlet.Main checkout [commit id] -- [filename]
     *
     *  Description: Takes the version of the file as it exists in the commit with
     *  the given id, and puts it in the working directory, overwriting the version
     *  of the file that’s already there if there is one. The new version of the
     *  file is not staged.
     *
     * @param commitID
     * @param filename
     */
    public static void checkoutFile(String commitID, String filename) {
        initialInspect("checkout");
        readPreInfo();

        if (commitID.length() == 6) {
            commitID = findCommitID(commitID);
        }

        /* Error if no commit with the given id exists. */
        if (!containCommit(commitID)) error("No commit with that id exists.");

        /* Error if this file does not exist in the commit. */
        Commit commit = getCommit(commitID);
        if (!commit.containFile(filename)) error("File does not exist in that commit.");

        /* STEP 1: Get the file in the current working directory. */
        File file = Utils.join(CWD, filename);

        /* STEP 2: Overwriting the version of content of this file use the commit version of file. */
        Utils.writeContents(file, commit.getFileContent(filename));

        /* STEP 3: Unstages this file if it staged for addition. */
        if (stagingArea.additionContainFile(filename)) stagingArea.rmFileFromAddition(filename);

        writePreInfo();
    }

    /** Usage: java gitlet.Main checkout [branch name]
     *
     *  Description: Takes all files in the commit at the head of the
     *  given branch, and puts them in the working directory,
     *  overwriting the versions of the files that are already there
     *  if they exist. Also, at the end of this command, the given
     *  branch will now be considered the current branch (HEAD).
     *  Any files that are tracked in the current branch but are
     *  not present in the checked-out branch are deleted.
     *
     * @param branch
     */
    public static void checkoutBranch(String branch) {
        initialInspect("checkout");
        readPreInfo();

        /* Error if no branch with that name exists. */
        if (!info.containBranch(branch)) error("No such branch exists.");

        /* Error if that branch is current branch. */
        if (branch.equals(info.getCurrentBranch())) error("No need to checkout the current branch.");

        /* Error if a working file is untracked in the current branch. */
        List<String> untrackedFiles = getUntrackedFiles(info.getHEAD().getFileMaps(),
                stagingArea.getAddition());
        if (!untrackedFiles.isEmpty()) error("There is an untracked file in the way; delete it, or add and commit it first.");

        /* STEP 1: Restore the files in current working directory. */
        Commit commit = info.getCommit(branch);
        changeCommit(info, stagingArea, commit);

        /* STEP 2: Reset the current branch. */
        info.setCurrentBranch(branch);

        writePreInfo();
    }

    /** Clear current working directory and create all files in given commit into it.
     *  And reset the HEAD commit to the given commit.
     *  And clear staging area.
     *
     * @param commit
     * @param info
     * @param stagingArea
     */
    private static void changeCommit(Info info, StagingArea stagingArea, Commit commit) {
        /* STEP 1: Delete all files in current working directory. */
        clearCWD();

        /* STEP 2: Write all files in head commit with given branch into current working directory. */
        Map<String, Blob> files = commit.getFileMaps();
        for (String filename : files.keySet()) {
            File file = Utils.join(CWD, filename);
            Utils.writeContents(file, files.get(filename).getContent());
        }

        /* STEP 3: Reset the current branch and HEAD commit. */
        info.setHEAD(commit);

        /* STEP 4: Clear staging area. */
        stagingArea.clear();
    }

    /** Usage: java gitlet.Main branch [branch name]
     *
     *  Description: Creates a new branch with the given name, and points it at the
     *  current head commit.
     *
     * @param branch
     */
    public static void branch(String branch) {
        initialInspect("branch");
        readPreInfo();

        /* Error if a branch with the given name already exists. */
        if (info.containBranch(branch)) error("A branch with that name already exists.");

        /* STEP 1: Create a branch with given name, and point it at the current HEAD commit. */
        info.createBranch(branch, info.getHEAD());

        writePreInfo();
    }

    /** Usage: java gitlet.Main rm-branch [branch name]
     *
     *  Description: Deletes the branch with the given name.
     *
     * @param branch
     */
    public static void rm_branch(String branch) {
        initialInspect("rm-branch");
        readPreInfo();

        /* Error if a branch with given name does not exist. */
        if (!info.containBranch(branch)) error("A branch with that name does not exist.");

        /* Error if try to remove the branch you're current on. */
        if (branch.equals(info.getCurrentBranch())) error("Cannot remove the current branch.");

        /* STEP 1: Remove the branch with given name. */
        info.removeBranch(branch);

        writePreInfo();
    }

    /** Usage: java gitlet.Main reset [commit id]
     *
     *  Description: Checks out all the files tracked by the given commit.
     *
     * @param commitID
     */
    public static void reset(String commitID) {
        initialInspect("reset");
        readPreInfo();

        if (commitID.length() == 6) {
            commitID = findCommitID(commitID);
        }

        /* Error if no commit with the given id exists. */
        if (!containCommit(commitID)) error("No commit with that id exists.");

        /* Error if a working file is untracked in the current branch. */
        List<String> untrackedFiles = getUntrackedFiles(info.getHEAD().getFileMaps(),
                stagingArea.getAddition());
        if (!untrackedFiles.isEmpty()) error("There is an untracked file in the way; delete it, or add and commit it first.");

        /* STEP 1: Restore all files in the commit into current working directory. */
        Commit commit = getCommit(commitID);
        changeCommit(info, stagingArea, commit);

        /* STEP 2: Reset the current branch head commit to this commit. */
        info.setCurrentBranchHeadCommit(commit);

        writePreInfo();
    }

    /** Usage: java gitlet.Main merge [branch name]
     *
     *  Description: Merges files from the given branch into the current branch.
     *
     * @param branch
     */
    public static void merge(String branch) {
        initialInspect("merge");
        readPreInfo();

        /* Error if the staging area is not clear. */
        if (!stagingArea.isClear()) error("You have uncommitted changes.");

        /* Error if the branch with given name does not exist. */
        if (!info.containBranch(branch)) error("A branch with that name does not exist.");

        /* Error if the branch with given name is current branch. */
        if (branch.equals(info.getCurrentBranch())) error("Cannot merge a branch with itself.");

        /* Error if a working file is untracked in the current branch. */
        List<String> untrackedFiles = getUntrackedFiles(info.getHEAD().getFileMaps(),
                stagingArea.getAddition());
        if (!untrackedFiles.isEmpty()) error("There is an untracked file in the way; delete it, or add and commit it first.");

        /* STEP 1: Find the split point. */
        Commit splitPoint = getSplitPoint(info, info.getCurrentBranch(), branch);

        /* STEP 2: Complete merge if split point is the commit of the given branch. */
        if (splitPoint == null) error("No Split Point");
        if (splitPoint.equals(info.getCommit(branch))) error("Given branch is an ancestor of the current branch.");

        /* STEP 3: Check out the commit of given branch and complete merge. */
        if (splitPoint.equals(info.getCommit(info.getCurrentBranch()))) {
            reset(info.getCommit(branch).getId());
            error("Current branch fast-forwarded.");
        }

        /* STEP 4: Merge two branch otherwise. */
        /* Merge files. */
        mergeHelper(info, stagingArea, info.getCommit(branch), splitPoint);
        /* Generate a merge commit. */
        mergeCommit("Merged " + branch +" into " + info.getCurrentBranch() + ".", info.getCommit(branch).getId());

        writePreInfo();
    }

    private static void mergeCommit(String message, String secondParentID) {
        readPreInfo();

        /* STEP 1: Create a new commit from HEAD commit. */
        Commit commit = new Commit(info.getHEAD());

        /* STEP 2: Update the content of this new commit. */
        commit.setMessage(message); // update message
        commit.setFirstParentID(info.getHEAD().getId()); // update first parent ID
        commit.setSecondParentID(secondParentID); // update second parent ID
        commit.updateTimestamp(); // update timestamp
        commit.updateFiles(stagingArea); // update file references

        /* STEP 3: Recalculate the commit SHA-1 ID and write it into Gitlet repository. */
        commit.refreshID();
        Utils.writeObject(Utils.join(COMMITS, commit.getId()), commit);

        /* STEP 4: Update the HEAD pointer and current branch head pointer. */
        info.setHEAD(commit);
        info.setCurrentBranchHeadCommit(commit);

        /* STEP 5: Clear the staging area. */
        stagingArea.clear();

        writePreInfo();
    }

    private static void mergeHelper(Info info, StagingArea stagingArea, Commit givenCommit, Commit splitPoint) {
        Commit currentCommit = info.getHEAD();

        /* Merge the files. */
        for (String filename : splitPoint.getFileMaps().keySet()) {
            if (splitPoint.getBLob(filename).equals(currentCommit.getBLob(filename))) {
                if (givenCommit.getBLob(filename) == null) {
                    File file = Utils.join(CWD, filename);
                    file.delete();
                    stagingArea.addFileToRemove(filename);
                } else if (!givenCommit.getBLob(filename).equals(splitPoint.getBLob(filename))) {
                    checkoutFile(givenCommit.getId(), filename);
                    stagingArea.addFileToAddition(filename, givenCommit.getBLob(filename));
                }
            } else if (!splitPoint.getBLob(filename).equals(givenCommit.getBLob(filename))) {
                File file = Utils.join(CWD, filename);
                writeConflict(file, currentCommit.getBLob(filename), givenCommit.getBLob(filename));
                Blob blob = new Blob(file);
                stagingArea.addFileToAddition(filename, blob);
            }
        }

        for (String filename : givenCommit.getFileMaps().keySet()) {
            if (splitPoint.getBLob(filename) == null) {
                if (currentCommit.getBLob(filename) == null) {
                    checkoutFile(givenCommit.getId(), filename);
                    stagingArea.addFileToAddition(filename, givenCommit.getBLob(filename));
                } else {
                    File file = Utils.join(CWD, filename);
                    writeConflict(file, currentCommit.getBLob(filename), givenCommit.getBLob(filename));
                    Blob blob = new Blob(file);
                    stagingArea.addFileToAddition(filename, blob);
                }
            }
        }
    }

    private static void writeConflict(File file, Blob headFile, Blob givenFile) {
        String contents = "<<<<<<< HEAD\n";
        contents += headFile == null ? "" : headFile.getContent();
        contents += "=======\n";
        contents += givenFile == null ? "" : givenFile.getContent();
        contents += ">>>>>>>\n";
        Utils.writeContents(file, contents);
        System.out.println("Encountered a merge conflict.");
    }

    /** Return the spilt point for two branch.
     *
     * @param info
     * @param currentBranch
     * @param givenBranch
     * @return Split point.
     */
    private static Commit getSplitPoint(Info info, String currentBranch, String givenBranch) {
        Commit curCommit = info.getCommit(currentBranch);
        Commit givCommit = info.getCommit(givenBranch);

        HashSet<Commit> visitedCommits = new HashSet<>();
        while (curCommit != null) {
            visitedCommits.add(curCommit);
            curCommit = curCommit.getFirstParent();
        }

        while (givCommit != null) {
            if (visitedCommits.contains(givCommit)) {
                return givCommit;
            }
            givCommit = givCommit.getFirstParent();
        }
        return null;
    }



    /**************     Some private and useful methods for this class.     ********************/

    /** Check the current work directory if is a gitlet repository and print
     *  some prompt according to the command.
     *
     *  @param command - which user want to perform.
     */
    private static void initialInspect(String command) {
        if (command.equals("init")) {
            if (REPOSITORY.exists()) {
                System.out.println("A Gitlet version-control system already exists in the current directory.");
                System.exit(0);
            }
        } else {
            if (!REPOSITORY.exists()) {
                System.out.println("Not in an initialized Gitlet directory.");
                System.exit(0);
            }
        }
    }

    /** Call it before perform other logic in a command except 'init' command.
     *  Read the pre-information for work normal of Gitlet system.
     *
     *  info:           Information about HEAD pointer, current branch and all branches.
     *  stagingArea:    Information about staged files, remove files and so on for later commit.
     */
    private static void readPreInfo() {
        if (f_INFO.exists()) info = Utils.readObject(f_INFO, Info.class);
        if (f_STAGING_AREA.exists()) stagingArea = Utils.readObject(f_STAGING_AREA, StagingArea.class);
    }

    /** Call it in the end of a command.
     *  Write the pre-information after update them.
     */
    private static void writePreInfo() {
        Utils.writeObject(f_INFO, info);
        Utils.writeObject(f_STAGING_AREA, stagingArea);
    }

    /** Return true if commit with given id exists otherwise return false.
     *
     * @param commitID
     */
    private static boolean containCommit(String commitID) {
        List<String> commitIDs = Utils.plainFilenamesIn(COMMITS);
        for (String commit : commitIDs) {
            if (commit.equals(commitID)) return true;
        }
        return false;
    }

    /** Return total commit id with given the prefix of id, return empty string otherwise.
     *
     * @param prefix
     * @return Total commit id.
     */
    private static String findCommitID(String prefix) {
        List<String> commitIDs = Utils.plainFilenamesIn(COMMITS);
        for (String commitID : commitIDs) {
            if (prefix.equals(commitID.substring(0, 6))) return commitID;
        }
        return "";
    }

    /** Return the commit with given commit id.
     *
     * @param commitID
     */
    private static Commit getCommit(String commitID) {
        return Utils.readObject(Utils.join(COMMITS, commitID), Commit.class);
    }

    public static void clearCWD() {
        List<String> filenames = Utils.plainFilenamesIn(CWD);
        for (String filename : filenames) {
            File file = Utils.join(CWD, filename);
            file.delete();
        }
    }

    /** Print the error message and exit the program.
     *
     * @param message
     */
    private static void error(String message) {
        System.out.println(message);
        System.exit(0);
    }
}
