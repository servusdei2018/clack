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
        for (OptionEnum opt : OptionEnum.values()) {
            OptionMessage om = new OptionMessage("user", opt, "setting");
            assertEquals(opt, om.getOption());
        }
    }

    @Test
    void testGetUsername() {
        assertEquals(USERNAME, msg.getUsername(), "The username should be 'testUser'.");
    }

    @Test
    void testGetValue() {
        OptionEnum opt = OptionEnum.CIPHER_KEY;
        String[] values = {null, "", "playfair"};
        for (String v : values) {
            OptionMessage om = new OptionMessage("user", opt, v);
            assertEquals(v, om.getValue());
        }
    }

    @Test
    void testToString() {
        OptionEnum opt = OptionEnum.CIPHER_KEY;
        String[] values = {null, "", "playfair"};
        for (String v : values) {
            OptionMessage om = new OptionMessage("user", opt, v);
            String expected = "OptionMessage{"
                    + "Message{msgTypeEnum=OPTION"
                    + ", timestamp=omitted"
                    + ", username='user'"
                    + "}"
                    + ", option=" + opt.toString()
                    + ", value='" + v
                    + "'}";
            String actual = om.toString().replaceFirst(
                    "timestamp=.*, username=",
                    "timestamp=omitted, username=");
            assertEquals(expected, actual);
        }
    }
}
