package ui;

import model.Player;
import model.Team;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Represents a window for adding a player to a team.
 */
public class AddPlayerWindow extends JFrame {
    private Team team;
    private JTextField nameField;
    private JTextField ageField;
    private JTextField positionField;
    private JTextField jerseyNumberField;
    private JButton addButton;

    /**
     * Constructor for the AddPlayerWindow class.
     *
     * @param team The team to which the player will be added.
     * Effects: Initializes the AddPlayerWindow with the specified team.
     */
    public AddPlayerWindow(Team team) {
        this.team = team;

        setTitle("Add Player");
        setSize(400, 200);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        initializeComponents();
        layoutComponents();
        addEventListeners();

        setVisible(true);
    }

    /**
     * Modifies: This instance of AddPlayerWindow
     * Effects: Initializes the text fields and button.
     */
    private void initializeComponents() {
        nameField = new JTextField(20);
        ageField = new JTextField(5);
        positionField = new JTextField(10);
        jerseyNumberField = new JTextField(5);
        addButton = new JButton("Add Player");
    }

    /**
     * Modifies: This instance of AddPlayerWindow
     * Effects: Sets up the layout of the components in a grid pattern.
     */
    private void layoutComponents() {
        JPanel panel = new JPanel(new GridLayout(5, 2));
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Age:"));
        panel.add(ageField);
        panel.add(new JLabel("Position:"));
        panel.add(positionField);
        panel.add(new JLabel("Jersey Number:"));
        panel.add(jerseyNumberField);
        panel.add(new JLabel());
        panel.add(addButton);

        add(panel);
    }

    /**
     * Modifies: This instance of AddPlayerWindow
     * Effects: Adds an action listener to the "Add Player" button.
     */
    private void addEventListeners() {
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addPlayer();
            }
        });
    }

    /**
     * Adds a player to the team based on the inputted information.
     * Requires: None
     * Modifies: This instance of AddPlayerWindow, the team
     * Effects: Creates a new Player object with the inputted details,
     *          adds it to the team, and displays a confirmation message.
     */
    private void addPlayer() {
        String playerName = nameField.getText();
        int playerAge = Integer.parseInt(ageField.getText());
        String playerPosition = positionField.getText();
        int jerseyNumber = Integer.parseInt(jerseyNumberField.getText());

        Player player = new Player(playerName, playerAge, playerPosition);
        player.setJerseyNumber(jerseyNumber);

        team.addMember(player);
        JOptionPane.showMessageDialog(this, "Player added successfully to the team: " + player.getName());
        dispose();
        new HomeWindow(team);
    }
}
