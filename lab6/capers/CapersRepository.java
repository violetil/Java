package capers;

import java.io.File;
import java.io.IOException;

import static capers.Utils.*;

/** A repository for Capers 
 * @author violet
 * The structure of a Capers Repository is as follows:
 *
 * .capers/ -- top level folder for all persistent data in your lab12 folder
 *    - dogs/ -- folder containing all of the persistent data for dogs
 *    - story -- file containing the current story
 */
public class CapersRepository {
    /** Current Working Directory. */
    static final File CWD = new File(System.getProperty("user.dir"));

    /** Main metadata folder. */
    static final File CAPERS_FOLDER = Utils.join(CWD, ".capers");

    /**
     * Does required filesystem operations to allow for persistence.
     * (creates any necessary folders or files)
     * Remember: recommended structure (you do not have to follow):
     *
     * .capers/ -- top level folder for all persistent data in your lab12 folder
     *    - dogs/ -- folder containing all of the persistent data for dogs
     *    - story -- file containing the current story
     */
    public static void setupPersistence() throws IOException {
        if (!CAPERS_FOLDER.exists()) {
            CAPERS_FOLDER.mkdirs();
        }

        // Story file.
        File storyFile = Utils.join(CAPERS_FOLDER, "story");
        if (!storyFile.exists()) {
            storyFile.createNewFile();
        }

        // Dogs folder.
        File dogsFolder = Utils.join(CAPERS_FOLDER, "dogs");
        if (!dogsFolder.exists()) {
            dogsFolder.mkdirs();
        }
    }

    /**
     * Appends the first non-command argument in args
     * to a file called `story` in the .capers directory.
     * @param text String of the text to be appended to the story
     */
    public static void writeStory(String text) {
        File storyFile = Utils.join(CAPERS_FOLDER, "story");
        String story = readContentsAsString(storyFile);

        // Appending the text to end of story, then prints and stores story to file.
        story += text + "\n";
        System.out.print(story);
        writeContents(storyFile, story);
    }

    /**
     * Creates and persistently saves a dog using the first
     * three non-command arguments of args (name, breed, age).
     * Also prints out the dog's information using toString().
     */
    public static void makeDog(String name, String breed, int age) throws IOException {
        Dog dog = new Dog(name, breed, age);
        System.out.println(dog.toString());
        dog.saveDog();
    }

    /**
     * Advances a dog's age persistently and prints out a celebratory message.
     * Also prints out the dog's information using toString().
     * Chooses dog to advance based on the first non-command argument of args.
     * @param name String name of the Dog whose birthday we're celebrating.
     */
    public static void celebrateBirthday(String name) throws IOException {
        Dog dog = Dog.fromFile(name);
        if (dog != null) {
            dog.haveBirthday();
            dog.saveDog();
        }
    }
}
