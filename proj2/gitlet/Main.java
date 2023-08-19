package gitlet;

import java.time.Instant;
import java.util.Arrays;

/** Driver class for Gitlet, a subset of the Git version-control system.
 *  @author Violet
 */
public class Main {

    /** Usage: java gitlet.Main ARGS, where ARGS contains
     *  <COMMAND> <OPERAND1> <OPERAND2> ...
     *
     *  This is the entry point to gitlet. It takes in arguments from command
     *  line and based on command calls the corresponding methods in Repository.
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Please enter a command.");
            return;
        }


        String firstArg = args[0];
        switch(firstArg) {
            case "init":
                validatesNumOperand("init", args, 1);
                Repository.initHandle();
                break;
            case "add":
                // TODO: handle the `add [filename]` command
                break;
            // TODO: FILL THE REST IN
            case "commit":
                break;
            default:
                System.out.println("No command with that name exists.");
        }
    }

    private static void validatesNumOperand(String op, String[] args, int n) {
        if (args.length != n) {
            System.out.println("Incorrect operands.");
            System.exit(0);
        }
    }
}
