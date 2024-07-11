package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;

public class TrainingSessionTest {

    private TrainingSession trainingSession;
    private Player player1;
    private Player player2;

    @BeforeEach
    public void setUp() {
        Date date = new Date();
        String venue = "Src";
        int durationInMinutes = 60;
        trainingSession = new TrainingSession(date, venue, durationInMinutes);

        player1 = new Player("Player 1", 20, "PG");
        player2 = new Player("Player 2", 22, "SG");
    }

    @Test
    public void testGettersAndSetters() {
        assertEquals(trainingSession.getVenue(), "Src");
        assertFalse(trainingSession.isCompleted());
        assertEquals(trainingSession.getDurationInMinutes(), 60);

        Date newDate = new Date();
        trainingSession.setDate(newDate);
        assertEquals(trainingSession.getDate(), newDate);

        String newVenue = "Src";
        trainingSession.setVenue(newVenue);
        assertEquals(trainingSession.getVenue(), newVenue);

        int newDuration = 45;
        trainingSession.setDurationInMinutes(newDuration);
        assertEquals(trainingSession.getDurationInMinutes(), newDuration);
    }

    @Test
    public void testSetAndGetDurationInMinutes_ZeroDuration() {
        trainingSession.setDurationInMinutes(0);
        assertEquals(0, trainingSession.getDurationInMinutes(), "Zero duration should be allowed");
    }

    @Test
    public void testAddAttendee() {
        trainingSession.addAttendee("Player 1");
        trainingSession.addAttendee("Player 2");
        ArrayList<String> attendees = trainingSession.getAttendees();
        assertEquals(2, attendees.size());
        assertTrue(attendees.contains("Player 1"));
        assertTrue(attendees.contains("Player 2"));
    }

    @Test
    public void testMarkCompleted() {
        assertFalse(trainingSession.isCompleted());
        trainingSession.markCompleted();
        assertTrue(trainingSession.isCompleted());
    }
}

