package persistence;

import model.Game;
import model.Player;
import model.Team;
import model.TrainingSession;
import model.PerformanceStats;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.stream.Stream;

import org.json.*;

/**
 * JsonReader reads team data from a JSON file and constructs corresponding Team, Player, Game, and TrainingSession
 * objects. It provides methods to parse JSON objects into these objects.
 */
public class JsonReader {
    private final String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads team data from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Team readTeam() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseTeam(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses team data from JSON object and returns it
    private Team parseTeam(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int lowerBoundAge = jsonObject.getInt("lowerBoundAge");
        int upperBoundAge = jsonObject.getInt("upperBoundAge");
        Team team = new Team(name, lowerBoundAge, upperBoundAge);

        parsePlayers(team, jsonObject);
        parseGames(team, jsonObject);
        parseTrainingSessions(team, jsonObject);

        return team;
    }

    // MODIFIES: team
    // EFFECTS: parses players from JSON object and adds them to team
    private void parsePlayers(Team team, JSONObject jsonObject) {
        JSONArray playersArray = jsonObject.getJSONArray("players");
        for (int i = 0; i < playersArray.length(); i++) {
            JSONObject playerObject = playersArray.getJSONObject(i);
            Player player = parsePlayer(playerObject);
            team.addMember(player);
        }
    }

    // EFFECTS: parses player from JSON object
    private Player parsePlayer(JSONObject playerObject) {
        String name = playerObject.getString("name");
        int age = playerObject.getInt("age");
        String position = playerObject.getString("position");
        int jerseyNumber = playerObject.getInt("jerseyNumber");
        JSONObject statsObject = playerObject.getJSONObject("stats");

        PerformanceStats stats = new PerformanceStats();
        stats.setPoints(statsObject.getInt("points"));
        stats.setAssists(statsObject.getInt("assists"));
        stats.setBlocks(statsObject.getInt("blocks"));
        stats.setRebounds(statsObject.getInt("rebounds"));

        Player player = new Player(name, age, position);

        if (playerObject.has("jerseyNumber")) {
            player.setJerseyNumber(jerseyNumber);
        }
        player.setStats(stats);
        return player;
    }

    // MODIFIES: team
    // EFFECTS: parses games from JSON object and adds them to team
    private void parseGames(Team team, JSONObject jsonObject) {
        JSONArray gamesArray = jsonObject.getJSONArray("games");

        for (int i = 0; i < gamesArray.length(); i++) {
            JSONObject gameObject = gamesArray.getJSONObject(i);
            Game game = parseGame(gameObject);
            team.addGame(game);
        }
    }

    // EFFECTS: parses game from JSON object
    public Game parseGame(JSONObject gameObject) {
        String opponentName = gameObject.getString("opponentName");
        String dateString = gameObject.getString("date");
        String venue = gameObject.getString("venue");
        int homeScore = gameObject.getInt("homeScore");
        int opponentScore = gameObject.getInt("opponentScore");
        boolean isWin = gameObject.getBoolean("isWin");

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = formatter.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Game game = new Game(opponentName, date, venue);
        game.setHomeScore(homeScore);
        game.setOpponentScore(opponentScore);
        game.setIsWin(isWin);
        return game;
    }

    // MODIFIES: team
    // EFFECTS: parses training sessions from JSON object and adds them to team
    public void parseTrainingSessions(Team team, JSONObject jsonObject) {
        JSONArray sessionsArray = jsonObject.getJSONArray("trainingSessions");
        for (int i = 0; i < sessionsArray.length(); i++) {
            JSONObject sessionObject = sessionsArray.getJSONObject(i);
            TrainingSession session = parseTrainingSession(sessionObject);
            team.addTrainingSession(session);
        }
    }

    // MODIFIES: this
    // EFFECTS: parses training session from JSON object and returns it
    public TrainingSession parseTrainingSession(JSONObject sessionObject) {
        String venue = sessionObject.getString("venue");
        String dateString = sessionObject.getString("date");
        int duration = sessionObject.getInt("duration");

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = formatter.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        TrainingSession session = new TrainingSession(date, venue, duration);

        JSONArray attendeesArray = sessionObject.getJSONArray("attendees");

        for (int i = 0; i < attendeesArray.length(); i++) {
            String attendeeName = attendeesArray.getString(i);
            session.addAttendee(attendeeName);
        }
        return session;
    }
}
