package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PlayerTest {
    private Player player;

    @BeforeEach
    public void setUp() {
        player = new Player("Aj", 19, "SG");
    }

    @Test
    public void testGetPosition() {
        assertEquals("SG", player.getPosition());
    }

    @Test
    public void testGetJerseyNumber() {
        player.setJerseyNumber(19);
        assertEquals(19, player.getJerseyNumber());
    }

    @Test
    public void testSetPosition() {
        player.setPosition("PG");
        assertEquals("PG", player.getPosition());
    }

    @Test
    public void testGetStats() {
        PerformanceStats perform = new PerformanceStats();
        player.setStats(perform);
        assertEquals(perform, player.getStats());
    }
}
