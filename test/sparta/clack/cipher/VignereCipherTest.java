package sparta.clack.cipher;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class VignereCipherTest {

    @Test
    void badConstructorArgs() {
        Exception e;

        e = assertThrows(IllegalArgumentException.class,
                () -> new VignereCipher(null));
        assertEquals("Need a non-null, non-empty string", e.getMessage());

        e = assertThrows(IllegalArgumentException.class,
                () -> new VignereCipher(""));
        assertEquals("Need a non-null, non-empty string", e.getMessage());

        e = assertThrows(IllegalArgumentException.class,
                () -> new VignereCipher("KEY123"));
        assertEquals("Key must contain only uppercase alphabetic characters", e.getMessage());
    }

    @Test
    void testConstructor() {
        assertThrows(IllegalArgumentException.class,
                () -> new VignereCipher(null));
        assertThrows(IllegalArgumentException.class,
                () -> new VignereCipher(""));
        assertThrows(IllegalArgumentException.class,
                () -> new VignereCipher("AB CD"));
        assertThrows(IllegalArgumentException.class,
                () -> new VignereCipher("ABcD"));
        assertDoesNotThrow(() -> new VignereCipher("A"));
        assertDoesNotThrow(() -> new VignereCipher("AA"));
        assertDoesNotThrow(() -> new VignereCipher("ABC"));
        assertDoesNotThrow(() -> new VignereCipher("Z"));
        assertDoesNotThrow(() -> new VignereCipher("XYZ"));
        assertDoesNotThrow(() -> new VignereCipher(CharacterCipher.ALPHABET + CharacterCipher.ALPHABET));
    }

    @Test
    void prep() {
    }

    @Test
    void encrypt() {
        String msg = "THE QUICK BROWN FOX JUMPED OVER THE LAZY POODLE";
        VignereCipher vc = new VignereCipher("KEY");
        assertEquals("DLCAYGMOZBSUXJMHNSWTCNSTOVRRIJKDWZSMNPC",
                vc.encrypt(vc.prep(msg)));

        vc = new VignereCipher(CharacterCipher.ALPHABET);
        assertEquals(CharacterCipher.ALPHABET, vc.encrypt("A".repeat(CharacterCipher.ALPHABET.length())));

        vc = new VignereCipher("EDGARALLANPOE");
        assertEquals("XKKGFLOMUT", vc.encrypt("THEGOLDBUG"));
        assertEquals("UXUTYTSPRNKSRRHBEIMZCE", vc.encrypt("QUOTHTHERAVENNEVERMORE"));
    }

    @Test
    void decrypt() {
        String msg = "DLCAYGMOZBSUXJMHNSWTCNSTOVRRIJKDWZSMNPC";
        VignereCipher vc = new VignereCipher("KEY");
        assertEquals(vc.prep("THE QUICK BROWN FOX JUMPED OVER THE LAZY POODLE"),
                vc.decrypt(msg));

        vc = new VignereCipher(CharacterCipher.ALPHABET);
        assertEquals("A".repeat(CharacterCipher.ALPHABET.length()), vc.decrypt(CharacterCipher.ALPHABET));

        vc = new VignereCipher("EDGARALLANPOE");
        assertEquals("THEGOLDBUG", vc.decrypt("XKKGFLOMUT"));
        assertEquals("QUOTHTHERAVENNEVERMORE",
                vc.decrypt("UXUTYTSPRNKSRRHBEIMZCE"));
    }

    @Test
    void testEdgeCases() {
        VignereCipher vc = new VignereCipher("KEY");
        assertEquals("", vc.decrypt(""));

        String singleCharEncrypted = vc.encrypt("A");
        assertNotNull(singleCharEncrypted);
    }
}
