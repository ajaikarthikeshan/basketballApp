package ui;

import model.Team;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Represents the window for creating a new basketball team, allowing the user
 * to input the team name, lower bound age, and upper bound age.
 */
public class TeamWindow extends JFrame {
    private JTextField teamNameField;
    private JTextField lowerBoundAgeField;
    private JTextField upperBoundAgeField;
    private JButton createTeamButton;
    private Team team;

    /**
     * Modifies: This instance of TeamWindow
     * Effects: Initializes the TeamWindow and sets up its components.
     */
    public TeamWindow() {
        setTitle("Create New Team");
        setSize(400, 200);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        initializeComponents();
        layoutComponents();
        addEventListeners();

        setVisible(true);
    }

    /**
     * Modifies: This instance of TeamWindow
     * Effects: Initializes the text fields and button.
     */
    private void initializeComponents() {
        teamNameField = new JTextField(20);
        lowerBoundAgeField = new JTextField(5);
        upperBoundAgeField = new JTextField(5);
        createTeamButton = new JButton("Create Team");
    }

    /**
     * Modifies: This instance of TeamWindow
     * Effects: Sets up the grid layout and adds components to the panel.
     */
    private void layoutComponents() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));
        panel.add(new JLabel("Team Name:"));
        panel.add(teamNameField);
        panel.add(new JLabel("Lower Bound Age:"));
        panel.add(lowerBoundAgeField);
        panel.add(new JLabel("Upper Bound Age:"));
        panel.add(upperBoundAgeField);
        panel.add(new JLabel()); // Empty label for spacing
        panel.add(createTeamButton);

        add(panel, BorderLayout.CENTER);
    }

    /**
     * Modifies: This instance of TeamWindow
     * Effects: Adds an ActionListener to createTeamButton.
     */
    private void addEventListeners() {
        createTeamButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String teamName = teamNameField.getText();
                int lowerBoundAge = Integer.parseInt(lowerBoundAgeField.getText());
                int upperBoundAge = Integer.parseInt(upperBoundAgeField.getText());
                team = new Team(teamName, lowerBoundAge, upperBoundAge);
                dispose();
                new HomeWindow(team);
            }
        });
    }
}
