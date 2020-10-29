package feistel;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    private final Scanner scanner;

    public Main() {
        scanner = new Scanner(System.in);

        mainMenu();
        //testEncryptDecrypt();
    }

    public static void main(String[] args) {
        new Main();
    }


    public void mainMenu() {
        while (true) {
            System.out.println("Feistel-Algorithmus");
            System.out.println("´´´´´´´´´´´´´´´´´´´");
            System.out.println("1. Text Verschlüsseln");
            System.out.println("2. Text Entschlüsseln");
            System.out.println();
            System.out.println("0. Beenden");

            try {
                int option = scanner.nextInt();
                scanner.nextLine();
                switch (option) {
                    case 1:
                        encodeText();
                        break;
                    case 2:
                        decodeText();
                        break;
                    case 0:
                        return;
                }
            } catch(InputMismatchException e) {
                Utils.handleIntInputException(e, 1, 2, scanner);
            } catch (NumberFormatException e) {
                Utils.handleNumberFormatException(e);
            }
        }
    }

    private void decodeText() {
        System.out.println("Text eingeben:");
        String text = scanner.nextLine();
        byte[] keys = getKeys();

        System.out.println("Entschlüsselter Text:");
        System.out.println(FeistelAlgorithm.decodeText(keys, text));
    }

    private void encodeText() {
        System.out.println("Text eingeben:");
        String text = scanner.nextLine();
        byte[] keys = getKeys();

        System.out.println("Verschlüsselter Text:");
        System.out.println(FeistelAlgorithm.encodeText(keys, text));
    }

    private byte[] getKeys() {
        while (true) {
            System.out.println("Schlüssel benötigt");
            System.out.println("1. Schlüssel als Text eingeben (Text wird automatisch in die Schlüsselzahlen umgewandelt)");
            System.out.println("2. Schlüssel als Zahlen eingeben (Kommagetrennte Werte von 0 - 255; z.B.: 34,76,123,99,241)");
            try {
                int option = scanner.nextInt();
                scanner.nextLine();
                switch (option) {
                    case 1 -> {
                        System.out.println("Bitte Schlüssel eingeben (Text):");
                        return readKeysFromText(scanner.nextLine());
                    }
                    case 2 -> {
                        System.out.println("Bitte Schlüssel eingeben (Zahlen):");
                        return readKeysFromNumbers(scanner.nextLine());
                    }
                    default -> throw new InputMismatchException("Bitte eine gültige Zahl eingeben!");
                }
            } catch (InputMismatchException e) {
                Utils.handleIntInputException(e, 1, 2, scanner);
            } catch (NumberFormatException e) {
                Utils.handleNumberFormatException(e);
            }
        }
    }

    private byte[] readKeysFromText(String keyText) {
        char[] keyChars = keyText.toCharArray();
        byte[] keys = new byte[keyChars.length * 2];
        for (int i = 0; i < keyChars.length; i++) {
            keys[2 * i] = (byte) (keyChars[i] >>> 8);
            keys[2 * i + 1] = (byte) keyChars[i];
        }
        return keys;
    }

    private byte[] readKeysFromNumbers(String keys) throws NumberFormatException {
        String[] keyStringArray = keys.split(",");
        byte[] keyBytes = new byte[keyStringArray.length];
        for (int i = 0; i < keyStringArray.length; i++) {
            int number = Integer.parseInt(keyStringArray[i]) + Byte.MIN_VALUE;
            if (number < Byte.MIN_VALUE || number > Byte.MAX_VALUE) {
                throw new NumberFormatException("Number was not between 0 and 255");
            }
            keyBytes[i] = (byte) number;
        }
        return keyBytes;
    }



    private void testEncryptDecrypt() {
        String text = scanner.nextLine();

        byte[] keys = {12, -123, 92, 126, -3, -92, -34, 93};

        String encodedText = FeistelAlgorithm.encodeText(keys, text);
        System.out.println("Verschlüsselter Text: " + encodedText);

        String decodedText = FeistelAlgorithm.decodeText(keys, encodedText);
        System.out.println("Entschlüsselter Text: " + decodedText);
    }
}
