package model;

import org.json.JSONObject;
import persistence.Writable;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The Game class represents a basketball game. It encapsulates details such
 * as the opponent's name, date of the game, venue, home score, opponent score,
 * and the win status. The class provides methods to access and modify these attributes.
 */
public class Game implements Writable {
    private String opponentName;
    private Date date;
    private String venue;
    private int homeScore;
    private int opponentScore;
    private boolean isWin;

    // EFFECTS: Constructs a Game object with the given opponent name, date, and venue,
    //          initializing opponent score, home score, and win status to default values
    public Game(String opponentName, Date date, String venue) {
        this.opponentName = opponentName;
        this.date = date;
        this.venue = venue;
        this.opponentScore = 0;
        this.homeScore = 0;
        this.isWin = false;
        EventLog.getInstance().logEvent(new Event("New game created with opponent: " + opponentName));
    }

    public String getOpponentName() {
        return this.opponentName;
    }

    public Date getDate() {
        return this.date;
    }

    // EFFECTS: Returns the formatted date string in "yyyy-MM-dd" format
    public String getFormattedDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(this.date);
    }


    public String getVenue() {
        return this.venue;
    }

    public int getHomeScore() {
        return this.homeScore;
    }

    public int getOpponentScore() {
        return this.opponentScore;
    }

    public boolean getIsWin() {
        return this.isWin;
    }

    public void setHomeScore(int homeScore) {
        this.homeScore = homeScore;
        EventLog.getInstance().logEvent(new Event("Home score set to " + homeScore));
    }

    public void setOpponentScore(int opponentScore) {
        this.opponentScore = opponentScore;
        EventLog.getInstance().logEvent(new Event("Opponent score set to " + opponentScore));
    }

    public void setIsWin(boolean isWin) {
        this.isWin = isWin;
        String winStatus = isWin ? "won" : "lost";
        EventLog.getInstance().logEvent(new Event("Game result: " + winStatus));
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonGame = new JSONObject();
        jsonGame.put("opponentName", this.opponentName);
        jsonGame.put("date", this.getFormattedDate());
        jsonGame.put("venue", this.venue);
        jsonGame.put("homeScore", this.homeScore);
        jsonGame.put("opponentScore", this.opponentScore);
        jsonGame.put("isWin", this.isWin);

        return jsonGame;
    }
}
