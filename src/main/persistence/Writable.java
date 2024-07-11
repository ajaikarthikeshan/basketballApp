package persistence;

import org.json.JSONObject;

/**
 * Writable interface represents an object that can be converted into a JSON object.
 */
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}