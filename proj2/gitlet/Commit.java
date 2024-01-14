package gitlet;

import java.io.File;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/** A snapshot of current work directory and the basic entity of Gitlet. Store the
 *  current work directory status and that it can help us to restore the directory
 *  status whenever.
 *
 *  @author Violet
 */
public class Commit implements Serializable {
    /* Unique SHA-1 ID for the commit have the specific content. */
    private String id;
    /* The short description of the commit. */
    private String message;
    /* Creation time of the commit. */
    private LocalDateTime timestamp;
    /* ID of first commit parent of this commit. */
    private String firstParentID;
    /* ID of second commit parent of this commit. */
    private String secondParentID;
    private Map<String, Blob> fileMaps;

    public Commit(String message, String firstParentID) {
        this(message, firstParentID, null, null);
    }

    public Commit(Commit commit) {
        this(commit.getMessage(), commit.getFirstParentID(), commit.getSecondParentID(), commit.getFileMaps());
    }

    /** Construct method. Automatic generate sha-1 code for id according to specific content
     *  of content.
     *
     * @param message
     * @param firstParentID
     * @param secondParentID
     * @param fileMaps
     */
    public Commit(String message, String firstParentID, String secondParentID, Map<String, Blob> fileMaps) {
        this.id = null;
        this.message = message;
        this.firstParentID = firstParentID;
        this.secondParentID = secondParentID;

        if (message.equals("initial commit")) {
            /* All the initial commit have the same timestamp. */
            this.timestamp = LocalDateTime.of(1970, 1, 1, 0, 0, 0);
        } else {
            /* Otherwise generate the current timestamp. */
            this.timestamp = LocalDateTime.now();
        }

        /* Save all files in fileMaps. */
        this.fileMaps = new HashMap<>();
        if (fileMaps != null) {
            for (String filename : fileMaps.keySet()) {
                this.fileMaps.put(filename, fileMaps.get(filename));
            }
        }

        this.id = Utils.sha1(Utils.serialize(this));
    }

    /** Return true if this file with this blob exists in the commit file maps
     *  otherwise return false.
     *
     * @param filename
     * @param blob
     */
    public boolean containFile(String filename, Blob blob) {
        if (!containFile(filename)) return false;

        return blob.getId().equals(fileMaps.get(filename).getId());
    }

    /** Return true if this file exists in the commit file maps
     *  otherwise return false.
     *
     * @param filename
     */
    public boolean containFile(String filename) {
        return fileMaps.get(filename) != null;
    }

    /** Update timestamp according to current time. */
    public void updateTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        this.timestamp = LocalDateTime.now();
    }

    /** Update the file references according to the staging area.
     *
     * @param stagingArea
     */
    public void updateFiles(StagingArea stagingArea) {
        stagedFiles(stagingArea.getAddition());
        removalFiles(stagingArea.getRemove());
    }

    /** Add staged files. */
    private void stagedFiles(Map<String, Blob> stagedFiles) {
        for (String filename : stagedFiles.keySet()) {
            fileMaps.put(filename, stagedFiles.get(filename));
        }
    }

    /** Add removal files. */
    private void removalFiles(Map<String, Blob> removalFiles) {
        for (String filename : removalFiles.keySet()) {
            fileMaps.remove(filename);
        }
    }

    /** Recalculate and refresh the SHA-1 ID. */
    public void refreshID() {
        this.id = null;
        String hash = Utils.sha1(Utils.serialize(this));

        this.id = hash;
    }

    /** Return the file contents as string if this file exists in this commit
     *  otherwise return null.
     *
     * @param filename
     * @return Contents of the file.
     */
    public String getFileContent(String filename) {
        if (!containFile(filename)) return null;
        Blob blob = fileMaps.get(filename);
        return blob.getContent();
    }

    /** Return the file blob if this file exists in this commit
     *  otherwise return null.
     *
     * @param filename
     * @return
     */
    public Blob getBLob(String filename) {
        if (!containFile(filename)) return null;
        return fileMaps.get(filename);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Commit commit = (Commit) obj;
        return id.equals(commit.getId());
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result;
        return result;
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer("===\n");
        buffer.append("commit ");
        buffer.append(this.getId());

        if (this.getSecondParentID() != null) {
            buffer.append("\nMerge: ");
            buffer.append(this.getFirstParentID().substring(0, 5) + " ");
            buffer.append(this.getSecondParentID().substring(0, 5));
        }

        buffer.append("\nDate: ");
        buffer.append(this.getTimestamp());

        buffer.append("\n" + message + "\n");

        return buffer.toString();
    }

    /*************************  Setter and getter methods   *******************************/
    /** Return the Commit object of first parent if the first parent not null
     *  otherwise return null.
     */
    public Commit getFirstParent() {
        if (this.getFirstParentID() != null) {
            return Utils.readObject(Utils.join(Repository.COMMITS, this.getFirstParentID()), Commit.class);
        }
        return null;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return timestamp.format(formatter).toString();
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getFirstParentID() {
        return firstParentID;
    }

    public void setFirstParentID(String firstParentID) {
        this.firstParentID = firstParentID;
    }

    public String getSecondParentID() {
        return secondParentID;
    }

    public void setSecondParentID(String secondParentID) {
        this.secondParentID = secondParentID;
    }

    public Map<String, Blob> getFileMaps() {
        return fileMaps;
    }

    public void setFileMaps(Map<String, Blob> fileMaps) {
        this.fileMaps = fileMaps;
    }
}
