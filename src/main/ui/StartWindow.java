package ui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

/**
 * Represents the start window of the basketball team management application,
 * displaying an image while loading the application and transitioning to the main window after a set time.
 */
public class StartWindow extends JFrame {
    private ImageIcon imageIcon;
    private Timer timer;

    /**
     * Modifies: This instance of StartWindow
     * Effects: Initializes the StartWindow and sets up its components.
     */
    public StartWindow() throws IOException {
        setTitle("Image Display");
        setSize(400, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initializeComponents();
        addComponents();
        setVisible(true);
    }

    /**
     * Modifies: This instance of StartWindow
     * Effects: Loads the image and resizes it to fit the window.
     */
    private void initializeComponents() throws IOException {
        int resizedWidth = 400;
        int resizedHeight = 400;
        Image originalImage = ImageIO.read(new File("images/basketball.jpg"));
        Image resizedImage = originalImage.getScaledInstance(resizedWidth, resizedHeight, Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(resizedImage);
    }

    /**
     * Modifies: This instance of StartWindow
     * Effects: Adds the image label and loading label, starts the timer.
     */
    private void addComponents() {
        JLabel imageLabel = new JLabel(imageIcon, JLabel.CENTER);
        add(imageLabel, BorderLayout.CENTER);

        JLabel loadingLabel = new JLabel("Loading...", JLabel.CENTER);
        loadingLabel.setForeground(Color.WHITE); // Set text color to white
        loadingLabel.setBackground(Color.BLACK); // Set background color to black
        loadingLabel.setOpaque(true); // Make the background opaque
        add(loadingLabel, BorderLayout.SOUTH);

        timer = new Timer(5000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                showMainWindow();
                timer.stop();
            }
        });
        timer.start();
    }

    /**
     * Effects: Disposes the StartWindow and shows the MainWindow.
     */
    private void showMainWindow() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new MainWindow();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Effects: Starts the application by creating a new instance of StartWindow.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new StartWindow();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
