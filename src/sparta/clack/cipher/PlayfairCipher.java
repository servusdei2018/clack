package sparta.clack.cipher;

/**
 * The {@code PlayfairCipher} class implements encryption and decryption using the Playfair cipher technique.
 *
 * <p>The Playfair cipher is a digraph substitution cipher, where pairs of letters (digraphs) are encrypted or
 * decrypted based on a 5x5 matrix of letters. The matrix is constructed using a key, and letters are substituted
 * according to the following rules:
 * <ul>
 *   <li>If both letters of a digraph are in the same row, they are replaced by the letters immediately to their
 *       right (with wraparound).</li>
 *   <li>If both letters of a digraph are in the same column, they are replaced by the letters immediately below them
 *       (with wraparound).</li>
 *   <li>If the letters of a digraph form a rectangle, they are replaced by the letters in the same row but at the
 *       opposite corners.</li>
 * </ul>
 */
public class PlayfairCipher extends CharacterCipher {
    private final String key;

    /**
     * Constructs a {@code PlayfairCipher} with the specified key.
     *
     * @param key the keyword used to generate the Playfair cipher matrix. It must not be empty or null.
     *            Only uppercase alphabetic characters are allowed in the key.
     * @throws IllegalArgumentException if the key is null.
     */
    public PlayfairCipher(String key) throws IllegalArgumentException {
        if (key == null) {
            throw new IllegalArgumentException("key must be a non-null string");
        }
        if (key.isEmpty()) {
            key = "ABCDEFGHIKLMNOPQRSTUVWXYZ";
        }
        this.key = key.toUpperCase().replaceAll("[^A-Z]", "");  // Keep only A-Z letters
    }

    /**
     * Prepare cleartext for encryption. This involves removing spaces, punctuation, and non-alphabetic characters,
     * and uppercasing the remaining letters. Additionally, 'J' is replaced with 'I', and the text is split into
     * digraphs. If a digraph consists of two identical letters, an 'X' is inserted between them.
     *
     * @param cleartext the plaintext to prepare for encryption.
     * @return a version of the cleartext ready for encryption, formatted as digraphs.
     */
    @Override
    String prep(String cleartext) {
        cleartext = cleartext.replaceAll("[^A-Za-z]", "").toUpperCase();
        // Handle 'J' by replacing with 'I' for the Playfair cipher
        cleartext = cleartext.replace('J', 'I');

        // Split the text into digraphs (pairs of letters)
        StringBuilder prepText = new StringBuilder();
        for (int i = 0; i < cleartext.length(); i++) {
            char current = cleartext.charAt(i);
            if (i + 1 < cleartext.length()) {
                char next = cleartext.charAt(i + 1);
                if (current == next) {
                    // If both characters are the same, insert an 'X' between them
                    prepText.append(current).append('X');
                    i++;  // Skip the next character
                } else {
                    prepText.append(current).append(next);
                    i++;  // Skip the next character
                }
            } else {
                prepText.append(current).append('X'); // Add 'X' to the last character if odd-length
            }
        }
        return prepText.toString();
    }

    /**
     * Encrypt a string that's been prepared for encryption.
     *
     * @param preptext a version of a cleartext string, prepared for encryption.
     * @return the encryption of the preptext.
     */
    @Override
    String encrypt(String preptext) {
        char[][] matrix = createMatrix();
        StringBuilder ciphertext = new StringBuilder();
        for (int i = 0; i < preptext.length(); i += 2) {
            char a = preptext.charAt(i);
            char b = preptext.charAt(i + 1);
            int[] posA = findPosition(matrix, a);
            int[] posB = findPosition(matrix, b);

            if (posA[0] == posB[0]) {
                // Same row: shift right
                ciphertext.append(matrix[posA[0]][(posA[1] + 1) % 5]);
                ciphertext.append(matrix[posB[0]][(posB[1] + 1) % 5]);
            } else if (posA[1] == posB[1]) {
                // Same column: shift down
                ciphertext.append(matrix[(posA[0] + 1) % 5][posA[1]]);
                ciphertext.append(matrix[(posB[0] + 1) % 5][posB[1]]);
            } else {
                // Rectangle: swap corners
                ciphertext.append(matrix[posA[0]][posB[1]]);
                ciphertext.append(matrix[posB[0]][posA[1]]);
            }
        }
        return ciphertext.toString();
    }

    /**
     * Decrypts an encrypted string. The decrypted text should match the preptext that was encrypted.
     *
     * @param ciphertext the encrypted string to decrypt.
     * @return the decryption of the ciphertext.
     */
    @Override
    String decrypt(String ciphertext) {
        char[][] matrix = createMatrix();
        StringBuilder plaintext = new StringBuilder();
        for (int i = 0; i < ciphertext.length(); i += 2) {
            char a = ciphertext.charAt(i);
            char b = ciphertext.charAt(i + 1);
            int[] posA = findPosition(matrix, a);
            int[] posB = findPosition(matrix, b);

            if (posA[0] == posB[0]) {
                // Same row: shift left
                plaintext.append(matrix[posA[0]][(posA[1] + 4) % 5]);
                plaintext.append(matrix[posB[0]][(posB[1] + 4) % 5]);
            } else if (posA[1] == posB[1]) {
                // Same column: shift up
                plaintext.append(matrix[(posA[0] + 4) % 5][posA[1]]);
                plaintext.append(matrix[(posB[0] + 4) % 5][posB[1]]);
            } else {
                // Rectangle: swap corners
                plaintext.append(matrix[posA[0]][posB[1]]);
                plaintext.append(matrix[posB[0]][posA[1]]);
            }
        }
        return plaintext.toString();
    }

    /**
     * Creates a 5x5 matrix for the Playfair cipher based on the provided key. The matrix is filled with the letters
     * of the key (removing duplicates) and then the remaining unused alphabetic letters (excluding 'J') are added.
     *
     * @return the 5x5 Playfair cipher matrix.
     */
    private char[][] createMatrix() {
        StringBuilder combined = new StringBuilder(key);
        for (char c = 'A'; c <= 'Z'; c++) {
            if (combined.indexOf(String.valueOf(c)) == -1 && c != 'J') {
                combined.append(c);
            }
        }
        char[][] matrix = new char[5][5];
        int index = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                matrix[i][j] = combined.charAt(index++);
            }
        }
        return matrix;
    }

    /**
     * Finds the row and column of a given character in the Playfair cipher matrix.
     *
     * @param matrix the Playfair cipher matrix.
     * @param c the character to locate in the matrix.
     * @return an array containing the row and column indices of the character in the matrix.
     * @throws IllegalArgumentException if the character is not found in the matrix.
     */
    private int[] findPosition(char[][] matrix, char c) throws IllegalArgumentException {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (matrix[i][j] == c) {
                    return new int[]{i, j};
                }
            }
        }
        throw new IllegalArgumentException("Character not found in matrix");
    }
}
