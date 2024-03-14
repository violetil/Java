package byow.Core;

public class Utils {
    /**
     * Extract the integer seed from user input.
     * The input format is "N#####S", # is arbitrary numbers.
     * @param input
     */
    public static int getSeed(String input) {
        String upperString = input.toUpperCase();
        if (upperString.charAt(0) != 'N') return -1;

        int seed = 0;
        for (int i = 1; i < upperString.length(); i++) {
            if (Character.isDigit(upperString.charAt(i))) {
                seed = seed*10 + (upperString.charAt(i) - '0');
            } else {
                if (upperString.charAt(i) == 'S') break;
                else return -1;
            }
            // System.out.println(seed);
        }

        return seed;
    }
}
