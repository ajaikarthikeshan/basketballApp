package persistence;

import model.Game;
import model.Player;
import model.Team;
import model.TrainingSession;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class JsonTest {
    protected void checkPlayer(String name, int age, String position, int jerseyNumber, Player player) {
        assertEquals(name, player.getName());
        assertEquals(age, player.getAge());
        assertEquals(position, player.getPosition());
        assertEquals(jerseyNumber, player.getJerseyNumber());
    }

    protected void checkGame(String opponentName, String date, String venue, int homeScore, int opponentScore, Boolean isWin, Game game) {
        assertEquals(opponentName, game.getOpponentName());
        assertEquals(date, game.getFormattedDate());
        assertEquals(venue, game.getVenue());
        assertEquals(homeScore, game.getHomeScore());
        assertEquals(opponentScore, game.getOpponentScore());

        if (isWin != null) {
            assertEquals(isWin, game.getIsWin());
        }
    }

    protected void checkTrainingSession(String date, String venue, int duration, List<String> attendees, TrainingSession session) {
        assertEquals(date, session.getFormattedDate());
        assertEquals(venue, session.getVenue());
        assertEquals(duration, session.getDurationInMinutes());
        assertEquals(attendees.size(), session.getAttendees().size());
    }
}

