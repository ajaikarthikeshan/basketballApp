package ui;

import model.EventLog;
import model.Team;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.Iterator;
import model.Event;
import model.EventLog;

/**
 * Creates the home window of the application, allowing users to navigate to different functionalities.
 */
public class HomeWindow extends JFrame {
    private static final String JSON_STORE = "./data/team.json";
    private Team team;
    private JButton addPlayerButton;
    private JButton viewPlayerListButton;
    private JButton gamesMenuButton;
    private JButton trainingSessionMenuButton;
    private JButton quitButton;
    private JsonWriter jsonWriter;

    /**
     * Constructor for the HomeWindow class.
     *
     * @param team The team for which the HomeWindow is created.
     * Requires: None
     * Modifies: This instance of HomeWindow
     * Effects: Initializes the HomeWindow with the specified team.
     */
    public HomeWindow(Team team) {
        this.team = team;

        setTitle("Home");
        setSize(600, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initializeComponents();
        layoutComponents();
        addEventListeners();
        addMoreEventListeners();

        setVisible(true);
    }

    /**
     * Modifies: This instance of HomeWindow
     * Effects: Initializes the buttons for adding a player, viewing player list,
     *          accessing the games menu, accessing the training session menu, and quitting the application.
     */
    private void initializeComponents() {
        addPlayerButton = new JButton("Add Player");
        viewPlayerListButton = new JButton("View Player List");
        gamesMenuButton = new JButton("Games Menu");
        trainingSessionMenuButton = new JButton("Training Session Menu");
        quitButton = new JButton("Quit");

        addPlayerButton.setPreferredSize(new Dimension(150, 30));
        viewPlayerListButton.setPreferredSize(new Dimension(150, 30));
        gamesMenuButton.setPreferredSize(new Dimension(150, 30));
        trainingSessionMenuButton.setPreferredSize(new Dimension(150, 30));
        quitButton.setPreferredSize(new Dimension(150, 30));
    }

    /**
     * Modifies: This instance of HomeWindow
     * Effects: Sets up the layout of the buttons in a vertical arrangement.
     */
    private void layoutComponents() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 1, 10, 10));
        buttonPanel.add(addPlayerButton);
        buttonPanel.add(viewPlayerListButton);
        buttonPanel.add(gamesMenuButton);
        buttonPanel.add(trainingSessionMenuButton);
        buttonPanel.add(quitButton);
        add(buttonPanel, BorderLayout.CENTER);
    }

    /**
     * Modifies: This instance of HomeWindow
     * Effects: Adds action listeners to the buttons for performing various actions.
     */
    private void addEventListeners() {
        addPlayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddPlayerWindow(team);
                dispose();
            }
        });
        viewPlayerListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ViewPlayerListWindow(team);
                dispose();
            }
        });
        gamesMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GamesMenuWindow(team);
                dispose();
            }
        });
    }

    /**
     * Modifies: This instance of HomeWindow
     * Effects: Adds action listeners to buttons for accessing the training session menu
     *          and quitting the application.
     */
    private void addMoreEventListeners() {
        trainingSessionMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TrainingSessionWindow(team);
                dispose();
            }
        });
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmSaveBeforeQuitting();
            }
        });
    }

    /**
     * Effects: Displays a confirmation dialog for saving before quitting
     *          and takes appropriate action based on user choice.
     */
    private void confirmSaveBeforeQuitting() {
        int choice = JOptionPane.showConfirmDialog(null,
                "Do you want to save before quitting?",
                "Confirm Quit",
                JOptionPane.YES_NO_CANCEL_OPTION);

        if (choice == JOptionPane.YES_OPTION) {
            saveTeamData();
        } else if (choice == JOptionPane.NO_OPTION) {
            System.exit(0);
        }
    }

    /**
     * Effects: Saves the team data to a JSON file and exits the application.
     */
    private void saveTeamData() {
        if (team != null) {
            try {
                jsonWriter = new JsonWriter(JSON_STORE);
                jsonWriter.open();
                jsonWriter.write(team);
                jsonWriter.close();
                System.out.println("Team data saved successfully to " + JSON_STORE);
                printLoggedEvents();
                System.exit(0);
            } catch (FileNotFoundException e) {
                System.out.println("Error saving team data: " + e.getMessage());
            }
        }
    }

    // EFFECTS: Method to print all logged events
    private void printLoggedEvents() {
        EventLog eventLog = EventLog.getInstance();

        Iterator<Event> eventIterator = eventLog.iterator();

        System.out.println("Logged Events:");
        while (eventIterator.hasNext()) {
            Event event = eventIterator.next();
            System.out.println(event.getDescription());
        }
    }

    /**
     * Effects: Starts the application by creating a new instance of HomeWindow.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new HomeWindow(null);
            }
        });
    }
}
