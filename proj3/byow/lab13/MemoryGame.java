package byow.lab13;

import byow.Core.RandomUtils;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.Color;
import java.awt.Font;
import java.util.Random;

public class MemoryGame {
    /** The width of the window of this game. */
    private int width;
    /** The height of the window of this game. */
    private int height;
    /** The current round the user is on. */
    private int round;
    /** The Random object used to randomly generate Strings. */
    private Random rand;
    /** Whether or not the game is over. */
    private boolean gameOver;
    /** Whether or not it is the player's turn. Used in the last section of the
     * spec, 'Helpful UI'. */
    private boolean playerTurn;
    /** The characters we generate random Strings from. */
    private static final char[] CHARACTERS = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    /** Encouraging phrases. Used in the last section of the spec, 'Helpful UI'. */
    private static final String[] ENCOURAGEMENT = {"You can do this!", "I believe in you!",
                                                   "You got this!", "You're a star!", "Go Bears!",
                                                   "Too easy for you!", "Wow, so impressive!"};

    public static void main(String[] args) {
        MemoryGame game = new MemoryGame(40, 40);
        game.startGame();
    }

    public MemoryGame(int width, int height) {
        /* Sets up StdDraw so that it has a width by height grid of 16 by 16 squares as its canvas
         * Also sets up the scale so the top left is (0,0) and the bottom right is (width, height)
         */
        this.width = width;
        this.height = height;
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();

        rand = new Random();
    }

    public String generateRandomString(int n) {
        String reString = "";

        for (int i = 0; i < n; i++) {
            reString += (char)(rand.nextInt(26)+'a');
        }
        return reString;
    }

    public void drawFrame(String s) {
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setPenColor(StdDraw.WHITE); // Set the pen color for draw.
        StdDraw.clear(StdDraw.BLACK); // Clear the window.
        StdDraw.text(0.5*this.width, 0.5*this.height, s); // Draw string into the offscreen.

        if (!gameOver) { // Display relevant game information.
            Font tFont = new Font("Monaco", Font.BOLD, 20);
            StdDraw.setFont(tFont);

            // Add the top bar
            StdDraw.line(0*this.width, 0.95*this.height, 1*this.width, 0.95*this.height);
            StdDraw.text(0.09*this.width, 0.975*this.height, "Round: " + round);
            String text = "";

            if (this.playerTurn) text = "Type!";
            else text = "Watch!";

            StdDraw.text(0.5*this.width, 0.975*this.height, text);
            StdDraw.text(0.80*this.width, 0.975*this.height, ENCOURAGEMENT[rand.nextInt(ENCOURAGEMENT.length)]);
        }
        StdDraw.show(); // Get copied from the offscreen to the screen.
        StdDraw.pause(300);
    }

    public void flashSequence(String letters) {
        for (char c : letters.toCharArray()) {
            drawFrame("");
            StdDraw.show();
            StdDraw.pause(200); // Blank time.

            drawFrame(Character.toString(c));
            // StdDraw.text(0.5*this.width, 0.5*this.height, Character.toString(c)); // Draw string into the offscreen.
            StdDraw.show(); // Get copied from the offscreen to the screen.
            StdDraw.pause(600); // Character show time.
        }
    }

    public String solicitNCharsInput(int n) {
        String reStr = "";
        int loops = 0; // Loop counter.

        while (loops < n) {
            if (StdDraw.hasNextKeyTyped()) {
                // Handle the user input.
                reStr += StdDraw.nextKeyTyped();
                drawFrame(reStr); // Draw the input string into screen.
                loops++;
            }
        }
        StdDraw.pause(1000);

        return reStr;
    }

    public void startGame() {
        gameOver = false; // Game is begin.
        round = 1; // First round is one.
        playerTurn = false;

        while (!gameOver) {
            drawFrame("Round: " + round); // Show the initial message.

            String randStr = generateRandomString(round);
            String inputStr = "";
            flashSequence(randStr); // Display the random string one letter at a time.

            playerTurn = true;
            drawFrame("");
            StdDraw.show();

            while (!StdDraw.hasNextKeyTyped()) { /* Wait for the user type in. */ }

            inputStr = solicitNCharsInput(round);

            if (!randStr.equals(inputStr)) gameOver = true;

            round++; // Next turn increase round.
            playerTurn = false;
        }

        // Game over.
        drawFrame("Game Over! You made it to round: " + (round-1));
    }
}
