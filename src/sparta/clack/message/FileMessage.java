package sparta.clack.message;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Represents a message containing a file's content for upload.
 * <p>
 * This class is used to send files from a client to a server. The file is read from the file path
 * specified, and the contents are stored as a string. Optionally, the file can be saved under a different
 * name.
 */
public class FileMessage extends Message {
    /**
     * The contents of the file being uploaded.
     */
    private final String fileContents;

    /**
     * The name of the file being uploaded.
     */
    private final String fileName;

    /**
     * Constructs a FileMessage object, reading the file contents from the specified path.
     *
     * @param username     the name of the user sending the file.
     * @param fileReadPath the path of the file to read and upload.
     * @throws IOException if the file cannot be read from the given path.
     */
    public FileMessage(String username, String fileReadPath) throws IOException {
        super(username, MsgType.FILE);
        this.fileName = Paths.get(fileReadPath).getFileName().toString();
        this.fileContents = new String(Files.readAllBytes(Paths.get(fileReadPath)));
    }

    /**
     * Constructs a FileMessage object, reading the file contents from the specified path
     * and optionally saving it with a different name.
     *
     * @param username      the name of the user sending the file.
     * @param fileReadPath  the path of the file to read and upload.
     * @param fileSaveName  the name to save the file as (if different from the original file name).
     * @throws IOException  if the file cannot be read from the given path.
     */
    public FileMessage(String username, String fileReadPath, String fileSaveName) throws IOException {
        super(username, MsgType.FILE);
        this.fileName = fileSaveName;
        this.fileContents = new String(Files.readAllBytes(Paths.get(fileReadPath)));
    }

    /**
     * Gets the contents of the file.
     *
     * @return the file contents as a string.
     */
    public String getFileContents() {
        return this.fileContents;
    }

    /**
     * Gets the name of the file.
     *
     * @return the file name.
     */
    public String getFileName() {
        return this.fileName;
    }

    /**
     * Returns a string representation of this FileMessage object.
     *
     * @return a string representation of this FileMessage object.
     */
    @Override
    public String toString() {
        return "FileMessage{"
                + super.toString()
                + ", fileName='" + fileName + '\''
                + ", fileContents=" + (fileContents.length() > 50 ? fileContents.substring(0, 50) + "..." : fileContents)
                + '}';
    }
}
