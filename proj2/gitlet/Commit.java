package gitlet;

import java.time.Instant;

/** Represents a gitlet commit object.
 *
 *
 *  @author Violet
 */
public class Commit {
    /** Hava message, pointer of parent Commit and timestamp filed.
     *  Use the java.time.Instant to represent the UTC timestamp.
     */

    /** The message of this Commit. */
    private String message;
    /** Timestamp of this Commit. */
    private String timestamp;
    /** Point to the parent Commit. */
    private Commit parent;

    /** Initialize metadata of Commit. */
    public Commit(String message, Commit parent) {
        this.message = message;
        this.parent = parent;

        // UTC timestamp without zone offset.
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

    /** Get the pointer of parent of Commit. */
    public Commit getParent() {
        return parent;
    }
}
