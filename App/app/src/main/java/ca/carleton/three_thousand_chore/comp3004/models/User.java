package ca.carleton.three_thousand_chore.comp3004.models;

import android.util.Log;

import com.android.volley.Request;

import org.json.JSONException;
import org.json.JSONObject;

import ca.carleton.three_thousand_chore.comp3004.JsonRequest;
import ca.carleton.three_thousand_chore.comp3004.RequestHelper;

/**
 * Created by jackmccracken on 2017-10-09.
 */

public class User {
    private int id;
    private String name;

    public User(int id, int[] helpRequests, String name) {
        this.id = id;
        this.name = name;
    }

    public User(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }

    public static void createUser(final JsonRequest.CompletionHandler<User> handler) {
        JsonRequest<User> request = new JsonRequest<>(Request.Method.POST, RequestHelper.BASE_URL + "/users", null, new JsonRequest.ObjectCreationHandler<User>() {
            @Override
            public User fromJson(JSONObject obj) throws JSONException {
                return new User(obj.getInt("id"));
            }
        }, handler);
        RequestHelper rh = RequestHelper.getInstance();

        rh.getQueue().add(request);
    }
}
