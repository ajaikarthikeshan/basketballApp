package model;

import org.json.JSONObject;
import persistence.Writable;

/**
 * The PerformanceStats class represents the performance statistics
 * of a basketball player. It encapsulates details such as points,
 * assists, blocks, and rebounds. The class provides methods to
 * access these attributes and add to them.
 */
public class PerformanceStats implements Writable {
    private int points;
    private int assists;
    private int blocks;
    private int rebounds;

    // EFFECTS: Constructs a PerformanceStats object with initial values of 0 for points, assists, blocks, and rebounds
    public PerformanceStats() {
        this.points = 0;
        this.assists = 0;
        this.blocks = 0;
        this.rebounds = 0;
    }

    public int getPoints() {
        return this.points;
    }

    public int getAssists() {
        return this.assists;
    }

    public int getBlocks() {
        return this.blocks;
    }

    public int getRebounds() {
        return this.rebounds;
    }

    public void addPoints(int points) {
        this.points += points;
        EventLog.getInstance().logEvent(new Event("Added " + points + " points."));
    }

    public void addAssists(int assists) {
        this.assists += assists;
        EventLog.getInstance().logEvent(new Event("Added " + assists + " assists."));
    }

    public void addBlocks(int blocks) {
        this.blocks += blocks;
        EventLog.getInstance().logEvent(new Event("Added " + blocks + " blocks."));
    }

    public void addRebounds(int rebounds) {
        this.rebounds += rebounds;
        EventLog.getInstance().logEvent(new Event("Added " + rebounds + " rebounds."));
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void setAssists(int assists) {
        this.assists = assists;
    }

    public void setBlocks(int blocks) {
        this.blocks = blocks;
    }

    public void setRebounds(int rebounds) {
        this.rebounds = rebounds;
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonStats = new JSONObject();
        jsonStats.put("points", this.points);
        jsonStats.put("assists", this.assists);
        jsonStats.put("blocks", this.blocks);
        jsonStats.put("rebounds", this.rebounds);

        return jsonStats;
    }
}

