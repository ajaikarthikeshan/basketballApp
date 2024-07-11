package persistence;

import model.Game;
import model.Player;
import model.Team;
import model.TrainingSession;
import org.json.JSONException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderTest extends JsonTest{

    @Test
    void testReaderNonExistentTeamFile() {
        JsonReader reader = new JsonReader("./data/noSuchTeamFile.json");
        try {
            Team team = reader.readTeam();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyTeam() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyTeam.json");
        try {
            Team team = reader.readTeam();

            assertEquals("name", team.getTeamName());
            assertEquals(0, team.getLowerBoundAge());
            assertEquals(0, team.getUpperBoundAge());
            assertEquals(0, team.getPlayers().size());
            assertEquals(0, team.getGames().size());
            assertEquals(0, team.getTrainingSessions().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderNormalTeam() {
        JsonReader reader = new JsonReader("./data/testReaderNormalTeam.json");
        try {
            Team team = reader.readTeam();

            assertEquals("Toms Gang", team.getTeamName());
            assertEquals(15, team.getLowerBoundAge());
            assertEquals(20, team.getUpperBoundAge());

            List<Player> players = team.getPlayers();
            assertEquals(2, players.size());
            checkPlayer("Tom", 18, "PG", 10, players.get(0));
            checkPlayer("Ash", 19, "SG", 11, players.get(1));

            List<Game> games = team.getGames();
            assertEquals(3, games.size());
            checkGame("Sham Gang", "2024-03-10", "Osborne", 80, 75, true, games.get(1));
            checkGame("3198", "2024-02-25", "SRC", 70, 85, false, games.get(0));
            checkGame("Upcoming Opponent", "2024-03-12", "Upcoming Venue", 0, 0, false, games.get(2));

            List<TrainingSession> sessions = team.getTrainingSessions();
            assertEquals(2, sessions.size());
            List<String> players1 = new ArrayList<>();
            players1.add(players.get(0).getName());
            players1.add(players.get(1).getName());
            List<String> players2 = new ArrayList<>();
            players2.add(players.get(1).getName());
            try {
                checkTrainingSession("2024-03-05", "Training Facility1", 90, players1, sessions.get(0));
                checkTrainingSession("2024-02-20", "Training Facility2", 75, players2, sessions.get(1));
            } catch (JSONException e) {
                System.err.println("Error parsing JSON data in training session: " + e.getMessage());
            }
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

}
