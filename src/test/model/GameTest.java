package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Date;

public class GameTest {
    private Game game;

    @BeforeEach
    public void setUp() {
        Date date = new Date();
        game = new Game("Opponent", date, "Venue");
    }

    @Test
    public void testGetOpponentName() {
        assertEquals("Opponent", game.getOpponentName());
    }

    @Test
    public void testGetVenue() {
        assertEquals("Venue", game.getVenue());
    }

    @Test
    public void testGetHomeScoreInitiallyZero() {
        assertEquals(0, game.getHomeScore());
    }

    @Test
    public void testGetOpponentScoreInitiallyZero() {
        assertEquals(0, game.getOpponentScore());
    }

    @Test
    public void testGetIsWinInitiallyFalse() {
        assertFalse(game.getIsWin());
    }

    @Test
    public void testSetHomeScore() {
        game.setHomeScore(90);
        assertEquals(90, game.getHomeScore());
    }

    @Test
    public void testSetOpponentScore() {
        game.setOpponentScore(80);
        assertEquals(80, game.getOpponentScore());
    }

    @Test
    public void testSetIsWin() {
        game.setIsWin(true);
        assertTrue(game.getIsWin());
    }
}
