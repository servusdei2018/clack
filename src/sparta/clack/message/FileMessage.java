package sparta.clack.message;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 * This class represents messages containing the name and
 * contents of a text file.
 *
 * @author D. Tuinstra, adapted from work by Soumyabrata Dey.
 */
public class FileMessage extends Message {

    private String filePath;
    private String fileSaveAsName;
    private String fileContents;

    /**
     * Constructs a FileMessage object with a given username
     * and file paths. It does not read in the file contents --
     * that must be done with readFile(). The fileSaveAsPath is
     * not used in its entirety: only the filename portion of
     * is kept and used when saving the message's file contents.
     *
     * @param username       name of user for this message.
     * @param filePath       where to find the file to read.
     * @param fileSaveAsPath the filename portion of this is used when saving the file.
     */
    public FileMessage(String username, String filePath, String fileSaveAsPath) {
        super(username, MSGTYPE_FILE);
        this.filePath = filePath;
        this.fileSaveAsName = Paths.get(fileSaveAsPath).getFileName().toString();
        this.fileContents = "";
    }

    /**
     * Constructs a FileMessage object with a given username,
     * and a given filePath to give both the reading and saving
     * location of the file. It does not read in the file contents --
     * that must be done with readFile().
     *
     * @param username name of user for this message.
     * @param filePath where to find the file to read, and where to write it.
     */
    public FileMessage(String username, String filePath) {
        this(username, filePath, filePath);
    }

    /**
     * Get the path, on the local file system, of the file to read.
     *
     * @return the path to the file to read.
     */
    public String getFilePath() {
        return this.filePath;
    }

    /**
     * Retrieves the contents of the file associated with this message.
     *
     * @return the contents of the file as a String. If the file has not
     *         been read yet, this may return null.
     */
    public String getFileContents() {
        return this.fileContents;
    }

    /**
     * Get the path where the file is to be written.
     *
     * @return the path where the file is to be written.
     */
    public String getFileSaveAsName() {
        return this.fileSaveAsName;
    }

    /**
     * Set the path where the file-to-read is located. This does not
     * cause the file to be read -- that must be done with readFile().
     *
     * @param filePath new file name to associate with this message.
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Set the name for the file to be written. This does not
     * cause the file to be written -- that must be done with
     * writeFile(). It is an IllegalArgument exception if the
     * fileSaveAsName contains path components.
     *
     * @throws IllegalArgumentException if fileSaveAsName contains path components
     */
    public void setFileSaveAsName(String fileSaveAsName) {
        try {
            this.fileSaveAsName = Paths.get(fileSaveAsName).getFileName().toString();
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(fileSaveAsName + ": String contains path components.", e.getCause());
        }
    }

    /**
     * Returns a three-element array of String. The first element is
     * the current filePath value, the second is the current
     * fileSaveAsName value, and the third is the current
     * fileContents value. The method does <b><em>not</em></b> read
     * the file named by filename -- that must be done with readFile().
     *
     * @return the current values of filePath, fileSaveAsName, and fileContents.
     */
    @Override
    public String[] getData() {
        return new String[] {filePath, fileSaveAsName, fileContents};
    }

    /**
     * Read contents of file 'fileName' into this message's fileContents.
     *
     * @throws IOException if the file named by this.fileName does
     *                     not exist or cannot be opened for reading.
     */
    public void readFile() throws IOException {
        this.fileContents = Files.readString(Paths.get(this.getFilePath()));
    }

    /**
     * Write this message's fileContents to the local Clack directory.
     *
     * @throws FileNotFoundException if file cannot be found or created,
     *                               or opened for writing.
     */
    public void writeFile() throws FileNotFoundException {
        Path savePath = Paths.get(this.getFileSaveAsName());
        try {
            Files.writeString(savePath, this.fileContents);
        } catch (IOException e) {
            throw new FileNotFoundException("Could not write to file: " + savePath + " - " + e.getMessage());
        }
    }

    /**
     * Returns a string representation of this TextMessage object.
     * <p>
     *
     *
     * The format of the string is:
     * <pre>
     * {class=FileMessage|super.toString()|filePath=file-path|fileSaveAsName=file-save-as-name|fileContents=file-contents}
     * </pre>
     * where {@code super.toString()} is the string representation
     * from the superclass, {@code file-path} is the file path, {@code file-save-as-name}
     * is the name to save the file as, and {@code file-contents} are the contents of the file.
     * </p>
     *
     * @return a string representation of this FileMessage object.
     */
    @Override
    public String toString() {
        return String.format("{class=FileMessage|"
                +"%s|"
                +"filePath=%s|"
                +"fileSaveAsName=%s|"
                +"fileContents=%s"
                +"}",
                super.toString(), filePath, fileSaveAsName, fileContents);
    }

    @Override
    public boolean equals(Object o) {
        // Guard against nulls
        if (o == null) {
            return false;
        }
        // Check for Message equality
        Message msg = (Message) o;
        if (!msg.getUsername().equals(this.getUsername())) {
            return false;
        }
        // Check for FileMessage equality
        FileMessage obj = (FileMessage) o;
        return Arrays.equals(this.getData(), obj.getData());
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }

}