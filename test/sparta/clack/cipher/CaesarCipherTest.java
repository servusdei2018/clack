package sparta.clack.cipher;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CaesarCipherTest {
    @Test
    void badConstructorArgs() {
        Exception e;

        e = assertThrows(IllegalArgumentException.class,
                () -> new CaesarCipher(0));
        assertEquals("key of zero not allowed", e.getMessage());

        e = assertThrows(IllegalArgumentException.class,
                () -> new CaesarCipher(0, "ABCD"));
        assertEquals("key of zero not allowed", e.getMessage());

        e = assertThrows(IllegalArgumentException.class,
                () -> new CaesarCipher(1, null));
        assertEquals("empty alphabet not allowed", e.getMessage());

        e = assertThrows(IllegalArgumentException.class,
                () -> new CaesarCipher(1, ""));
        assertEquals("empty alphabet not allowed", e.getMessage());

        e = assertThrows(IllegalArgumentException.class,
                () -> new CaesarCipher(1, "ABCAD"));
        assertEquals("duplicate chars in alphabet", e.getMessage());
    }

    @Test
    void getAlphabet() {
        CaesarCipher cc = new CaesarCipher(5);
        assertEquals(CaesarCipher.DEFAULT_ALPHABET, cc.getAlphabet());

        cc = new CaesarCipher(-442, "xyz ,.");
        assertEquals("xyz ,.", cc.getAlphabet());
    }

    @Test
    void encrypt() {
        String msg = "The quick, brown fox jumped. Over the lazy poodle!";

        CaesarCipher cc = new CaesarCipher(1);
        // Ignore all but CAPITAL LETTERS.
        assertEquals("Uhe quick, brown fox jumped. Pver the lazy poodle!",
                cc.encrypt(msg));
        // Message in all caps.
        String msgINCAPS = msg.toUpperCase();
        assertEquals("UIF RVJDL, CSPXO GPY KVNQFE. PWFS UIF MBAZ QPPEMF!",
                cc.encrypt(msgINCAPS));
        // Expanded alphabet.
        cc = new CaesarCipher(1, CaesarCipher.DEFAULT_ALPHABET
                + CaesarCipher.DEFAULT_ALPHABET.toLowerCase()
                + " .,");
        assertEquals("Uif.rvjdlA.cspxo.gpy.kvnqfe,.Pwfs.uif.mb z.qppemf!",
                cc.encrypt(msg));
    }

    @Test
    void decrypt() {
        String msg = "The quick, brown fox jumped. Over the lazy poodle!";

        CaesarCipher cc = new CaesarCipher(1);
        // Ignore all but CAPITAL LETTERS.
        assertEquals(msg,
                cc.decrypt("Uhe quick, brown fox jumped. Pver the lazy poodle!"));
        // Message in all caps.
        String msgINCAPS = msg.toUpperCase();
        assertEquals(msgINCAPS,
                cc.decrypt("UIF RVJDL, CSPXO GPY KVNQFE. PWFS UIF MBAZ QPPEMF!"));
        // Expanded alphabet.
        cc = new CaesarCipher(1, CaesarCipher.DEFAULT_ALPHABET
                + CaesarCipher.DEFAULT_ALPHABET.toLowerCase()
                + " .,");
        assertEquals(msg,
                cc.decrypt("Uif.rvjdlA.cspxo.gpy.kvnqfe,.Pwfs.uif.mb z.qppemf!"));

    }
}