package ui;

import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.io.FileNotFoundException;

public class BasketballTeamApp {
    private static final String JSON_STORE = "./data/team.json";
    private Team team;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    /**
     * BasketballTeamApp represents an application for managing a basketball team,
     * allowing users to create a new team, add players, schedule games, record game outcomes,
     * schedule training sessions, and view player statistics and team activities.
     */
    public BasketballTeamApp() {
        team = null;
        input = new Scanner(System.in);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runTeamManagementApp();
    }

    private void runTeamManagementApp() {
        boolean keepGoing = true;

        while (keepGoing) {
            if (team == null) {
                keepGoing = processMainMenu();
            } else {
                keepGoing = processTeamMenu();
            }
        }
        System.out.println("\nExiting Basketball Team Management App.");
    }

    private boolean processMainMenu() {
        System.out.println("\nMain Menu:");
        System.out.println("\tn -> Create New Team");
        System.out.println("\tl -> Load Saved Team");
        System.out.println("\tq -> Quit");
        System.out.print("Enter your command: ");
        String command = input.next().toLowerCase();

        switch (command) {
            case "n":
                createNewTeam();
                return true;
            case "l":
                loadTeamData();
                return true;
            case "q":
                confirmSaveBeforeQuitting();
                return false;
            default:
                System.out.println("Invalid command. Please try again.");
                return true;
        }
    }

    //CREDIT: Idea for menu
    // https://stackoverflow.com/questions/8059259/creating-a-console-menu-for-user-to-make-a-selection
    private boolean processTeamMenu() {
        displayTeamMenu();
        String command = input.next().toLowerCase();
        switch (command) {
            case "a":
                addPlayer();
                return true;
            case "v":
                viewPlayerList();
                return true;
            case "g":
                processGamesMenu();
                return true;
            case "p":
                processTrainingSessionMenu();
                return true;
            case "q":
                confirmSaveBeforeQuitting();
                return false;
            default:
                return true;
        }
    }

    void confirmSaveBeforeQuitting() {
        if (team != null) {
            System.out.println("Would you like to save your team data before quitting? (y/n)");
            String saveChoice = input.next().toLowerCase();
            if (saveChoice.equals("y")) {
                saveTeamData();
            } else {
                System.exit(0);
            }
        }
    }

    private void displayTeamMenu() {
        System.out.println("\nTeam Menu:");
        System.out.println("\ta -> Add Player");
        System.out.println("\tv -> View Player List");
        System.out.println("\tg -> Games Menu");
        System.out.println("\tp -> Training Session Menu");
        System.out.println("\tq -> Quit");
        System.out.print("Enter your command: ");
    }

    private void processGamesMenu() {
        System.out.println("\nGames Menu:");
        System.out.println("\ts -> Schedule Game");
        System.out.println("\tv -> View Game Schedule");
        System.out.println("\tr -> Record Game Outcome");
        System.out.println("\tb -> Back");
        System.out.print("Enter your command: ");
        String command = input.next().toLowerCase();

        switch (command) {
            case "s":
                scheduleGame();
                break;
            case "v":
                viewGameSchedule();
                break;
            case "r":
                recordGameOutcome();
                break;
            case "b":
                processTeamMenu();
            default:
                System.out.println("Invalid command. Please try again.");
                processGamesMenu();
        }
    }

    // MODIFIES: this
    // EFFECTS: Creates a new team with the specified name and age boundaries and assigns it to the team
    void createNewTeam() {
        System.out.print("\nEnter team name: ");
        String teamName = input.next();
        System.out.print("Enter lower bound age: ");
        int lowerBoundAge = input.nextInt();
        System.out.print("Enter upper bound age: ");
        int upperBoundAge = input.nextInt();
        team = new Team(teamName, lowerBoundAge, upperBoundAge);
        System.out.println("\nTeam created successfully: " + team.getTeamName());
    }

    // MODIFIES: this
    // EFFECTS: Prompts the user to enter details of a new player and adds the player to the team
    private void addPlayer() {
        if (team == null) {
            System.out.println("\nNo team created yet. Please create a team first.");
            return;
        }

        System.out.print("\nEnter player name: ");
        String playerName = input.next();
        System.out.print("Enter player age: ");
        int playerAge = input.nextInt();
        System.out.print("Enter player position: ");
        String playerPosition = input.next();
        System.out.print("Enter player jersey number: ");
        int jerseyNumber = input.nextInt();

        Player player = new Player(playerName, playerAge, playerPosition);
        player.setJerseyNumber(jerseyNumber);

        team.addMember(player);
        System.out.println("\nPlayer added successfully to the team: " + player.getName());
    }

    // EFFECTS: Displays the list of players in the team along with their positions and jersey numbers
    //          Also calls the viewPlayerStats method to view stats for a specific player
    private void viewPlayerList() {
        if (team == null) {
            System.out.println("\nNo team created yet. Please create a team first.");
            return;
        }

        System.out.println("\nPlayer List:");
        ArrayList<Player> players = team.getPlayers();
        for (Player player : players) {
            System.out.println(player.getName() + " - " + player.getPosition() + " - " + player.getJerseyNumber());
        }

        viewPlayerStats();
    }

    // EFFECTS: Displays player statistics for the player with the specified jersey number,
    //          if found in the team; otherwise, displays not found message
    private void viewPlayerStats() {
        if (team == null) {
            System.out.println("\nNo team created yet. Please create a team first.");
            return;
        }

        System.out.print("\nEnter player jersey number to view stats: ");
        int jerseyNumber = input.nextInt();

        Player player = team.findPlayerByJerseyNumber(jerseyNumber);
        if (player != null) {
            displayPlayerStats(player);
        } else {
            System.out.println("Player with jersey number " + jerseyNumber + " not found.");
            return;
        }

        System.out.print("\nDo you want to view stats for another player? (yes/no): ");
        String choice = input.next().toLowerCase();
        if (choice.equals("yes")) {
            viewPlayerStats();
        } else {
            processTeamMenu();
        }
    }

    private void displayPlayerStats(Player player) {
        System.out.println("\nPlayer Stats:");
        System.out.println("Name: " + player.getName());
        System.out.println("Points: " + player.getStats().getPoints());
        System.out.println("Rebounds: " + player.getStats().getRebounds());
        System.out.println("Assists: " + player.getStats().getAssists());
        System.out.println("Blocks: " + player.getStats().getBlocks());
    }

    // CREDIT: The SimpleDateFormat and parse exception usage
    // https://stackoverflow.com/questions/17580355/what-is-the-use-of-try-catch-in-setting-a-date-value-in-java
    // MODIFIES: team
    // EFFECTS: schedules a game for the team
    private void scheduleGame() {
        if (team == null) {
            System.out.println("\nNo team created yet. Please create a team first.");
            return;
        }

        System.out.print("\nEnter opponent's name: ");
        String opponentName = input.next();
        System.out.print("Enter date of the game (YYYY-MM-DD): ");
        String dateString = input.next();
        Date date;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
        } catch (ParseException e) {
            System.out.println("Invalid date format. Please enter date in the format YYYY-MM-DD.");
            return;
        }
        System.out.print("Enter venue: ");
        String venue = input.next();

        Game game = new Game(opponentName, date, venue);
        team.addGame(game);
        System.out.println("\nGame scheduled successfully against " + opponentName + " on " + dateString);

        processGamesMenu();
    }

    // EFFECTS: displays the team's game schedule showing opponent, date, and venue
    private void viewGameSchedule() {
        if (team == null) {
            System.out.println("\nNo team created yet. Please create a team first.");
            return;
        }

        ArrayList<Game> games = team.getGames();
        if (games.isEmpty()) {
            System.out.println("\nNo games scheduled yet.");
        } else {
            System.out.println("\nGame Schedule:");
            for (Game game : games) {
                System.out.println("Opponent: " + game.getOpponentName());
                System.out.println("Date: " + game.getDate());
                System.out.println("Venue: " + game.getVenue());
                System.out.println();
            }
        }
        processGamesMenu();
    }

    // EFFECTS: Records the outcome of a game by selecting it from a list and updating its scores
    private void recordGameOutcome() {
        if (team == null) {
            System.out.println("\nNo team created yet. Please create a team first.");
            return;
        }

        ArrayList<Game> games = team.getGames();

        if (games.isEmpty()) {
            System.out.println("\nNo games scheduled yet.");
            return;
        }
        
        Game selectedGame = selectGameFromList(games);
        if (selectedGame == null) {
            System.out.println("Invalid game selection.");
            return;
        }

        int totalHomeScore = inputPlayerStatsForHomeTeam();
        int opponentScore = inputOpponentScore();

        updateGameOutcome(selectedGame, totalHomeScore, opponentScore);
    }

    // EFFECTS: Allows the user to select a game from a list and returns the selected game
    private Game selectGameFromList(ArrayList<Game> games) {
        System.out.println("\nSelect a game to record outcome:");
        for (int i = 0;  i < games.size(); i++) {
            System.out.println((i + 1) + ". " + games.get(i).getOpponentName() + " - " + games.get(i).getDate());
        }
        System.out.print("Enter the number of the game: ");
        int gameNumber = input.nextInt();

        if (gameNumber < 1 || gameNumber > games.size()) {
            return null;
        }
        return games.get(gameNumber - 1);
    }

    private int inputPlayerStatsForHomeTeam() {
        System.out.println("\nInput individual player stats to determine the home team's score:");
        ArrayList<Player> players = team.getPlayers();
        int totalHomeScore = 0;
        for (Player player : players) {
            System.out.println("\nPlayer: " + player.getName());
            System.out.print("Enter points scored by " + player.getName() + ": ");
            int points = input.nextInt();
            totalHomeScore += points;
            player.getStats().addPoints(points);
            System.out.print("Enter assists given by " + player.getName() + ": ");
            int assists = input.nextInt();
            player.getStats().addAssists(assists);
            System.out.print("Enter rebounds by " + player.getName() + ": ");
            int rebounds = input.nextInt();
            player.getStats().addRebounds(rebounds);
            System.out.print("Enter blocks by " + player.getName() + ": ");
            int blocks = input.nextInt();
            player.getStats().addBlocks(blocks);
        }
        return totalHomeScore;
    }

    private int inputOpponentScore() {
        System.out.print("\nEnter opponent's score: ");
        return input.nextInt();
    }

    // MODIFIES: Game
    // EFFECTS: Updates the game outcome and displays the result, prompting user to return to game menu
    private void updateGameOutcome(Game selectedGame, int totalHomeScore, int opponentScore) {
        selectedGame.setHomeScore(totalHomeScore);
        selectedGame.setOpponentScore(opponentScore);
        selectedGame.setIsWin(totalHomeScore > opponentScore);

        System.out.println("\nGame Outcome Recorded:");
        System.out.println("Opponent: " + selectedGame.getOpponentName());
        System.out.println("Date: " + selectedGame.getDate());
        System.out.println("Venue: " + selectedGame.getVenue());
        System.out.println("Home Score: " + selectedGame.getHomeScore());
        System.out.println("Opponent Score: " + selectedGame.getOpponentScore());
        System.out.println("Win Status: " + (selectedGame.getIsWin() ? "Win" : "Loss"));

        System.out.print("\nDo you want to go back to the game menu? (yes/no): ");
        String choice = input.next().toLowerCase();
        if (choice.equals("yes")) {
            processGamesMenu();
        } else {
            System.out.println("Exiting Game Outcome Recording.");
        }
    }

    private void processTrainingSessionMenu() {
        System.out.println("\nTraining Session Menu:");
        System.out.println("\tc -> Create New Training Session");
        System.out.println("\tv -> View Training Sessions");
        System.out.println("\tb -> Back to Team Menu");
        System.out.print("Enter your command: ");
        String command = input.next().toLowerCase();

        switch (command) {
            case "c":
                createNewTrainingSession();
            case "v":
                viewTrainingSessions();
            case "b":
                processTeamMenu();
            default:
                System.out.println("Invalid command. Please try again.");
                processTrainingSessionMenu();
        }
    }

    // MODIFIES: TrainingSession
    private void createNewTrainingSession() {
        System.out.print("\nEnter date of the training session (YYYY-MM-DD): ");
        String dateString = input.next();
        Date date;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
        } catch (ParseException e) {
            System.out.println("Invalid date format. Please enter date in the format YYYY-MM-DD.");
            return;
        }

        System.out.print("Enter venue: ");
        String venue = input.next();

        System.out.print("Enter duration in minutes: ");
        int durationInMinutes = input.nextInt();

        TrainingSession trainingSession = new TrainingSession(date, venue, durationInMinutes);
        team.addTrainingSession(trainingSession);

        System.out.println("\nTraining session created successfully:");
        System.out.println("Date: " + trainingSession.getDate());
        System.out.println("Venue: " + trainingSession.getVenue());
        System.out.println("Duration: " + trainingSession.getDurationInMinutes() + " minutes");

        processTrainingSessionMenu();
    }

    // EFFECTS: Displays the team's training sessions, offering options to update each session
    private void viewTrainingSessions() {
        if (team == null) {
            System.out.println("\nNo team created yet. Please create a team first.");
            return;
        }

        ArrayList<TrainingSession> trainingSessions = team.getTrainingSessions();
        if (trainingSessions.isEmpty()) {
            System.out.println("\nNo training sessions scheduled yet.");
        } else {
            displayTrainingSessions(trainingSessions);
            offerUpdateOptions(trainingSessions);
        }
    }


    private void displayTrainingSessions(ArrayList<TrainingSession> trainingSessions) {
        System.out.println("\nTraining Sessions:");
        for (int i = 0; i < trainingSessions.size(); i++) {
            TrainingSession trainingSession = trainingSessions.get(i);
            System.out.println("Session " + (i + 1) + ":");
            displaySessionDetails(trainingSession);
            System.out.println();
        }
    }

    private void displaySessionDetails(TrainingSession trainingSession) {
        System.out.println("Date: " + trainingSession.getDate());
        System.out.println("Venue: " + trainingSession.getVenue());
        System.out.println("Duration: " + trainingSession.getDurationInMinutes() + " minutes");
        System.out.println("Completed: " + (trainingSession.isCompleted() ? "Yes" : "No"));
    }

    // EFFECTS: Offers update options for the selected training session and performs the selected action
    private void offerUpdateOptions(ArrayList<TrainingSession> trainingSessions) {
        System.out.print("Enter session number to update or 'q' to go back to Main Menu: ");
        String sessionNumber = input.next().toLowerCase();
        if (!sessionNumber.equals("q")) {
            int index = Integer.parseInt(sessionNumber) - 1;
            if (index >= 0 && index < trainingSessions.size()) {
                updateTrainingSession(trainingSessions.get(index));
            } else {
                System.out.println("Invalid session number. Please try again.");
                viewTrainingSessions();
            }
        }
    }

    // MODIFIES: training session's completion status
    // EFFECTS: Marks the training session as completed
    private void updateTrainingSession(TrainingSession trainingSession) {
        System.out.println("\nUpdate Training Session:");
        System.out.println("\tc -> Mark as Completed");
        System.out.println("\ta -> Add Attendee");
        System.out.println("\tq -> Go back to Training Session Menu");
        System.out.print("Enter your command: ");
        String command = input.next().toLowerCase();

        switch (command) {
            case "c":
                trainingSession.markCompleted();
                System.out.println("Training session marked as completed.");
                processTrainingSessionMenu();
            case "a":
                addAttendeeToTrainingSession(trainingSession);
                processTrainingSessionMenu();
            case "b":
                processTrainingSessionMenu();
            default:
                System.out.println("Invalid command. Please try again.");
                updateTrainingSession(trainingSession);
        }
    }

    // MODIFIES: training session's attendee list
    // EFFECTS: Adds a player as an attendee to the training session
    private void addAttendeeToTrainingSession(TrainingSession trainingSession) {
        System.out.print("Enter jersey number of the player to add as attendee: ");
        int jerseyNumber = input.nextInt();
        Player player = team.findPlayerByJerseyNumber(jerseyNumber);
        if (player != null) {
            trainingSession.addAttendee(player.getName());
            System.out.println(player.getName() + " added as attendee to the training session.");
        } else {
            System.out.println("Player with jersey number " + jerseyNumber + " not found.");
        }
        updateTrainingSession(trainingSession);
    }

    // Saves team data to a JSON file
    private void saveTeamData() {
        if (team != null) {
            try {
                jsonWriter.open();
                jsonWriter.write(team);
                jsonWriter.close();
                System.out.println("Team data saved successfully to " + JSON_STORE);
                System.exit(0);
            } catch (FileNotFoundException e) {
                System.out.println("Error saving team data: " + e.getMessage());
            }
        }
    }

    // EFFECTS: Loads team data from a JSON file (if it exists)
    void loadTeamData() {
        try {
            team = jsonReader.readTeam();
            System.out.println("Team data loaded successfully from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Team data file not found. Please create a new team.");
        }
    }
}

