package sparta.clack.ui;

import javax.swing.*;
import java.awt.*;

/**
 * The main UI class for the Clack client application.
 * <p>
 * This class is responsible for creating and displaying the main window of the client,
 * which includes setting up the layout and components, chat interface, input fields,
 * and panels. The window is used for user interaction with the Clack client.
 * </p>
 */
public class ClientUI {
    /**
     * Launches the main user interface for the Clack client.
     */
    public void run() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createAndShowGUI();
            }
        });
    }

    /**
     * Initializes and displays the main application window and its associated components.
     */
    private void createAndShowGUI() {
        JFrame frame = new JFrame("Clack");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());

        JButton testButton = new JButton("Clickity clack!");
        frame.add(testButton, BorderLayout.CENTER);

        frame.setVisible(true);
    }
}
