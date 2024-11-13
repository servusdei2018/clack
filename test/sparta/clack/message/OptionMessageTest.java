package sparta.clack.message;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;

import static java.time.Duration.between;
import static org.junit.jupiter.api.Assertions.*;

class OptionMessageTest {
    OptionMessage msg;
    Instant now;

    final String USERNAME = "the user";
    final OptionEnum OPTION = OptionEnum.CIPHER_KEY;
    final String VALUE = "12345";

    @BeforeEach
    void setUp() {
        msg = new OptionMessage(USERNAME, OPTION, VALUE);
    }

    @Test
    void getMsgType() {
        assertEquals(MsgType.OPTION, msg.getMsgType());
    }

    @Test
    void getTimestamp() {
        now = Instant.now();
        Duration duration = between(msg.getTimestamp(), now);
        long timeDiff = Math.abs(duration.toSeconds());
        assertTrue(timeDiff <= 1);
    }

    @Test
    void testGetOption() {
        assertEquals(OPTION, msg.getOption(), "The option should be CIPHER_KEY.");
    }

    @Test
    void testGetUsername() {
        assertEquals(USERNAME, msg.getUsername(), "The username should be 'testUser'.");
    }

    @Test
    void testGetValue() {
        assertEquals(VALUE, msg.getValue(), "The value should be 12345.");
    }
}
