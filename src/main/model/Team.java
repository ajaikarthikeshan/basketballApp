package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.Date;

/**
 * The 'Team' class, part of the 'model' package, encapsulates a basketball team.
 * It holds information such as the team's name, the players, and the age boundaries
 * for player eligibility. The class provides a suite of features for managing the team.
 * These include adding and removing players from the team based on their age eligibility,
 * finding players by their positions, and adding games to the team's schedule
 * in chronological order. It also allows viewing the opponent of the next upcoming game.
 */

public class Team implements Writable {
    private final String name;
    private ArrayList<Player> players;
    private final int lowerBoundAge;
    private final int upperBoundAge;
    private ArrayList<Game> games;
    private ArrayList<TrainingSession> session;

    // EFFECTS: Initializes a new team with the specified name and age boundaries
    public Team(String name, int lowerBoundAge, int upperBoundAge) {
        this.name = name;
        this.players = new ArrayList<>();
        this.lowerBoundAge = lowerBoundAge;
        this.upperBoundAge = upperBoundAge;
        this.games = new ArrayList<>();
        this.session = new ArrayList<>();
        EventLog.getInstance().logEvent(new Event("New team created: " + name));
    }

    public String getTeamName() {
        return this.name;
    }

    public ArrayList<Player> getPlayers() {
        return this.players;
    }

    public ArrayList<Game> getGames() {
        return this.games;
    }

    // REQUIRES: player.getAge >= lowerBoundAge and player.getAge <= upperBoundAge
    // MODIFIES: this
    // EFFECTS: Adds the specified player to the team if their age is within the specified bounds
    public void addMember(Player player) {
        this.players.add(player);
        EventLog.getInstance().logEvent(new Event("Player added to team: " + player.getName()));
    }

    // REQUIRES: no other game should be scheduled on same day
    // MODIFIES: this
    // EFFECTS: adds game to teams list of upcoming games
    public void addGame(Game game) {
        Date newGameDate = game.getDate();
        int i;
        for (i = 0; i < games.size(); i++) {
            if (games.get(i).getDate().after(newGameDate)) {
                break;
            }
        }
        games.add(i, game);
        EventLog.getInstance().logEvent(new Event("Game added to schedule: " + game.getOpponentName()));
    }

    // MODIFIES: this
    // EFFECTS: Returns the opponent name of the next upcoming game or null if no upcoming games.
    public String viewGameSchedule() {
        Date now = new Date();
        for (Game game : this.games) {
            if (game.getDate().after(now)) {
                EventLog.getInstance().logEvent(new Event("Viewed game schedule."));
                return game.getOpponentName();
            }
        }
        return null;
    }

    // EFFECTS: Returns the player object if found in the team's player list; otherwise, returns null.
    public Player findPlayerByJerseyNumber(int jerseyNumber) {
        for (Player player : this.players) {
            if (player.getJerseyNumber() == jerseyNumber) {
                EventLog.getInstance().logEvent(new Event("Player found by jersey number: " + jerseyNumber));
                return player;
            }
        }
        return null;
    }


    public ArrayList<TrainingSession> getTrainingSessions() {
        return this.session;
    }

    // MODIFIES: this
    // EFFECTS: Adds a training session to the list of sessions for this team.
    public void addTrainingSession(TrainingSession trainingSession) {
        this.session.add(trainingSession);
        EventLog.getInstance().logEvent(new Event("Training session added."));
    }

    public void setGames(ArrayList<Game> pastGames) {
        this.games = pastGames;
        EventLog.getInstance().logEvent(new Event("Team games set."));
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonTeam = new JSONObject();
        jsonTeam.put("name", this.name);
        jsonTeam.put("lowerBoundAge", this.lowerBoundAge);
        jsonTeam.put("upperBoundAge", this.upperBoundAge);

        // Players (assuming players have their own toJson method)
        JSONArray jsonPlayers = new JSONArray();
        for (Player player : this.players) {
            jsonPlayers.put(player.toJson());
        }
        jsonTeam.put("players", jsonPlayers);

        // Games (assuming games have their own toJson method)
        JSONArray jsonGames = new JSONArray();
        for (Game game : this.games) {
            jsonGames.put(game.toJson());
        }
        jsonTeam.put("games", jsonGames);

        // Training sessions (assuming training sessions have their own toJson method)
        JSONArray jsonSessions = new JSONArray();
        for (TrainingSession session : this.session) {
            jsonSessions.put(session.toJson());
        }
        jsonTeam.put("trainingSessions", jsonSessions);

        return jsonTeam;
    }

    public int getLowerBoundAge() {
        return lowerBoundAge;
    }

    public int getUpperBoundAge() {
        return upperBoundAge;
    }
}
