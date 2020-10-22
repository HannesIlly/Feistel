package feistel;

public class FeistelAlgorithm {

    /**
     * Encodes a string with the given set of keys. First each char of the message will be encoded with the first key.
     * In the next step the this message is encoded with the next key - this is repeated until each key was used.
     *
     * @param keys The keys used for encoding the message.
     * @param text The message.
     * @return The encoded message.
     */
    public static String encodeText(byte[] keys, String text) {
        // char[] is used to encode each character of the message separately
        char[] textChars = text.toCharArray();

        // iterate all keys
        for (byte currentKey : keys) {
            // encode the text with current key
            for (int i = 0; i < textChars.length; i++) {
                textChars[i] = encodeChar(textChars[i], currentKey);
            }
        }

        return new String(textChars);
    }

    /**
     * Decodes the message by traversing the keys used for encoding in reverse order and decoding each characters in each step.
     * @param keys The keys that were used to encode the message.
     * @param encodedText The encoded message.
     * @return The decoded message.
     */
    public static String decodeText(byte[] keys, String encodedText) {
        // char[] is used to decode each character of the message separately
        char[] textChars = encodedText.toCharArray();

        // iterate all keys in reverse order
        for (int k = keys.length - 1; k >= 0; k--) {
            // decode the text with the current key
            for (int i = 0; i < textChars.length; i++) {
                textChars[i] = decodeChar(textChars[i], keys[k]);
            }
        }

        return new String(textChars);
    }

    /**
     * This method encodes a char with a specific key. The char (size 16 Bit = 2 Bytes) will be split into a left byte and right byte.
     * The key is then used to encode the right part, and has therefore also the size of one byte.
     * The remaining operations further encode the input char by performing a xor-Operation (^) and switching the right byte to the left.
     *
     * @param input The character that will be encoded.
     * @param key   The key used to encode the right byte.
     * @return The encoded character.
     */
    private static char encodeChar(char input, byte key) {
        // The left byte has to be shifted, so that it can be stored in a byte-variable.
        byte left = (byte) (input >>> 8); // >>> right shift by 8 Bits (ignores if the number was positive or negative)
        byte right = (byte) input;

        // apply the encoding operations
        byte encodedLeft = right;
        // ^ is the bitwise XOR-Operation
        byte encodedRight = (byte) (left ^ encodeFunction(key, right));

        // The resulting bytes now have to be packed together by shifting the left byte to its place and combining it with the right byte.
        // The AND-Operation with the right byte ensures that there will be no ones where the left byte would be
        // (so that the OR-Operation for combining the two bytes can succeed)
        return (char) ((encodedLeft << 8) | (0x00FF & encodedRight));
    }

    /**
     * Decodes the specified character by performing the encoding operations in reverse order (with inverse functions).
     *
     * @param input The character that will be decoded.
     * @param key   The key that was used to encode the char.
     * @return The encoded char.
     */
    private static char decodeChar(char input, byte key) {
        // encoding operations in reverse order and using the decodeFunction

        byte left = (byte) (input >>> 8);
        byte right = (byte) input;

        byte decodedLeft = (byte) (right ^ decodeFunction(key, left));
        byte decodedRight = left;

        return (char) ((decodedLeft << 8) | (0x00FF & decodedRight));
    }

    /**
     * This method represents the function F(K,R) that encodes the right part with a given key.
     *
     * @param key   The key that is used for encoding.
     * @param input The message that will be encoded.
     * @return The encoded message.
     */
    private static byte encodeFunction(byte key, byte input) {
        // The encoding function is in this example a simple XOR-Function.
        // (With this simple operation there is no need for a separate decodeFunction but if it is replaced by a different function, the algorithm needs to use the inverse of it (decodeFunction))
        return (byte) (key ^ input);
    }

    /**
     * This method represents the inverse function of F(K,R). It decodes the right part of the message with the given key and encoded message.
     *
     * @param key     The key that is used for decoding.
     * @param encoded The encoded message that will be decoded.
     * @return The decoded message.
     */
    private static byte decodeFunction(byte key, byte encoded) {
        return (byte) (encoded ^ key);
    }
}
