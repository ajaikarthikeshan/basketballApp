package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TeamMemberTest {
    private TeamMember member;

    @BeforeEach
    public void setUp() {
        member = new Player("Aj", 19, "PG");
    }

    @Test
    public void testGetName() {
        assertEquals("Aj", member.getName());
    }

    @Test
    public void testGetAge() {
        assertEquals(19, member.getAge());
    }
}
