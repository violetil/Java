package gitlet;

import java.io.Serializable;
import java.util.*;

/** Represent the staging area of Gitlet, it stores the staged file and remove file for
 *  later commit also provides some methods to modify the staging area.
 *
 * @author violet
 */
public class StagingArea implements Serializable {
    /* Addition area which store the staged files. */
    private Map<String, Blob> addition;
    /* Remove area which store the removed files. */
    private Map<String, Blob> remove;

    public StagingArea() {
        addition = new HashMap<>();
        remove = new HashMap<>();
    }

    /** Take a filename and blob, return true if this file with blob exists
     *  in addition area otherwise return false.
     *
     * @param filename
     * @param blob
     */
    public boolean additionContainFile(String filename, Blob blob) {
        if (!additionContainFile(filename)) return false;

        return blob.getId().equals(addition.get(filename).getId());
    }

    /** Take a filename, return true if this file exists in addition area
     *  otherwise return false.
     *
     * @param filename
     */
    public boolean additionContainFile(String filename) {
        return addition.get(filename) != null;
    }

    /** Return true if this file exists in remove area,
     *  otherwise return false.
     *
     * @param filename
     */
    public boolean removeContainFile(String filename) {
        return remove.get(filename) != null;
    }

    /** Add a file with blob into addition area.
     *
     * @param filename
     * @param blob
     */
    public void addFileToAddition(String filename, Blob blob) {
        addition.put(filename, blob);
    }

    /** Remove a file from addition area.
     *
     * @param filename
     */
    public void rmFileFromAddition(String filename) {
        addition.remove(filename);
    }

    /** Add a file into remove represent it was removal for later commit.
     *
     * @param filename
     */
    public void addFileToRemove(String filename) {
        remove.put(filename, null);
    }

    /** Remove a file from remove area.
     *
     * @param filename
     */
    public void rmFileFromRemove(String filename) {
        remove.remove(filename);
    }

    /** Clear staging area, delete all file references.
     */
    public void clear() {
        addition.clear();
        remove.clear();
    }

    /** Return true if staging area is cleared otherwise return false.
     */
    public boolean isClear() {
        if (addition.isEmpty() && remove.isEmpty()) return true;
        return false;
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer("=== Staged Files ===\n");
        List<String> additionKeys = new ArrayList<>(addition.keySet());
        Collections.sort(additionKeys);

        for (String stagedFile : additionKeys) {
            buffer.append(stagedFile);
            buffer.append("\n");
        }

        buffer.append("\n=== Removed Files ===\n");
        List<String> removalKeys = new ArrayList<>(remove.keySet());
        Collections.sort(removalKeys);

        for (String removalFile : removalKeys) {
            buffer.append(removalFile);
            buffer.append("\n");
        }

        return buffer.toString();
    }

    /***************************    Setter and getter methods   **************************/
    public Map<String, Blob> getAddition() {
        return addition;
    }

    public void setAddition(Map<String, Blob> addition) {
        this.addition = addition;
    }

    public Map<String, Blob> getRemove() {
        return remove;
    }

    public void setRemove(Map<String, Blob> remove) {
        this.remove = remove;
    }
}
