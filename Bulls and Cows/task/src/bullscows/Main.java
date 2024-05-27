package bullscows;

import java.util.*;

public class Main {

    public static int bulls;
    public static boolean playingGame = true;
    public static int turnCounter = 1;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please enter the secret code's length:");

        String inputLengthOfCode;
        int lengthOfCode;

        if (scanner.hasNextInt()) {
            inputLengthOfCode = scanner.next();
            lengthOfCode = Integer.parseInt(inputLengthOfCode);
            if (lengthOfCode > 36 || lengthOfCode <= 0) {
                System.out.println("Error: can't generate a secret number with a length of " + lengthOfCode + " because there aren't enough unique digits.");
                return;
            }
        } else {
            inputLengthOfCode = scanner.nextLine();
            System.out.println("Error: \"" + inputLengthOfCode + "\" isn't a valid number.");
            return;
        }

        System.out.println("Input the number of possible symbols in the code:");

        String inputNumberOfSymbols;
        int numberOfSymbols;

        if (scanner.hasNextInt()) {
            inputNumberOfSymbols = scanner.next();
            numberOfSymbols = Integer.parseInt(inputNumberOfSymbols);
            if (numberOfSymbols > 36) {
                System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
                return;
            }
            if (numberOfSymbols < lengthOfCode) {
                System.out.println("Error: it's not possible to generate a code with a length of " + lengthOfCode+ " with " + numberOfSymbols + " unique symbols.");
                return;
            }
        } else {
            inputNumberOfSymbols = scanner.nextLine();
            System.out.println("Error: \"" + inputNumberOfSymbols + "\" isn't a valid number.");
            return;
        }

        String secretCode = randomGenerator(lengthOfCode, numberOfSymbols);

        System.out.println("Okay, let's start a game!");

        String guess;
        while(playingGame) {
            guess = scanner.next();
            if (guess.length() > secretCode.length()){
                System.out.println("Your guess should have length of " + lengthOfCode);
            } else {
                game(secretCode, guess);
            }

            if (bulls == secretCode.length()) {
                playingGame = false;
            }
        }

        System.out.println("Congratulations! You guessed the secret code.");

    }

    public static StringBuilder getGrade (String secretCode, String guess) {
        StringBuilder stringBuilder = new StringBuilder("Grade: ");

        bulls = 0;
        int cows = 0;

        for (int i = 0; i < guess.length(); i++) {
            if (guess.charAt(i) == secretCode.charAt(i)) {
                bulls++;
            } else if(secretCode.contains(String.valueOf(guess.charAt(i)))) {
                cows++;
            }
        }
        if (cows > 0 && bulls > 0) {
            stringBuilder.append(bulls).append(" bull").append(bulls > 1 ? "s" : "")
                    .append(" and ")
                    .append(cows).append(" cow").append(cows > 1 ? "s" : "");
        } else if (bulls > 0 && cows == 0) {
            stringBuilder.append(bulls).append(" bull").append(bulls > 1 ? "s" : "");
        } else if (cows > 0 && bulls == 0) {
            stringBuilder.append(cows).append(" cow").append(cows > 1 ? "s" : "");
        } else {
            stringBuilder.append("None");
        }
        return stringBuilder;
    }

    public static String randomGenerator(int length, int symbols) {
        StringBuilder stringBuilder = new StringBuilder("The secret is prepared: ");
        List<String> randomList = new ArrayList<>(List.of("0", "1", "2", "3", "4",
                "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f", "g", "h", "i",
                "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t","u", "v", "w", "x",
                "y", "z"));

        randomList.subList(symbols, randomList.size()).clear();


        stringBuilder.append("*".repeat(Math.max(0, length)));

        if (symbols <= 10) {
            stringBuilder.append(" (0-").append(symbols - 1).append(").");
        } else if (symbols == 11) {
            stringBuilder.append(" (0-9, a)");
        } else {
            stringBuilder.append(" (0-9, a-").append(randomList.get(symbols - 1)).append(").");
        }

        System.out.println(stringBuilder);

        do {
            Collections.shuffle(randomList);
        } while (randomList.get(0).equals("0"));

        StringBuilder result = new StringBuilder();

        for (var ch : randomList.subList(0, length)) {
            result.append(ch);
        }
        return result.toString();
    }

    public static void game(String secretCode, String guess) {
        System.out.println("Turn " + turnCounter + ":");

        System.out.println(getGrade(secretCode, guess));

        turnCounter++;
    }
}
