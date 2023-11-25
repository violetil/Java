package gitlet;

import java.io.Serializable;
import java.util.*;

public class StagingArea implements Serializable {
    private Map<String, Blob> addition;
    private Map<String, Blob> remove;

    public StagingArea() {
        addition = new HashMap<>();
        remove = new HashMap<>();
    }

    public boolean ExistsInAddition(String name, Blob blob) {
        if (addition.get(name) == null) return false;

        return blob.GetId().equals(addition.get(name).GetId());
    }

    public boolean ExistsInAddition(String name) {
        return addition.get(name) != null;
    }

    public void AddAdditionFile(String fileName, Blob blob) {
        addition.put(fileName, blob);
    }

    public void AddRemoveFile(String fileName) {
        remove.put(fileName, null);
    }

    public void RemoveAdditionFile(String name) {
        addition.remove(name);
    }

    public void PlusAdditionFiles(Commit commit) {
        for (String key : addition.keySet()) {
            commit.AddFile(key, addition.get(key));
        }
    }

    public void PlusRemoveFiles(Commit commit) {
        for (String key : remove.keySet()) {
            commit.RemoveFile(key);
        }
    }

    public void clear() {
        addition.clear();
        remove.clear();
    }

    @Override
    public String toString() {
        String res = "\n=== Staged Files ===\n";
        for (String key : addition.keySet()) {
            res += key + "\n";
        }

        res += "\n=== Removed Files ===\n";
        for (String key : remove.keySet()) {
            res += key + "\n";
        }

        return res;
    }
}
