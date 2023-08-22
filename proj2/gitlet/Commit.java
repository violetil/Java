package gitlet;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;

/** Represents a gitlet commit object.
 *
 *  Store describe of commit from user and create time of commit.
 *  Commit is serializable and will be store in file to contain persistence.
 *  Every initial commit timestamp is 00:00:00 UTC, Thursday, 1 January 1970.
 *
 *  @author Violet
 */
public class Commit implements Serializable {
    /** The message of this Commit. */
    private String message;
    /** Timestamp of this Commit. */
    private String timestamp;
    /** The unique id of this Commit. */
    private String uid;
    /** The unique id of parent Commit. */
    private String parentUID;

    /** Initialize metadata of Commit. */
    public Commit(String message, String parentUID) {
        this.message = message;
        this.parentUID = parentUID;

        // UTC timestamp without zone offset.
        // Initial commit
        if (parentUID == null) {
            LocalDateTime specificDateTime = LocalDateTime.of(1970, 1, 1, 0, 0);
            this.timestamp = specificDateTime.toInstant(java.time.ZoneOffset.UTC).toString();
        }
        this.timestamp = Instant.now().toString();
    }

    /** Get the message of Commit. */
    public String getMessage() {
        return message;
    }

    /** Get the timestamp of Commit. */
    public String getTimestamp() {
        return timestamp;
    }

    /** Get the unique id of parent Commit. */
    public String getParentUID() {
        return parentUID;
    }

    /** Update the uid. */
    public void updateUID(String uid) {
        this.uid = uid;
    }
}
