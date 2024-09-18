package sparta.clack.message;

/**
 * The {@code CaesarCipher} class implements encryption and decryption using the Caesar cipher technique.
 *
 * <p>The Caesar cipher is a substitution cipher where each letter in the plaintext is shifted a fixed number of places
 * down or up the alphabet. This class provides methods for both encryption and decryption based on this technique.
 *
 * <p>To avoid code duplication between encryption and decryption, the actual character shifting is handled by the
 * {@code transform} method. Encryption is achieved by applying a positive shift (specified by the key), while decryption
 * is achieved by applying a negative shift. For detailed implementation, refer to the {@link #transform(String, int)
 * transform} method.
 */
public class CaesarCipher {
    // Default alphabet used for encryption/decryption
    private static final String DEFAULT_ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private final String alphabet;
    private final int key;

    /**
     * Constructs a {@code CaesarCipher} with the specified key and the default alphabet.
     *
     * @param key the number of positions each letter in the plaintext is shifted. This value is modulo the length of
     *            the alphabet to ensure it falls within the valid range.
     */
    public CaesarCipher(int key) {
        this(key, DEFAULT_ALPHABET);
    }

    /**
     * Constructs a {@code CaesarCipher} with the specified key and custom alphabet.
     *
     * @param key      the number of positions each letter in the plaintext is shifted. This value is modulo the length
     *                 of the alphabet to ensure it falls within the valid range.
     * @param alphabet the custom alphabet used for the cipher. This should be a string containing unique characters.
     *                 It is converted to uppercase for consistency.
     */
    public CaesarCipher(int key, String alphabet) {
        this.key = key % alphabet.length(); // Handle key larger than alphabet length
        this.alphabet = alphabet.toUpperCase();
    }

    /**
     * Returns the alphabet used for encryption and decryption.
     *
     * @return the alphabet used by this cipher.
     */
    public String getAlphabet() {
        return alphabet;
    }

    /**
     * Encrypts the specified clear text using the Caesar cipher.
     *
     * @param clearText the text to be encrypted. Non-alphabetic characters are unchanged.
     * @return the encrypted text where each letter is shifted by the cipher's key value.
     */
    public String encrypt(String clearText) {
        return transform(clearText, key);
    }

    /**
     * Decrypts the specified cipher text using the Caesar cipher.
     *
     * @param clearText the text to be decrypted. Non-alphabetic characters are unchanged.
     * @return the decrypted text where each letter is shifted back by the cipher's key value.
     */
    public String decrypt(String clearText) {
        return transform(clearText, -key);
    }


    /**
     * Transforms the given text by shifting characters according to the specified shift value.
     *
     * @param text  the text to be transformed. Non-alphabetic characters are unchanged.
     * @param shift the number of positions to shift each letter. Positive for encryption, negative for decryption.
     * @return the transformed text after applying the shift.
     */
    private String transform(String text, int shift) {
        StringBuilder transformed = new StringBuilder();
        String upperText = text.toUpperCase();
        int alphabetLength = alphabet.length();

        for (char c : upperText.toCharArray()) {
            int index = alphabet.indexOf(c);
            if (index >= 0) {
                int newIndex = (index + shift + alphabetLength) % alphabetLength;
                transformed.append(alphabet.charAt(newIndex));
            } else {
                transformed.append(c); // Non-alphabet characters are not changed
            }
        }

        return transformed.toString();
    }
}
