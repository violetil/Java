package gitlet;

import java.io.File;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Commit implements Serializable {
    private String id;
    private String message;
    private String timestamp;
    private Commit firstParent;
    private Commit secondParent;
    private Map<String, Blob> fileMaps;

    public Commit(String message, Commit firstParent) {
        this(message, firstParent, null, null);
    }

    public Commit(Commit commit) {
        this(commit.message, commit.firstParent, commit.secondParent, commit.fileMaps);
    }

    public Commit(String message, Commit firstParent, Commit secondParent, Map<String, Blob> fileMaps) {
        this.id = null;
        this.message = message;
        this.firstParent = firstParent;
        this.secondParent = secondParent;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        if (message.equals("initial commit")) {
            this.timestamp = LocalDateTime.of(1970, 1, 1, 0, 0, 0).format(formatter).toString();
        } else {
            this.timestamp = LocalDateTime.now().format(formatter).toString();
        }

        // deep copy
        this.fileMaps = new HashMap<>();
        if (fileMaps != null) {
            for (String tmp : fileMaps.keySet()) {
                this.fileMaps.put(tmp, fileMaps.get(tmp));
            }
        }

        this.id = Utils.sha1(Utils.serialize(this));
    }

    /** Return true if this commit have this file with specific content. */
    public boolean ExistsFile(String name, Blob blob) {
        if (fileMaps.get(name) == null) return false;

        return blob.GetId().equals(fileMaps.get(name).GetId());
    }

    public boolean ExistsFile(File file) {
        return false;
    }

    /** Return true if this commit have this file. */
    public boolean ExistsFile(String name) {
        return fileMaps.get(name) != null;
    }

    public Set<String> GetFiles() {
        return fileMaps.keySet();
    }

    /** Return the file content as string if this file exists in this commit,
     *  return false otherwise. */
    public String GetContent(String fileName) {
        if (!ExistsFile(fileName)) return null;

        Blob blob = fileMaps.get(fileName);

        return blob.GetContent();
    }

    public Blob GetBlob(String fileName) {
        return fileMaps.get(fileName);
    }

    public void AddFile(String name, Blob blob) {
        fileMaps.put(name, blob);
    }

    public void RemoveFile(String name, Blob blob) {
        fileMaps.remove(name, blob);
    }

    public void RemoveFile(String name) {
        fileMaps.remove(name);
    }

    public String GetId() {
        return this.id;
    }

    public String GetMessage() {
        return this.message;
    }

    public Commit GetFirParent() {
        return this.firstParent;
    }

    public void SetFirParent(Commit firstParent) {
        this.firstParent = firstParent;
    }

    public void SetMessage(String message) {
        this.message = message;
    }

    public String UpdateId() {
        this.id = CalculateHash();

        return this.id;
    }

    private String CalculateHash() {
        String originalId = this.id;

        this.id = null;
        String hash = Utils.sha1(Utils.serialize(this));

        this.id = originalId;
        return hash;
    }

    @Override
    public String toString() {
        String res = "===\n";
        res += "commit " + this.id;
        res += "\nDate: " + this.timestamp;
        res += "\n" + message + "\n";

        return res;
    }
}
