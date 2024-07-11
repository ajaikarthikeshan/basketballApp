package ui;

import model.Team;
import model.TrainingSession;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Represents the window for managing training sessions, allowing the user to start a new training session,
 * view the schedule of training sessions, and navigate back to the home window.
 */
public class TrainingSessionWindow extends JFrame {
    private Team team;
    private JButton startTrainingButton;
    private JButton viewScheduleButton;
    private JButton backButton;

    /**
     * Requires: Team object
     * Modifies: This instance of TrainingSessionWindow
     * Effects: Initializes the TrainingSessionWindow and sets up its components.
     */
    public TrainingSessionWindow(Team team) {
        this.team = team;

        setTitle("Training Session Menu");
        setSize(400, 200);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        initializeComponents();
        layoutComponents();
        addEventListeners();

        setVisible(true);
    }

    /**
     * Modifies: This instance of TrainingSessionWindow
     * Effects: Initializes the buttons.
     */
    private void initializeComponents() {
        startTrainingButton = new JButton("New Training Session");
        viewScheduleButton = new JButton("View Training Session Schedule");
        backButton = new JButton("Back to Home");
    }

    /**
     * Modifies: This instance of TrainingSessionWindow
     * Effects: Sets up the grid layout and adds components to the panel.
     */
    private void layoutComponents() {
        JPanel panel = new JPanel(new GridLayout(3, 1));
        panel.add(startTrainingButton);
        panel.add(viewScheduleButton);
        panel.add(backButton);
        add(panel, BorderLayout.CENTER);
    }

    /**
     * Modifies: This instance of TrainingSessionWindow
     * Effects: Adds ActionListeners to the buttons.
     */
    private void addEventListeners() {
        startTrainingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createNewTrainingSession();
            }
        });

        viewScheduleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewTrainingSessions();
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new HomeWindow(team);
                dispose();
            }
        });
    }

    /**
     * Effects: Creates a new TrainingSession object with the provided details.
     */
    private TrainingSession getTrainingSessionDetails() {
        JTextField dateField = new JTextField();
        JTextField venueField = new JTextField();
        JTextField durationField = new JTextField();
        JPanel panel = new JPanel(new GridLayout(0, 2));
        panel.add(new JLabel("Date (YYYY-MM-DD):"));
        panel.add(dateField);
        panel.add(new JLabel("Venue:"));
        panel.add(venueField);
        panel.add(new JLabel("Duration (minutes):"));
        panel.add(durationField);
        int result = JOptionPane.showConfirmDialog(null, panel, "Start Training Session", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String dateString = dateField.getText();
            String venue = venueField.getText();
            int durationInMinutes = Integer.parseInt(durationField.getText());
            try {
                Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
                return new TrainingSession(date, venue, durationInMinutes);
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(null, "Invalid date format.");
            }
        }
        return null;
    }

    /**
     * Modifies: This instance of TrainingSessionWindow, Team object
     * Effects: Adds a new training session to the team and displays a confirmation message.
     */
    private void createNewTrainingSession() {
        TrainingSession trainingSession = getTrainingSessionDetails();
        if (trainingSession != null) {
            team.addTrainingSession(trainingSession);
            JOptionPane.showMessageDialog(null, "Training session created successfully:\n"
                    + "Date: " + trainingSession.getDate() + "\n"
                    + "Venue: " + trainingSession.getVenue() + "\n"
                    + "Duration: " + trainingSession.getDurationInMinutes() + " minutes");
        }
    }

    /**
     * Effects: Shows the list of existing training sessions or a message if none exist.
     */
    private void viewTrainingSessions() {
        if (team == null) {
            JOptionPane.showMessageDialog(null, "No team created yet. Please create a team first.");
            return;
        }

        ArrayList<TrainingSession> trainingSessions = team.getTrainingSessions();
        if (trainingSessions.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No training sessions scheduled yet.");
        } else {
            displayTrainingSessions(trainingSessions);
            offerUpdateOptions(trainingSessions);
        }
    }

    /**
     * Effects: Shows the details of all training sessions.
     */
    private void displayTrainingSessions(ArrayList<TrainingSession> trainingSessions) {
        StringBuilder sessionsInfo = new StringBuilder();
        sessionsInfo.append("\nTraining Sessions:\n");
        for (int i = 0; i < trainingSessions.size(); i++) {
            TrainingSession trainingSession = trainingSessions.get(i);
            sessionsInfo.append("Session ").append(i + 1).append(":\n");
            sessionsInfo.append("Date: ").append(trainingSession.getDate()).append("\n");
            sessionsInfo.append("Venue: ").append(trainingSession.getVenue()).append("\n");
            sessionsInfo.append("Duration: ").append(trainingSession.getDurationInMinutes()).append(" minutes\n");
            sessionsInfo.append("Completed: ").append(trainingSession.isCompleted() ? "Yes" : "No").append("\n\n");
        }
        JOptionPane.showMessageDialog(null, sessionsInfo.toString());
    }

    // Effects: Displays a dialog box to offer options for updating a training session
    //          If the user selects "Update", prompts for the session number to update
    //          If a valid session number is entered, calls updateTrainingSession() with the selected session
    //          If the user selects "Go Back", returns without making any changes
    private void offerUpdateOptions(ArrayList<TrainingSession> trainingSessions) {
        String[] options = {"Update", "Go Back"};
        int choice = JOptionPane.showOptionDialog(null, "Select an option:", "Update Training Session",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        if (choice == 0) {
            String sessionNumber = JOptionPane.showInputDialog("Enter session number to update:");
            if (sessionNumber != null && !sessionNumber.isEmpty()) {
                int index = Integer.parseInt(sessionNumber) - 1;
                if (index >= 0 && index < trainingSessions.size()) {
                    updateTrainingSession(trainingSessions.get(index));
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid session number. Please try again.");
                    offerUpdateOptions(trainingSessions);
                }
            }
        }
    }

    // Modifies: trainingSession.completion status
    // Effects: Displays a dialog box to select an option for updating a training session
    //          If "Mark as Completed" is selected, marks the training session as completed
    //          If "Add Attendee" is selected, prompts to add an attendee to the training session
    //          If "Back" is selected, returns to the previous menu
    private void updateTrainingSession(TrainingSession trainingSession) {
        String[] options = {"Mark as Completed", "Add Attendee", "Back"};
        int choice = JOptionPane.showOptionDialog(null, "Select an option:", "Update Training Session",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        switch (choice) {
            case 0:
                trainingSession.markCompleted();
                JOptionPane.showMessageDialog(null, "Training session marked as completed.");
                new TrainingSessionWindow(team);
                break;
            case 1:
                addAttendeeToTrainingSession(trainingSession);
                break;
            case 2:
                break;
            default:
                JOptionPane.showMessageDialog(null, "Invalid command. Please try again.");
                updateTrainingSession(trainingSession);
                break;
        }
    }

    // Modifies: trainingSession.attendees
    // Effects: Prompts the user to enter the name of the attendee to add
    //          If a valid name is entered, adds the attendee to the training session
    //          If an invalid name is entered, displays an error message and prompts again
    //          Calls updateTrainingSession() after adding the attendee
    private void addAttendeeToTrainingSession(TrainingSession trainingSession) {
        String playerName = JOptionPane.showInputDialog("Enter player's name to add as attendee:");
        if (playerName != null && !playerName.isEmpty()) {
            trainingSession.addAttendee(playerName);
            JOptionPane.showMessageDialog(null, playerName + " has been added to the training session.");
        } else {
            JOptionPane.showMessageDialog(null, "Invalid player name. Please try again.");
        }
        updateTrainingSession(trainingSession);
    }
}
