package gitlet;

import java.io.File;
import java.io.Serializable;

/** A version of a file which will be store the Gitlet repository with the unique SHA-1 ID.
 *
 * @author Violet
 */
public class Blob implements Serializable {
    private String id;
    private String content;

    /** Construct method, take a file parameter and generate the SHA-1 code
     *  according to the file content.
     *
     * @param file
     */
    public Blob(File file) {
        id = null;
        content = Utils.readContentsAsString(file);

        id = Utils.sha1(Utils.serialize(this));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (obj == null || getClass() != obj.getClass()) return false;

        Blob blob = (Blob) obj;
        return this.id.equals(blob.id);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + content.hashCode();
        return result;
    }

    /***********************    Setter and getter methods   *********************************/
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
