package sparta.clack.message;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;

import static java.time.Duration.between;
import static org.junit.jupiter.api.Assertions.*;

class HelpMessageTest {
    HelpMessage msg;
    Instant now;

    final String USERNAME = "the user";

    @BeforeEach
    void setUp() {
        msg = new HelpMessage(USERNAME);
    }

    @Test
    void getMsgType() {
        assertEquals(MsgType.HELP, msg.getMsgType());
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
}
