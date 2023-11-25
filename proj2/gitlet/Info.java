package gitlet;

import java.io.Serializable;
import java.util.*;

public class Info implements Serializable {
    public Commit HEAD;
    private String currentBranch;
    private Map<String, Commit> branches;

    public Info() {
        branches = new HashMap<>();
    }

    public boolean ExistsBranch(String name) {
        return branches.get(name) != null;
    }

    public void CreateBranch(String name, Commit commit) {
        branches.put(name, commit);
    }

    public String GetCurrentBranch() {
        return currentBranch;
    }

    public Commit GetLatestCommit(String branchName) {
        for (String branch : branches.keySet()) {
            if (branchName.equals(branch)) {
                return branches.get(branch);
            }
        }

        return null;
    }

    public void SetHEAD(Commit commit) {
        HEAD = commit;
    }

    public void SetCurrentBranch(String name) {
        currentBranch = name;
    }

    public void SetBranch(String name, Commit commit) {
        branches.put(name, commit);
    }

    @Override
    public String toString() {
        String res = "=== Branches ===\n";
        res += "*" + currentBranch + "\n";

        for (String key : branches.keySet()) {
            if (!key.equals(currentBranch)) res += key + "\n";
        }

        return res;
    }
}
