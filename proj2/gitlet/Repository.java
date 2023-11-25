package gitlet;

import java.util.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/** Represents a gitlet repository.
 *
 *  @author Violet
 */
public class Repository {
    /** User current working directory */
    public static final File CWD = new File(System.getProperty("user.dir"));

    /** Gitlet self repository */
    public static final File REPOSITORY = Utils.join(CWD, ".gitlet");

    /** Gitlet commits directory */
    public static final File COMMITS = Utils.join(REPOSITORY, "commits");

    /** Gitlet staging area */
    public static final File STAGING_AREA = Utils.join(REPOSITORY, "stagingArea");
    private static final File f_STAGING_AREA = Utils.join(STAGING_AREA, "stagingArea");

    /** Gitlet blobs */
    public static final File BLOBS = Utils.join(REPOSITORY, "blobs");

    /** Gitlet info area */
    public static final File INFO = Utils.join(REPOSITORY, "info");
    private static final File f_INFO = Utils.join(INFO, "info");

    private static Info info = new Info();

    private static StagingArea stagingArea = new StagingArea();

    private static void GetPreInfo() {
        if (f_INFO.exists()) info = Utils.readObject(f_INFO, Info.class);
        if (f_STAGING_AREA.exists()) stagingArea = Utils.readObject(f_STAGING_AREA, StagingArea.class);
    }

    /** Usage: java gitlet.Main init */
    public static void init() throws IOException {
        if (IsInit()) {
            System.out.println("A Gitlet version-control system already exists in the current directory.");
            System.exit(0);
        }

        REPOSITORY.mkdirs();
        COMMITS.mkdir();
        STAGING_AREA.mkdirs();
        INFO.mkdirs();
        BLOBS.mkdir();

        Commit commit = new Commit("initial commit", null);

        info.CreateBranch("master", commit);
        info.SetCurrentBranch("master");
        info.SetHEAD(commit);

        Utils.writeObject(Utils.join(COMMITS, commit.GetId()), commit);
        Utils.writeObject(Utils.join(INFO, "info"), info);
    }

    /** Usage: java gitlet.Main add filename */
    public static void add(String fileName) {
        if (!IsInit()) {
            System.exit(0);
        }

        GetPreInfo();

        File file = Utils.join(CWD, fileName);
        if (!file.exists()) {
            System.out.println("This file doesn't exists.");
            System.exit(0);
        }

        Blob blob = new Blob(file);
        if (stagingArea.ExistsInAddition(fileName, blob)) return;
        if (info.HEAD.ExistsFile(fileName, blob)) return;

        Utils.writeObject(Utils.join(BLOBS, blob.GetId()), blob);

        stagingArea.AddAdditionFile(fileName, blob);
        Utils.writeObject(f_STAGING_AREA, stagingArea);
    }

    /** Usage: java gitlet.Main commit [commit message] */
    public static void commit(String cMessage) {
        if (!IsInit()) {
            System.exit(0);
        }

        GetPreInfo();

        Commit commit = new Commit(info.HEAD);
        commit.SetMessage(cMessage);
        commit.SetFirParent(info.HEAD);
        stagingArea.PlusAdditionFiles(commit);
        stagingArea.PlusRemoveFiles(commit);

        Utils.writeObject(Utils.join(COMMITS, commit.UpdateId()), commit);

        info.SetHEAD(commit);
        info.SetBranch(info.GetCurrentBranch(), commit);
        Utils.writeObject(f_INFO, info);

        stagingArea.clear();
        Utils.writeObject(f_STAGING_AREA, stagingArea);
    }

    public static void rm(String fileName) {
        if (!IsInit()) {
            System.exit(0);
        }

        GetPreInfo();

        if (stagingArea.ExistsInAddition(fileName)) stagingArea.RemoveAdditionFile(fileName);

        if (info.HEAD.ExistsFile(fileName)) {
            stagingArea.AddRemoveFile(fileName);
            File file = Utils.join(CWD, fileName);
            if (file.exists()) file.delete();
        }

        Utils.writeObject(f_INFO, info);
        Utils.writeObject(f_STAGING_AREA, stagingArea);
    }

    public static void checkout(String fileName) {
        if (!IsInit()) {
            System.exit(0);
        }

        GetPreInfo();

        if (info.HEAD.ExistsFile(fileName)) {
            File file = Utils.join(CWD, fileName);

            Utils.writeContents(file, info.HEAD.GetContent(fileName));
        }
    }

    public static void checkout(String commitId, String fileName) {
        if (!IsInit()) {
            System.exit(0);
        }

        GetPreInfo();

        List<String> commits = Utils.plainFilenamesIn(COMMITS);

        for (String id : commits) {
            if (commitId.equals(id)) {
                Commit commit = Utils.readObject(Utils.join(COMMITS, id), Commit.class);

                if (commit.ExistsFile(fileName)) {
                    Utils.writeContents(Utils.join(CWD, fileName), commit.GetContent(fileName));
                }
            }
        }
    }

    public static void checkout(int status, String branchName) {
        if (!IsInit()) {
            System.exit(0);
        }

        GetPreInfo();

        if (branchName.equals(info.GetCurrentBranch())) {
            System.exit(0);
        }

        Commit commit = info.GetLatestCommit(branchName);

        if (commit == null) {
            System.exit(0);
        }

        List<String> cwdFiles = Utils.plainFilenamesIn(CWD);
        for (String name : cwdFiles) {
            File file = Utils.join(CWD, name);
            Blob blob = new Blob(file);
            if (!info.HEAD.ExistsFile(name, blob)) {
                System.exit(0);
            }
        }

        // change the working directory according to commit
        Set<String> set = commit.GetFiles();
        for (String name : cwdFiles) {
            File file = Utils.join(CWD, name);
            if (!set.contains(name)) {
                file.delete();
            }
        }
        for (String name : set) {
            File file = Utils.join(CWD, name);
            Utils.writeContents(file, commit.GetContent(name));
        }

        info.SetHEAD(commit);
        info.SetCurrentBranch(branchName);

        Utils.writeObject(f_INFO, info);
    }

    public static void branch(String name) {
        if (!IsInit()) {
            System.exit(0);
        }

        GetPreInfo();

        if (info.ExistsBranch(name)) {
            System.exit(0);
        }

        info.CreateBranch(name, info.HEAD);

        Utils.writeObject(f_INFO, info);
    }

    /** Usage: java gitlet.Main status */
    public static void status() {
        if (!IsInit()) {
            System.exit(0);
        }

        GetPreInfo();

        System.out.println(info.toString() + stagingArea.toString());
    }

    public static void log() {
        if (!IsInit()) {
            System.exit(0);
        }

        GetPreInfo();

        Commit cur = info.HEAD;
        while (cur != null) {
            System.out.println(cur);
            cur = cur.GetFirParent();
        }
    }

    private static boolean IsInit() {
        return REPOSITORY.exists();
    }

    /** Formats the date time and return. */
    private static String TimeFormat(LocalDateTime t) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String tString = t.format(formatter);
        return tString;
    }

    /** Return the default date time with format. */
    private static String getTimestamp() {
        LocalDateTime initTime = LocalDateTime.of(1970, 1, 1, 0, 0, 0);
        return TimeFormat(initTime);
    }

    /** Takes now date time and return the format date time. */
    private static String getTimestamp(LocalDateTime t) {
        return TimeFormat(t);
    }
}
