package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Date;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TeamTest {

    private Team team;
    private TrainingSession trainingSession1;
    private TrainingSession trainingSession2;


    @BeforeEach
    public void setUp() {
        team = new Team("A", 18, 20);
        trainingSession1 = new TrainingSession(new Date(), "src", 75);
        trainingSession2 = new TrainingSession(new Date(), "osborne", 80);
    }

    @Test
    public void testGetTeamName() {
        assertEquals("A", team.getTeamName());
    }

    @Test
    public void testAddMember() {
        Player player = new Player("Player1", 20, "PG");
        team.addMember(player);
        assertEquals(1, team.getPlayers().size());
    }

    @Test
    void testAddGame() {
        Game game1 = new Game("A", new Date(System.currentTimeMillis() + 86400000), "src");
        Game game2 = new Game("B", new Date(System.currentTimeMillis() + 2 * 86400000), "src");
        Game game3 = new Game("C", new Date(System.currentTimeMillis() + 3 * 86400000), "src");

        team.addGame(game2);
        assertEquals(game2, team.getGames().get(0));


        team.addGame(game1);
        assertEquals(game1, team.getGames().get(0));


        team.addGame(game3);
        assertEquals(game3, team.getGames().get(2));
    }

    @Test
    void testViewGameSchedule_NoGames() {
        assertNull(team.viewGameSchedule());
    }

    // CREDIT: For using system.currentTimeMillis to test
    //https://stackoverflow.com/questions/2001671/override-java-system-currenttimemillis-for-testing-time-sensitive-code
    @Test
    void testViewGameSchedule_UpcomingGame() {
        Date tomorrow = new Date(System.currentTimeMillis() + 86400000);
        Game upcomingGame = new Game("Opponent", tomorrow, "Venue");
        team.addGame(upcomingGame);
        assertEquals("Opponent", team.viewGameSchedule());
    }

    @Test
    void testViewGameSchedule_PastGames() {
        ArrayList<Game> pastGames = new ArrayList<Game>();
        Date yesterday = new Date(System.currentTimeMillis() - 86400000);
        pastGames.add(new Game("Opponent1", yesterday, "src"));
        pastGames.add(new Game("Opponent2", yesterday, "arc"));
        team.setGames(pastGames);
        assertNull(team.viewGameSchedule());
    }

    @Test
    public void testFindPlayerByJerseyNumber() {
        Player player1 = new Player("Aj", 19, "PG");
        player1.setJerseyNumber(7);
        team.addMember(player1);

        assertEquals(player1, team.findPlayerByJerseyNumber(7));

        assertNull(team.findPlayerByJerseyNumber(10));
    }

    @Test
    public void testGetTrainingSessionsInitiallyEmpty() {
        assertTrue(team.getTrainingSessions().isEmpty());
    }

    @Test
    public void testAddTrainingSession() {
        team.addTrainingSession(trainingSession1);
        assertEquals(1, team.getTrainingSessions().size());
        assertTrue(team.getTrainingSessions().contains(trainingSession1));
    }

    @Test
    public void testAddMultipleTrainingSessions() {
        team.addTrainingSession(trainingSession1);
        team.addTrainingSession(trainingSession2);
        assertEquals(2, team.getTrainingSessions().size());
        assertTrue(team.getTrainingSessions().contains(trainingSession1));
        assertTrue(team.getTrainingSessions().contains(trainingSession2));
    }
}
