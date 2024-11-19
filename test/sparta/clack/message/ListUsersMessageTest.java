package sparta.clack.message;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;

import static java.time.Duration.between;
import static org.junit.jupiter.api.Assertions.*;

class ListUsersMessageTest {
    ListUsersMessage msg;
    Instant now;

    final String USERNAME = "the user";

    @BeforeEach
    void setUp() {
        msg = new ListUsersMessage(USERNAME);
    }

    @Test
    void getMsgType() {
        assertEquals(MsgType.LISTUSERS, msg.getMsgType());
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
    void testToString() {
        ListUsersMessage lm = new ListUsersMessage("user");
        String expected = "ListUsersMessage{"
                + "Message{msgTypeEnum=LISTUSERS"
                + ", timestamp=omitted"
                + ", username='user'"
                + "}}";
        String actual = lm.toString().replaceFirst(
                "timestamp=.*, username=",
                "timestamp=omitted, username=");
        assertEquals(expected, actual);
    }
}