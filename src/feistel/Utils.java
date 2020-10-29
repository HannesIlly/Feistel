package feistel;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Utils {

    public static void handleIntInputException(InputMismatchException e, int min, int max, Scanner scanner) {
        System.err.println("Die Eingabe war nicht gültig! Bitte eine Zahl von " + min + " bis " + max + " eingeben.");
        // read next line to discard the wrong number
        scanner.nextLine();
    }

    public static void handleNumberFormatException(NumberFormatException e) {
        System.err.println("Die Eingabe war keine gültige Zahl! (" + e.getMessage() + ")");
    }
}
