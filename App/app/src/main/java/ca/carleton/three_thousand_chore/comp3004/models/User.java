package ca.carleton.three_thousand_chore.comp3004.models;

import android.util.Log;

import com.android.volley.Request;
import com.google.firebase.iid.FirebaseInstanceId;

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
    private String token;

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

    public void setToken(String token) {
        this.token = token;
    }

    public static void createUser(final JsonRequest.CompletionHandler<User> handler) {
        try
        {
            // Get the token
            JSONObject params = new JSONObject();
            params.put("token", FirebaseInstanceId.getInstance().getToken());

            // Make request
            JsonRequest<User> request = new JsonRequest<>(Request.Method.POST, RequestHelper.BASE_URL + "/users", params, new JsonRequest.ObjectCreationHandler<User>() {
                @Override
                public User fromJson(JSONObject response) throws JSONException {
                    User newuser = new User(response.getInt("id"));
                    newuser.setToken(response.getString("token"));
                    return newuser;
                }
            }, handler);
            RequestHelper rh = RequestHelper.getInstance();

            rh.getQueue().add(request);
        } catch (JSONException e)
        {
            e.printStackTrace();
        }


    }
}
