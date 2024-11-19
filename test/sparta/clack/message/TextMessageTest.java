package sparta.clack.message;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;

import static java.time.Duration.between;
import static org.junit.jupiter.api.Assertions.*;

class TextMessageTest {
    TextMessage msg;
    Instant now;

    final String USERNAME = "the user";

    @BeforeEach
    void setUp() {
        msg = new TextMessage(USERNAME, "TEST");
    }

    @Test
    void getMsgType() {
        assertEquals(MsgType.TEXT, msg.getMsgType());
    }

    @Test
    void getTimestamp() {
        now = Instant.now();
        Duration duration = between(msg.getTimestamp(), now);
        long timeDiff = Math.abs(duration.toSeconds());
        assertTrue(timeDiff <= 1);
    }

    @Test
    void getUsername() {
        assertEquals(USERNAME, msg.getUsername());
    }

    @Test
    void getText() {
        TextMessage hm = new TextMessage("user", "this is the text");
        assertEquals("this is the text", hm.getText());
    }

    @Test
    void testToString() {
        TextMessage hm = new TextMessage("user", "this is the text");
        String expected = "TextMessage{"
                + "Message{msgTypeEnum=TEXT"
                + ", timestamp=omitted"
                + ", username='user'"
                + "}, text='this is the text'}";
        String actual = hm.toString().replaceFirst(
                "timestamp=.*, username=",
                "timestamp=omitted, username=");
        assertEquals(expected, actual);
    }
}