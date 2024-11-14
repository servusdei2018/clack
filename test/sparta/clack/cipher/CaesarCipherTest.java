package sparta.clack.cipher;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CaesarCipherTest {
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
    void encrypt() {
        String msg = "THE QUICK BROWN FOX JUMPED OVER THE LAZY POODLE";
        CaesarCipher cc = new CaesarCipher(1);
        assertEquals("UIFRVJDLCSPXOGPYKVNQFEPWFSUIFMBAZQPPEMF",
                cc.encrypt(cc.prep(msg)));
    }

    @Test
    void decrypt() {
        String msg = "UIFRVJDLCSPXOGPYKVNQFEPWFSUIFMBAZQPPEMF";
        CaesarCipher cc = new CaesarCipher(1);
        assertEquals(cc.prep("THE QUICK BROWN FOX JUMPED OVER THE LAZY POODLE"),
                cc.decrypt(msg));
    }

    @Test
    void testEdgeCases() {
        CaesarCipher cc = new CaesarCipher(1);
        assertEquals("", cc.decrypt(""));

        String singleCharEncrypted = cc.encrypt("A");
        assertNotNull(singleCharEncrypted);
    }
}