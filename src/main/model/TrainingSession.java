package model;

import org.json.JSONObject;
import persistence.Writable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * The TrainingSession class represents a training session for a basketball team.
 * It encapsulates details such as the date, venue, duration, and attendees of the
 * session. The class provides methods to manage these attributes, including adding
 * and removing attendees, marking the session as completed, and modifying the date,
 * venue, and duration.
 */
public class TrainingSession implements Writable {
    private Date date;
    private String venue;
    private ArrayList<String> attendees;
    private boolean completed;
    private int durationInMinutes;

    // REQUIRES: no game to be on given date
    public TrainingSession(Date date, String venue, int durationInMinutes) {
        this.date = date;
        this.venue = venue;
        this.attendees = new ArrayList<>();
        this.completed = false;
        this.durationInMinutes = durationInMinutes;
        EventLog.getInstance().logEvent(new Event("New training session created on " + getFormattedDate()));
    }

    public Date getDate() {
        return date;
    }

    // EFFECTS: Returns the formatted date string in "yyyy-MM-dd" format.
    public String getFormattedDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(this.date);
    }

    public void setDate(Date date) {
        this.date = date;
        EventLog.getInstance().logEvent(new Event("Training session date updated to " + getFormattedDate()));
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
        EventLog.getInstance().logEvent(new Event("Training session venue updated to " + venue));
    }

    public boolean isCompleted() {
        return completed;
    }

    public void markCompleted() {
        this.completed = true;
        EventLog.getInstance().logEvent(new Event("Training session marked as completed."));
    }

    public void addAttendee(String player) {
        attendees.add(player);
        EventLog.getInstance().logEvent(new Event("Player " + player + " added to training session."));
    }

    public ArrayList<String> getAttendees() {
        return attendees;
    }

    public int getDurationInMinutes() {
        return durationInMinutes;
    }

    public void setDurationInMinutes(int durationInMinutes) {
        this.durationInMinutes = durationInMinutes;
        EventLog.getInstance().logEvent(new Event("Duration of training session is " + durationInMinutes + " minutes"));
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonSession = new JSONObject();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  // Adjust format if needed
        String formattedDate = dateFormat.format(this.date);
        jsonSession.put("date", formattedDate);
        jsonSession.put("duration", this.durationInMinutes);
        jsonSession.put("venue", this.venue);
        jsonSession.put("attendees", this.attendees);
        jsonSession.put("completed", this.completed);
        return jsonSession;
    }
}
