package sparta.clack.cipher;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CaesarCipherTest {

    @Test
    void encrypt() {
        String msg = "THE QUICK BROWN FOX JUMPED OVER THE LAZY POODLE";
        CaesarCipher cc = new CaesarCipher(1);
        assertEquals("UIFRVJDLCSPXOGPYKVNQFEPWFSUIFMBAZQPPEMF",
                cc.encrypt(cc.prep(msg)));
        cc = new CaesarCipher(53);
        assertEquals("BCDEFGHIJKLMNOPQRSTUVWXYZA", cc.encrypt(CharacterCipher.ALPHABET));
    }

    @Test
    void decrypt() {
        String msg = "UIFRVJDLCSPXOGPYKVNQFEPWFSUIFMBAZQPPEMF";
        CaesarCipher cc = new CaesarCipher(1);
        assertEquals(cc.prep("THE QUICK BROWN FOX JUMPED OVER THE LAZY POODLE"),
                cc.decrypt(msg));
        cc = new CaesarCipher(53);
        assertEquals(CharacterCipher.ALPHABET, cc.decrypt("BCDEFGHIJKLMNOPQRSTUVWXYZA"));
    }

    @Test
    void badConstructorArgs() {
        Exception e;

        e = assertThrows(IllegalArgumentException.class,
                () -> new CaesarCipher(null));
        assertEquals("Need a non-null, non-empty string", e.getMessage());

        e = assertThrows(IllegalArgumentException.class,
                () -> new CaesarCipher(""));
        assertEquals("Need a non-null, non-empty string", e.getMessage());

        e = assertThrows(IllegalArgumentException.class,
                () -> new CaesarCipher("!abc"));
        assertEquals("First character of 'key' argument not in ALPHABET", e.getMessage());
    }

    @Test
    void testConstructorInt() {
        CaesarCipher cc;
        for (int n = -2; n < 3; ++n) {
            int ccShift = n * CharacterCipher.ALPHABET.length();
            cc = new CaesarCipher(ccShift - 1);
            assertEquals("WXYZAB", cc.encrypt("XYZABC"));
            cc = new CaesarCipher(ccShift);
            assertEquals("XYZABC", cc.encrypt("XYZABC"));
            cc = new CaesarCipher(ccShift + 1);
            assertEquals("YZABCD", cc.encrypt("XYZABC"));
        }
    }

    @Test
    void testConstructorString() {
        assertThrows(IllegalArgumentException.class,
                () -> new CaesarCipher(null));
        assertThrows(IllegalArgumentException.class,
                () -> new CaesarCipher(""));
        assertThrows(IllegalArgumentException.class,
                () -> new CaesarCipher(" "));
        assertThrows(IllegalArgumentException.class,
                () -> new CaesarCipher("x"));
        assertThrows(IllegalArgumentException.class,
                () -> new CaesarCipher("%"));
        assertThrows(IllegalArgumentException.class,
                () -> new CaesarCipher("5"));
        assertThrows(IllegalArgumentException.class,
                () -> new CaesarCipher(" SECRET"));

        String[] testkeys =
                {"A", "A*", "AB", "P", "P*", "PQ", "Z", "Z*", "ZA"};
        for (String key : testkeys) {
            CaesarCipher cc = new CaesarCipher(key);
            // "A" should encrypt to first letter of key.
            assertEquals(key.substring(0, 1), cc.encrypt("A"));
        }

    }

    /**
     * Test prep(). Should behave exactly as CharacterCipher.clean().
     */
    @Test
    void testPrep() {
        CaesarCipher cc = new CaesarCipher(10);     // arbitrary key.
        String[] testStrs = {
                null, "", " ", " a ", " a b ", " a BbB c "
        };
        for (String ts : testStrs) {
            assertEquals(CharacterCipher.clean(ts), cc.prep(ts));
        }
    }

    @Test
    void testEdgeCases() {
        CaesarCipher cc = new CaesarCipher(1);
        assertEquals("", cc.decrypt(""));

        String singleCharEncrypted = cc.encrypt("A");
        assertNotNull(singleCharEncrypted);
    }
}