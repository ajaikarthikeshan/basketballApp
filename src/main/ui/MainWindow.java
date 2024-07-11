package ui;

import model.Team;
import persistence.JsonReader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Creates the main window of the basketball team management application,
 * allowing users to create a new team, load an existing team, or quit the application.
 */

public class MainWindow extends JFrame {
    private static final String JSON_STORE = "./data/team.json";
    private JButton createNewTeamButton;
    private JButton loadTeamButton;
    private JButton quitButton;
    private JsonReader jsonReader;

    /**
     * Modifies: This instance of MainWindow
     * Effects: Initializes the MainWindow and sets up its components.
     */
    public MainWindow() throws IOException {
        setTitle("Basketball Team Management");
        setSize(400, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initializeComponents();
        layoutComponents();
        addEventListeners();
        setVisible(true);
    }

    /**
     * Modifies: This instance of MainWindow
     * Effects: Initializes the buttons for creating a new team,
     *          loading an existing team, and quitting the application.
     */
    private void initializeComponents() throws IOException {
        createNewTeamButton = new JButton("Create New Team");
        loadTeamButton = new JButton("Load Existing Team");
        quitButton = new JButton("Quit");

        createNewTeamButton.setPreferredSize(new Dimension(200, 30));
        loadTeamButton.setPreferredSize(new Dimension(200, 30));
        quitButton.setPreferredSize(new Dimension(200, 30));
    }

    /**
     * Modifies: This instance of MainWindow
     * Effects: Sets up the layout of the buttons in a vertical arrangement.
     */
    private void layoutComponents() {
        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 10, 10)); // 3 rows, 1 column
        buttonPanel.add(createNewTeamButton);
        buttonPanel.add(loadTeamButton);
        buttonPanel.add(quitButton);
        add(buttonPanel, BorderLayout.CENTER);
    }

    /**
     * Modifies: This instance of MainWindow
     * Effects: Adds action listeners to the buttons for performing various actions.
     */
    private void addEventListeners() {
        createNewTeamButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new TeamWindow();
            }
        });

        loadTeamButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                loadTeamData();
            }
        });

        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    /**
     * Effects: Loads team data from a JSON file and opens the HomeWindow with the loaded team.
     */
    private void loadTeamData() {
        try {
            jsonReader = new JsonReader(JSON_STORE);
            Team team = jsonReader.readTeam();
            new HomeWindow(team);
        } catch (IOException e) {
            System.out.println("Team data file not found. Please create a new team.");
        }
    }

    /**
     * Effects: Starts the application by creating a new instance of MainWindow.
     */
    public static void main(String[] args) {
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
}
