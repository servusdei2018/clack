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
    void encrypt() {
        String msg = "THE QUICK BROWN FOX JUMPED OVER THE LAZY POODLE";
        VignereCipher vc = new VignereCipher("KEY");
        assertEquals("DLCAYGMOZBSUXJMHNSWTCNSTOVRRIJKDWZSMNPC",
                vc.encrypt(vc.prep(msg)));
    }

    @Test
    void decrypt() {
        String msg = "DLCAYGMOZBSUXJMHNSWTCNSTOVRRIJKDWZSMNPC";
        VignereCipher vc = new VignereCipher("KEY");
        assertEquals(vc.prep("THE QUICK BROWN FOX JUMPED OVER THE LAZY POODLE"),
                vc.decrypt(msg));
    }
}
