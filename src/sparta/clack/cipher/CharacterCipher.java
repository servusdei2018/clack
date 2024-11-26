package sparta.clack.cipher;

/**
 * Abstract class for ciphers that work on character data.
 */
public abstract class CharacterCipher {
    /**
     * Default alphabet used for encryption/decryption.
     */
    public static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /** Create new instance of CharacterCipher. */
    public CharacterCipher() {}

    /**
     * Removes all non-alphabet characters from a string, and uppercases all remaining letters. This is a utility
     * method, useful in implementing prep(). If the argument is null or empty, returns it as it is.
     *
     * @param str the string to clean
     * @return the cleaned string (which might be empty), or null.
     */
    public static String clean(String str) {
        if (str == null) {
            return null;
        }
        return str.toUpperCase().replaceAll("[^A-Z]", "");
    }

    /**
     * Mathematical "mod" operator. Use instead of Java's "%" operator when shifting leftward (a negative shift), as
     * this will always return a number in the range [0, modulus).
     *
     * @param n       the number to be "modded".
     * @param modulus the modulus.
     * @throws IllegalArgumentException if modulus is less than 1.
     * @return the result of the modulus operation, guaranteed to be in the range [0, modulus).
     */
    public static int mod(int n, int modulus) {
        if (modulus < 1) {
            throw new IllegalArgumentException("modulus cannot be < 1");
        }
        // n % modulus -> in range (-modulus, modulus)
        // (n % modulus) + modulus -> in (0, 2 * modulus)
        // ((n % modulus) + modulus) % modulus -> in [0, modulus)
        return ((n % modulus) + modulus) % modulus;
    }

    /**
     * Returns a copy of a string, but reformatted into groups of <em>n</em> characters, with groups
     * separated by a space. The last group may have fewer than <em>n</em> characters.
     *
     * @param str the string to break into groups
     * @param n how many characters in each group
     * @return the grouped version of the argument string
     * @throws IllegalArgumentException if the input string is null or if <em>n</em> is less than 1
     */
    public static String group(String str, int n) {
        if (n < 1) {
            throw new IllegalArgumentException("n cannot be less than 1.");
        }
        if (str == null) {
            return null;
        }
        StringBuilder groupedString = new StringBuilder();
        int length = str.length();
        for (int i = 0; i < length; i += n) {
            if (i + n < length) {
                groupedString.append(str, i, i + n).append(" ");
            } else {
                groupedString.append(str.substring(i));
            }
        }
        return groupedString.toString();
    }

    /**
     * Returns the character that is n letters further on in ALPHABET, wrap around at both ends of ALPHABET.
     * Negative values are allowed and cause a shift to the left. A shift of 0 returns the original character.
     *
     * @param c the character to shift.
     * @param n the number of places to shift the character.
     * @return the character at the location n places beyond c.
     * @throws IllegalArgumentException if c is not in ALPHABET.
     */
    public static char shift(char c, int n) throws IllegalArgumentException {
        if (ALPHABET.indexOf(c) < 0 ) {
            throw new IllegalArgumentException(
                    "Argument ('" + c + "') not in ALPHABET");
        }
        int index = (ALPHABET.indexOf(c) + n) % ALPHABET.length();
        if (index < 0) {
            index += ALPHABET.length(); // Handle negative shift
        }
        return ALPHABET.charAt(index);
    }

    /**
     * Returns the string resulting from shifting each character of str by n places.
     *
     * @param str the string to shift.
     * @param n the amount to shift each letter.
     * @return the shifted version of str.
     * @throws IllegalArgumentException if any character in the string is not in {@code ALPHABET}.
     */
    public static String shift(String str, int n) throws IllegalArgumentException {
        if (str == null) {
            return null;
        }
        StringBuilder shiftedString = new StringBuilder();
        for (char c : str.toCharArray()) {
            shiftedString.append(shift(c, n));
        }
        return shiftedString.toString();
    }

    /**
     * Prepare cleartext for encrypting. At minimum this requires removing spaces, punctuation, and non-alphabetic
     * characters, then uppercasing what's left. Other ciphers, such as PLAYFAIR, may have additional preparation
     * that this method needs to do.
     *
     * @param cleartext text to prepare
     * @return a version of the cleartext ready for encrypting.
     */
    abstract String prep(String cleartext);

    /**
     * Encrypt a string that's been prepared for encryption.
     *
     * @param preptext a version of a cleartext string, prepared
     *                 for encryption.
     * @return the encryption of the preptext.
     */
    abstract String encrypt(String preptext);

    /**
     * Decrypts an encrypted string. The decrypted text should match
     * the preptext that was encrypted.
     *
     * @param ciphertext the encrypted string to decrypt.
     * @return the decryption of the ciphertext.
     */
    abstract String decrypt(String ciphertext);
}