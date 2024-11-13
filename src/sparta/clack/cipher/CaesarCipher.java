package sparta.clack.cipher;

/**
 * The {@code CaesarCipher} class implements encryption and decryption using the Caesar cipher technique.
 *
 * <p>The Caesar cipher is a substitution cipher where each letter in the plaintext is shifted a fixed number of places
 * down or up the alphabet. This class provides methods for both encryption and decryption based on this technique.
 */
public class CaesarCipher extends CharacterCipher {
    private final int key;

    /**
     * Constructs a {@code CaesarCipher} with the specified key and the default alphabet.
     *
     * @param key the number of positions each letter in the plaintext is shifted. This value is modulo the length of
     *            the alphabet to ensure it falls within the valid range.
     */
    public CaesarCipher(int key) {
        this.key = key % ALPHABET.length();
    }

    /**
     * Constructs a {@code CaesarCipher} where the shift is given by taking the key's first letter, then finding the
     * location of this letter in {@code ALPHABET}.
     *
     * @param key the string whose first letter determines the shift.
     */
    public CaesarCipher(String key) {
        if (key == null || key.isEmpty()) {
            throw new IllegalArgumentException(
                    "Need a non-null, non-empty string");
        }
        char shiftChar = key.charAt(0);
        this.key = ALPHABET.indexOf(shiftChar);
        if (this.key < 0) {
            throw new IllegalArgumentException(
                    "First character of 'key' argument not in ALPHABET");
        }
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
        return CharacterCipher.shift(preptext, key);
    }

    /**
     * Decrypts an encrypted string. The decrypted text should match the preptext that was encrypted.
     *
     * @param ciphertext the encrypted string to decrypt.
     * @return the decryption of the ciphertext.
     */
    @Override
    String decrypt(String ciphertext) {
        return CharacterCipher.shift(ciphertext, -key);
    }
}
