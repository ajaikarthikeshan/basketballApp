package persistence;

import model.*;
import org.junit.jupiter.api.Test;

import model.Game; // Assuming you have a Game class
import model.Player; // Assuming you have a Player class
import model.Team;
import org.junit.jupiter.api.Test;


import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class JsonWriterTest {
    @Test
    void testWriterInvalidFile() {
        try {
            Team thunder = new Team("Oklahoma City Thunder", 10, 12);
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyTeam() {
        try {
            // Create an empty team based on your Team class structure
            Team emptyTeam = new Team("Placeholder Team", 10 , 12);

            JsonWriter writer = new JsonWriter("./data/testWriterEmptyTeam.json");
            writer.open();
            writer.write(emptyTeam);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyTeam.json");
            Team readTeam = reader.readTeam();

            assertEquals("Placeholder Team", readTeam.getTeamName());
            assertEquals(0, readTeam.getPlayers().size());
            assertEquals(0, readTeam.getGames().size());
            assertEquals(0, readTeam.getTrainingSessions().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterNormalTeamFromJson() {
        try {
            Team tomsGang = new Team("Toms gang", 15, 20);
            // Add Players
            tomsGang.addMember(new Player("Tom", 18, "PG"));
            tomsGang.addMember(new Player("Ash", 19, "SG"));

            // Create SimpleDateFormat instance with the desired date format
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            // Parse date strings into Date objects
            Date date1 = dateFormat.parse("2024-03-10");
            Date date2 = dateFormat.parse("2024-02-25");
            Date date3 = dateFormat.parse("2024-03-12");
            Date trainingDate1 = dateFormat.parse("2024-03-05");
            Date trainingDate2 = dateFormat.parse("2024-02-20");

            tomsGang.addGame(new Game("Sham Gang", date1, "Osborne"));
            tomsGang.addGame(new Game("3198", date2, "SRC"));
            tomsGang.addGame(new Game("Upcoming Opponent", date3, "Upcoming Venue"));

            // Add Training Sessions
            TrainingSession session1 = new TrainingSession(trainingDate1, "Training Facility1", 90);
            session1.addAttendee("Tom");
            session1.addAttendee("Ash");
            tomsGang.addTrainingSession(session1);
            TrainingSession session2 = new TrainingSession(trainingDate2, "Training Facility2", 75);
            tomsGang.addTrainingSession(session2);
            session2.addAttendee("Ash");

            JsonWriter writer = new JsonWriter("./data/testWriterNormalTeam.json");
            writer.open();
            writer.write(tomsGang);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterNormalTeam.json");
            Team readTeam = reader.readTeam();

            // Assert properties from the provided normal team JSON
            assertEquals("Toms gang", readTeam.getTeamName());
            assertEquals(15, readTeam.getLowerBoundAge());
            assertEquals(20, readTeam.getUpperBoundAge());

            assertEquals(2, readTeam.getPlayers().size());
            List<Player> players = readTeam.getPlayers();
            assertEquals("Tom", players.get(0).getName());
            assertEquals(18, players.get(0).getAge());

            assertEquals(3, readTeam.getGames().size());
            List<Game> games = readTeam.getGames();
            assertEquals("Sham Gang", games.get(1).getOpponentName());
            assertEquals("2024-03-10", games.get(1).getFormattedDate());
            assertFalse(games.get(0).getIsWin());

            assertEquals(2, readTeam.getTrainingSessions().size());
            List<TrainingSession> trainingSessions = readTeam.getTrainingSessions();
            assertEquals("Training Facility1", trainingSessions.get(0).getVenue());
            assertEquals(90, trainingSessions.get(0).getDurationInMinutes());
            assertEquals(2, trainingSessions.get(0).getAttendees().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

}
