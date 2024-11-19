package sparta.clack.cipher;

import org.junit.jupiter.api.Test;

import static sparta.clack.cipher.CharacterCipher.*;
import static org.junit.jupiter.api.Assertions.*;

class CharacterCipherTest {

    @Test
    void testGroup() {
        // Bad n
        assertThrows(IllegalArgumentException.class,
                () -> group(null, 0));
        assertThrows(IllegalArgumentException.class,
                () -> group(null, -1));
        assertThrows(IllegalArgumentException.class,
                () -> group("SOME TEXT", 0));
        assertThrows(IllegalArgumentException.class,
                () -> group("SOME TEXT", -1));
        // str == null
        assertNull(group(null, 1));
        assertNull(group(null, 2));
        // empty string
        assertEquals("", group("", 1));
        assertEquals("", group("", 2));
        // str.length() < n
        assertEquals("A", group("A", 2));
        assertEquals("A", group("A", 3));
        // str.length() == n
        assertEquals("A", group("A", 1));
        assertEquals("AB", group("AB", 2));
        // str.length() > n but < 2n
        assertEquals("ABC D", group("ABCD", 3));
        assertEquals("ABCD EF", group("ABCDEF", 4));
        assertEquals("ABCDE FGHI", group("ABCDEFGHI", 5));
        // str.length() == 2n
        assertEquals("AB CD", group("ABCD", 2));
        assertEquals("ABC DEF", group("ABCDEF", 3));
        // str.length() > 2n
        assertEquals("ABCDE FGHIJ KL", group("ABCDEFGHIJKL", 5));
    }

    @Test
    void testShiftChar() {
        assertThrows(IllegalArgumentException.class,
                () -> shift(' ', 0));
        assertThrows(IllegalArgumentException.class,
                () -> shift('a', 0));
        assertThrows(IllegalArgumentException.class,
                () -> shift('*', 0));
        assertThrows(IllegalArgumentException.class,
                () -> shift('5', 0));

        for (int n = -2; n < 3; ++n) {
            int shiftAmt = n * ALPHABET.length();
            assertEquals('Z', shift('A', shiftAmt - 1));
            assertEquals('A', shift('A', shiftAmt));
            assertEquals('B', shift('A', shiftAmt + 1));

            assertEquals('L', shift('M', shiftAmt - 1));
            assertEquals('M', shift('M', shiftAmt));
            assertEquals('N', shift('M', shiftAmt + 1));

            assertEquals('Y', shift('Z', shiftAmt - 1));
            assertEquals('Z', shift('Z', shiftAmt));
            assertEquals('A', shift('Z', shiftAmt + 1));
        }
    }

    @Test
    void testShiftString() {
        assertThrows(IllegalArgumentException.class,
                () -> shift(" ", 0));
        assertThrows(IllegalArgumentException.class,
                () -> shift("a", 0));
        assertThrows(IllegalArgumentException.class,
                () -> shift("*", 0));
        assertThrows(IllegalArgumentException.class,
                () -> shift("5", 0));

        assertNull(shift(null, -1));
        assertNull(shift(null, 0));
        assertNull(shift(null, 1));
        assertEquals("", shift("", -1));
        assertEquals("", shift("", 0));
        assertEquals("", shift("", 1));
        assertEquals("Z", shift("A", -1));
        assertEquals("A", shift("A", 0));
        assertEquals("B", shift("A", 1));
        assertEquals("ZABCDEFGHIJKLMNOPQRSTUVWXY", shift(ALPHABET, -1));
        assertEquals(ALPHABET, shift(ALPHABET, 0));
        assertEquals("BCDEFGHIJKLMNOPQRSTUVWXYZA", shift(ALPHABET, 1));
    }
}