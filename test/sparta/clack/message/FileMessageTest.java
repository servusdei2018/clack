package sparta.clack.message;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class FileMessageTest {
    FileMessage msg;
    final String USERNAME = "the user";
    Path tempFile;
    final String TEST_FILE_CONTENTS = "This is a test file for FileMessage.";
    final String CUSTOM_FILE_NAME = "customTestFileName.txt";

    @BeforeEach
    void setUp() throws IOException {
        tempFile = Files.createTempFile("testfile", ".txt");
        Files.write(tempFile, TEST_FILE_CONTENTS.getBytes());
        msg = new FileMessage(USERNAME, tempFile.toString());
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(tempFile);
    }

    @Test
    void testFileMessageConstructorWithDefaultFileName() throws IOException {
        assertEquals(TEST_FILE_CONTENTS, msg.getFileContents());
        assertEquals(tempFile.getFileName().toString(), msg.getFileName());
    }

    @Test
    void testFileMessageConstructorWithCustomFileName() throws IOException {
        FileMessage msgWithCustomName = new FileMessage(USERNAME, tempFile.toString(), CUSTOM_FILE_NAME);
        assertEquals(TEST_FILE_CONTENTS, msgWithCustomName.getFileContents());
        assertEquals(CUSTOM_FILE_NAME, msgWithCustomName.getFileName());
    }

    @Test
    void testGetFileContents() {
        assertEquals(TEST_FILE_CONTENTS, msg.getFileContents());
    }

    @Test
    void testGetFileName() {
        assertEquals(tempFile.getFileName().toString(), msg.getFileName());
    }

    @Test
    void testFileMessageInvalidPath() {
        assertThrows(IOException.class, () -> {
            new FileMessage(USERNAME, "invalid/path/to/file.txt");
        });
    }

    @Test
    void testFileMessageEmptyFile() throws IOException {
        Path emptyFile = Files.createTempFile("emptyfile", ".txt");
        Files.write(emptyFile, "".getBytes());

        FileMessage emptyFileMessage = new FileMessage(USERNAME, emptyFile.toString());
        assertEquals("", emptyFileMessage.getFileContents());
        assertEquals(emptyFile.getFileName().toString(), emptyFileMessage.getFileName());

        Files.deleteIfExists(emptyFile);
    }
}
