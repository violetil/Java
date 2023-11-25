package gitlet;

import java.io.File;
import java.io.Serializable;

public class Blob implements Serializable {
    private String id;
    private String content;

    public Blob(File file) {
        id = null;
        content = Utils.readContentsAsString(file);

        id = Utils.sha1(Utils.serialize(this));
    }

    public String GetId() {
        return id;
    }

    public String GetContent() {
        return content;
    }
}
