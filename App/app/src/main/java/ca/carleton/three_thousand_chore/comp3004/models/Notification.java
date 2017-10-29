package ca.carleton.three_thousand_chore.comp3004.models;

import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ca.carleton.three_thousand_chore.comp3004.JsonArrayRequest;
import ca.carleton.three_thousand_chore.comp3004.JsonObjectRequest;
import ca.carleton.three_thousand_chore.comp3004.Requests;

/**
 * Created by jackmccracken on 2017-10-09.
 */

public class Notification {
    private int id;
    private String title;
    private String description;

    public Notification(int id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public static JsonObjectRequest.ObjectCreationHandler<Notification> objectCreationHandler = new JsonObjectRequest.ObjectCreationHandler<Notification>() {
        @Override
        public Notification fromJson(JSONObject obj) throws JSONException {
            return new Notification(obj.getInt("id"), obj.getString("title"), obj.getString("description"));
        }
    };

    public static void getAll(int userId, final JsonObjectRequest.CompletionHandler<List<Notification>> handler) {
        JsonArrayRequest<Notification> request = new JsonArrayRequest<>(Requests.BASE_URL + "/users/" + userId + "/notifications", objectCreationHandler, handler);
        Requests.getInstance().getQueue().add(request);
    }
}
