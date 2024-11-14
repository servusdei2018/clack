package sparta.clack.cipher;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PlayfairCipherTest {

    @Test
    void badConstructorArgs() {
        Exception e;

        e = assertThrows(IllegalArgumentException.class,
                () -> new PlayfairCipher(null));
        assertEquals("Need a non-null, non-empty string", e.getMessage());

        e = assertThrows(IllegalArgumentException.class,
                () -> new PlayfairCipher(""));
        assertEquals("Need a non-null, non-empty string", e.getMessage());

        PlayfairCipher pf = new PlayfairCipher("KEY123");
        assertNotNull(pf); // Should not throw any exception, non-alphabet characters will be ignored
    }

    @Test
    void encrypt() {
        String msg = "THE QUICK BROWN FOX JUMPED OVER THE LAZY POODLE";
        PlayfairCipher pf = new PlayfairCipher("KEY");
        assertEquals("ZODVKPICYTMZMGNZPKIRDLLZYQZODQBXKRNZLQAV",
                pf.encrypt(pf.prep(msg)));
    }

    @Test
    void decrypt() {
        String msg = "ZODVKPICYTMZMGNZPKIRDLLZYQZODQBXKRNZLQAV";
        PlayfairCipher pf = new PlayfairCipher("KEY");
        assertEquals(pf.prep("THE QUICK BROWN FOX JUMPED OVER THE LAZY POODLE"),
                pf.decrypt(msg));
    }

    @Test
    void testPrep() {
        PlayfairCipher pf = new PlayfairCipher("KEY");

        String cleartext = "THE QUICK BROWN FOX LEAPED OVER THE LAZY POODLE";
        String preptext = pf.prep(cleartext);
        assertEquals("THEQUICKBROWNFOXLEAPEDOVERTHELAZYPOXDLEX", preptext);

        cleartext = "JUSTICE FOR ALL";
        preptext = pf.prep(cleartext);
        assertEquals("IUSTICEFORALLX", preptext);

        cleartext = "BALLOON";
        preptext = pf.prep(cleartext);
        assertEquals("BALXOXNX", preptext);
    }

    @Test
    void testEdgeCases() {
        PlayfairCipher pf = new PlayfairCipher("KEY");
        assertEquals("", pf.decrypt(""));

        String singleCharEncrypted = pf.encrypt(pf.prep("A"));
        assertNotNull(singleCharEncrypted);
    }
}
