package sparta.clack.cipher;

/**
 * The {@code VignereCipher} class implements encryption and decryption using the Vigenère cipher technique.
 *
 * <p>The Vigenère cipher is a polyalphabetic substitution cipher, where each letter in the plaintext is shifted
 * by a number of positions defined by the corresponding letter in the keyword. This class provides methods for both
 * encryption and decryption based on this technique.
 */
public class VignereCipher extends CharacterCipher {
    private final String key;

    /**
     * Constructs a {@code VignereCipher} with the specified key.
     *
     * @param key the keyword used to shift letters in the plaintext. It must not be empty or null.
     *            The key is repeated as necessary to match the length of the plaintext.
     */
    public VignereCipher(String key) {
        if (key == null || key.isEmpty()) {
            throw new IllegalArgumentException("Need a non-null, non-empty string");
        }
        if (key.length() != prep(key).length()) {
            throw new IllegalArgumentException("Key must contain only uppercase alphabetic characters");
        }
        this.key = key;
    }

    /**
     * Prepare cleartext for encrypting. This involves removing spaces, punctuation, and non-alphabetic characters,
     * and uppercasing the remaining letters.
     *
     * @param cleartext the plaintext to prepare for encryption.
     * @return a version of the cleartext ready for encrypting.
     */
    @Override
    String prep(String cleartext) {
        return cleartext.replaceAll("[^A-Z]", "").toUpperCase();
    }

    /**
     * Encrypt a string that's been prepared for encryption.
     *
     * @param preptext a version of a cleartext string, prepared for encryption.
     * @return the encryption of the preptext.
     */
    @Override
    String encrypt(String preptext) {
        StringBuilder encryptedText = new StringBuilder();
        int keyLength = key.length();

        for (int i = 0; i < preptext.length(); i++) {
            char plaintextChar = preptext.charAt(i);
            char keyChar = key.charAt(i % keyLength);

            int shift = ALPHABET.indexOf(keyChar);  // Shift value is determined by key
            encryptedText.append(shift(plaintextChar, shift));
        }

        return encryptedText.toString();
    }

    /**
     * Decrypts an encrypted string. The decrypted text should match the preptext that was encrypted.
     *
     * @param ciphertext the encrypted string to decrypt.
     * @return the decryption of the ciphertext.
     */
    @Override
    String decrypt(String ciphertext) {
        StringBuilder decryptedText = new StringBuilder();
        int keyLength = key.length();

        for (int i = 0; i < ciphertext.length(); i++) {
            char cipherChar = ciphertext.charAt(i);
            char keyChar = key.charAt(i % keyLength);

            int shift = ALPHABET.indexOf(keyChar);  // Shift value is determined by key
            decryptedText.append(shift(cipherChar, -shift));  // Negative shift for decryption
        }

        return decryptedText.toString();
    }
}
