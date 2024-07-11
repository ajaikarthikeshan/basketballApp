package model;

import org.json.JSONObject;


/**
 * The Player class, a subclass of the TeamMember class, represents a
 * basketball player. It introduces additional attributes such as the
 * player's position, jersey number, height in cm, injury status, and
 * performance stats. The class provides methods to access and modify
 * these attributes. It also includes methods to set the player's position,
 * jersey number, height, and injury status.
 */
public class Player extends TeamMember {

    private String position;
    private int jerseyNumber;
    private PerformanceStats stats;

    // REQUIRES: New player cannot have the same jersey number as a pre-existing player
    // EFFECTS: Initializes a new Player object with the given name, position, age, jersey number, height,
    // and sets injury status to false, and logs the event
    public Player(String name, int age, String position) {
        super(name, age);
        this.position = position;
        this.jerseyNumber = 0;
        this.stats = new PerformanceStats();
        EventLog.getInstance().logEvent(new Event("New player created: " + name));
    }

    // EFFECTS: Returns the position of the player and logs the event
    public String getPosition() {
        EventLog.getInstance().logEvent(new Event("Position accessed for player: " + this.name));
        return this.position;
    }

    // EFFECTS: Returns the jersey number of the player and logs the event
    public int getJerseyNumber() {
        EventLog.getInstance().logEvent(new Event("Jersey number accessed for player: " + this.name));
        return this.jerseyNumber;
    }

    // EFFECTS: Returns the performance statistics of the player and logs the event
    public PerformanceStats getStats() {
        EventLog.getInstance().logEvent(new Event("Performance stats accessed for player: " + this.name));
        return this.stats;
    }

    // REQUIRES: position is one of PG, SG, SF, PF, C
    // MODIFIES: this
    // EFFECTS: Sets the position of the player and logs the event
    public void setPosition(String position) {
        this.position = position;
        EventLog.getInstance().logEvent(new Event("Player position set to " + position));
    }

    // REQUIRES: jersey number must be unique in team
    // MODIFIES: this
    // EFFECTS: Sets the jersey number of the player and logs the event
    public void setJerseyNumber(int jerseyNumber) {
        this.jerseyNumber = jerseyNumber;
        EventLog.getInstance().logEvent(new Event("Jersey number set to " + jerseyNumber));
    }

    public void setStats(PerformanceStats perform) {
        this.stats = perform;
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonPlayer = new JSONObject();
        jsonPlayer.put("name", this.name);
        jsonPlayer.put("age", this.age);
        jsonPlayer.put("position", this.position);
        jsonPlayer.put("jerseyNumber", this.jerseyNumber);

        // Performance stats (assuming performance stats have their own toJson method)
        jsonPlayer.put("stats", this.stats.toJson());

        return jsonPlayer;
    }
}
