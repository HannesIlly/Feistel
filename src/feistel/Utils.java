package feistel;

import java.util.InputMismatchException;

public class Utils {

    public static void printIntInputException(InputMismatchException e, int min, int max) {
        System.err.println("Die Eingabe war nicht gültig! Bitte eine Zahl von " + min + " bis " + max + " eingeben.");
    }

    public static void handleNumberFormatException(NumberFormatException e) {
        System.err.println("Die Eingabe war keine gültige Zahl! (" + e.getMessage() + ")");
    }
}
