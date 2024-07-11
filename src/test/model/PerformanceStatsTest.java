package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PerformanceStatsTest {
    private PerformanceStats stats;

    @BeforeEach
    public void setUp() {
        stats = new PerformanceStats();
    }

    @Test
    public void testGetPoints() {
        assertEquals(0, stats.getPoints());
    }

    @Test
    public void testGetAssists() {
        assertEquals(0, stats.getAssists());
    }

    @Test
    public void testGetBlocks() {
        assertEquals(0, stats.getBlocks());
    }

    @Test
    public void testGetRebounds() {
        assertEquals(0, stats.getRebounds());
    }

    @Test
    public void testAddPoints() {
        stats.addPoints(10);
        assertEquals(10, stats.getPoints());
    }

    @Test
    public void testAddAssists() {
        stats.addAssists(3);
        assertEquals(3, stats.getAssists());
    }

    @Test
    public void testAddBlocks() {
        stats.addBlocks(6);
        assertEquals(6, stats.getBlocks());
    }

    @Test
    public void testAddRebounds() {
        stats.addRebounds(9);
        assertEquals(9, stats.getRebounds());
    }
}
