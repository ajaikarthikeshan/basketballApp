package ui;

import model.Game;
import model.Player;
import model.Team;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Represents a window for managing games,including scheduling games, viewing game schedule, and recording game outcomes
 */
public class GamesMenuWindow extends JFrame {
    private Team team;
    private JButton scheduleGameButton;
    private JButton viewGameScheduleButton;
    private JButton recordGameOutcomeButton;
    private JButton backButton;

    /**
     * Constructor for the GamesMenuWindow class.
     *
     * @param team The team for which the GamesMenuWindow is created.
     *             Requires: None
     *             Modifies: This instance of GamesMenuWindow
     *             Effects: Initializes the GamesMenuWindow with the specified team.
     */
    public GamesMenuWindow(Team team) {
        this.team = team;

        setTitle("Games Menu");
        setSize(600, 600);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        initializeComponents();
        layoutComponents();
        addEventListeners();

        setVisible(true);
    }

    /**
     * Modifies: This instance of GamesMenuWindow
     * Effects: Initializes the buttons for scheduling games, viewing game schedule,
     * recording game outcomes, and going back to the home window.
     */
    private void initializeComponents() {
        scheduleGameButton = new JButton("Schedule Game");
        viewGameScheduleButton = new JButton("View Game Schedule");
        recordGameOutcomeButton = new JButton("Record Game Outcome");
        backButton = new JButton("Back");
    }

    /**
     * Modifies: This instance of GamesMenuWindow
     * Effects: Sets up the layout of the buttons in a vertical arrangement.
     */
    private void layoutComponents() {
        JPanel panel = new JPanel(new GridLayout(4, 1));

        panel.add(scheduleGameButton);
        panel.add(viewGameScheduleButton);
        panel.add(recordGameOutcomeButton);
        panel.add(backButton);

        add(panel, BorderLayout.CENTER);
    }

    /**
     * Modifies: This instance of GamesMenuWindow
     * Effects: Adds action listeners to the buttons for scheduling games,
     * viewing game schedule, recording game outcomes, and going back to the home window.
     */
    private void addEventListeners() {
        addGameEventListeners();
        addBackButtonListener();
    }

    /**
     * Modifies: This instance of GamesMenuWindow
     * Effects: Adds action listeners to the buttons for scheduling games,
     * viewing game schedule, and recording game outcomes.
     */
    private void addGameEventListeners() {
        scheduleGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                scheduleGame();
            }
        });
        viewGameScheduleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewGameSchedule();
            }
        });
        recordGameOutcomeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                recordGameOutcome();
            }
        });
    }

    /**
     * Modifies: This instance of GamesMenuWindow
     * Effects: Adds an action listener to the back button for returning to the home window.
     */
    private void addBackButtonListener() {
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                goToHomeWindow();
            }
        });
    }

    /**
     * Effects: Disposes the current GamesMenuWindow and opens a new HomeWindow.
     */
    private void goToHomeWindow() {
        dispose();
        new HomeWindow(team);
    }

    /**
     * Modifies: This instance of GamesMenuWindow, the team
     * Effects: Prompts the user to enter details for scheduling a game,
     * adds the game to the team, and displays a confirmation message.
     */
    private void scheduleGame() {
        if (team == null) {
            JOptionPane.showMessageDialog(this, "No team created yet.");
            return;
        }
        Game game = promptGameDetails();
        if (game != null) {
            team.addGame(game);
            JOptionPane.showMessageDialog(this, "Game scheduled - " + game.getOpponentName() + " on " + game.getDate());
        }
    }

    /**
     * Effects: Prompts the user to enter details for scheduling a game,
     * parses the input, and returns a new Game object.
     */
    private Game promptGameDetails() {
        JTextField opponentNameField = new JTextField();
        JTextField dateField = new JTextField();
        JTextField venueField = new JTextField();
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Opponent's Name:"));
        panel.add(opponentNameField);
        panel.add(new JLabel("Date (YYYY-MM-DD):"));
        panel.add(dateField);
        panel.add(new JLabel("Venue:"));
        panel.add(venueField);
        int result = JOptionPane.showConfirmDialog(this, panel, "Schedule Game", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String opponentName = opponentNameField.getText();
            String dateString = dateField.getText();
            String venue = venueField.getText();
            try {
                Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
                return new Game(opponentName, date, venue);
            } catch (ParseException e) {
                JOptionPane.showMessageDialog(this, "Invalid date format.");
            }
        }
        return null;
    }


    /**
     * Effects: Displays the game schedule in a dialog box.
     */
    private void viewGameSchedule() {
        if (team == null) {
            JOptionPane.showMessageDialog(this, "No team created yet.");
            return;
        }
        ArrayList<Game> games = team.getGames();
        if (games.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No games scheduled yet.");
        } else {
            StringBuilder scheduleText = new StringBuilder();
            scheduleText.append("Game Schedule:\n");

            for (Game game : games) {
                scheduleText.append("Opponent: ").append(game.getOpponentName()).append("\n");
                scheduleText.append("Date: ").append(game.getDate()).append("\n");
                scheduleText.append("Venue: ").append(game.getVenue()).append("\n\n");
            }
            JOptionPane.showMessageDialog(this, scheduleText.toString(), "Game Schedule", JOptionPane.PLAIN_MESSAGE);
        }
    }

    /**
     * Effects: Prompts the user to select a game, input player stats,
     * input opponent's score, updates game outcome, and displays the result.
     */
    private void recordGameOutcome() {
        if (team == null) {
            JOptionPane.showMessageDialog(this, "No team created yet.");
            return;
        }
        ArrayList<Game> games = team.getGames();
        if (games.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No games scheduled yet.");
            return;
        }
        Game selectedGame = selectGameFromList(games);
        if (selectedGame == null) {
            JOptionPane.showMessageDialog(this, "Invalid game selection.");
            return;
        }

        int totalHomeScore = inputPlayerStatsForHomeTeam();
        int opponentScore = inputOpponentScore();
        updateGameOutcome(selectedGame, totalHomeScore, opponentScore);
    }

    /**
     * Effects: Displays a dialog box with a list of games for the user to select,
     * returns the selected game, or null if no game is selected.
     */
    private Game selectGameFromList(ArrayList<Game> games) {
        Object[] options = new Object[games.size()];
        for (int i = 0; i < games.size(); i++) {
            options[i] = games.get(i).getOpponentName() + " - " + games.get(i).getDate();
        }
        int selection = JOptionPane.showOptionDialog(this, "Select a game to record outcome:", "Record Game Outcome",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        if (selection == JOptionPane.CLOSED_OPTION) {
            return null;
        }
        return games.get(selection);
    }

    /**
     * Effects: Prompts the user to input points, assists, rebounds, and blocks for each player,
     * updates player statistics, and returns the total home score.
     */
    private int inputPlayerStatsForHomeTeam() {
        int totalHomeScore = 0;
        ArrayList<Player> players = team.getPlayers();
        for (Player player : players) {
            int points = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter points by" + player.getName()));
            totalHomeScore += points;
            player.getStats().addPoints(points);
            int assists = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter assists by " + player.getName()));
            player.getStats().addAssists(assists);
            int rebounds = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter rebounds by " + player.getName()));
            player.getStats().addRebounds(rebounds);
            int blocks = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter blocks by " + player.getName()));
            player.getStats().addBlocks(blocks);
        }
        return totalHomeScore;
    }

    /**
     * Effects: Prompts the user to input the opponent's score and returns the inputted score.
     */
    private int inputOpponentScore() {
        return Integer.parseInt(JOptionPane.showInputDialog(this, "Enter opponent's score:"));
    }

    /**
     * Effects: Updates the home score, opponent score, and win status of the selected game,
     * displays the updated game outcome, and provides an option to return to the games menu.
     */
    private void updateGameOutcome(Game selectedGame, int totalHomeScore, int opponentScore) {
        selectedGame.setHomeScore(totalHomeScore);
        selectedGame.setOpponentScore(opponentScore);
        selectedGame.setIsWin(totalHomeScore > opponentScore);

        JOptionPane.showMessageDialog(this, "Game Outcome Recorded:\n"
                + "Opponent: " + selectedGame.getOpponentName() + "\n"
                + "Date: " + selectedGame.getDate() + "\n"
                + "Venue: " + selectedGame.getVenue() + "\n"
                + "Home Score: " + selectedGame.getHomeScore() + "\n"
                + "Opponent Score: " + selectedGame.getOpponentScore() + "\n"
                + "Win Status: " + (selectedGame.getIsWin() ? "Win" : "Loss"));
        int option = JOptionPane.showConfirmDialog(this, "Back to Games menu?", "Game Menu", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            dispose();
            new GamesMenuWindow(team);
        }

    }
}
