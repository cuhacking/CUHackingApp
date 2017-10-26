package ca.carleton.three_thousand_chore.comp3004.models;

import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONException;
import org.json.JSONObject;

import ca.carleton.three_thousand_chore.comp3004.JsonRequest;
import ca.carleton.three_thousand_chore.comp3004.Requests;

/**
 * Created by jackmccracken on 2017-10-09.
 */

public class User {
    private int id;
    private String name;
    private String token; // Not used?

    static private String TAG = "User Log";

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

    public void setToken(final String token) {
        JSONObject params = new JSONObject();
        try
        {
            params.put("token", FirebaseInstanceId.getInstance().getToken());

            JsonRequest<User> request = new JsonRequest<User>(Request.Method.PUT, Requests.BASE_URL + "/users/" + id, params, new JsonRequest.CompletionHandler<JSONObject>()
            {
                @Override
                public void requestSucceeded(JSONObject object)
                {
                    User.this.token = token;
                    Log.e(TAG, "requestSucceeded: token added (" + token + ")");
                }

                @Override
                public void requestFailed(String errorMessage)
                {
                    Log.e(TAG, "requestFailed: error getting token for notifications");
                }
            });
            Requests rh = Requests.getInstance();

            rh.getQueue().add(request);
        } catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    public static void createUser(final JsonRequest.CompletionHandler<User> handler) {
        try
        {
            // Get the token
            JSONObject params = new JSONObject();
            params.put("token", FirebaseInstanceId.getInstance().getToken());

            // Make request
            JsonRequest<User> request = new JsonRequest<>(Request.Method.POST, Requests.BASE_URL + "/users", params, new JsonRequest.ObjectCreationHandler<User>() {
                @Override
                public User fromJson(JSONObject response) throws JSONException {
                    User newuser = new User(response.getInt("id"));
                    newuser.setToken(response.getString("token"));
                    return newuser;
                }
            }, handler);
            Requests rh = Requests.getInstance();

            rh.getQueue().add(request);
        } catch (JSONException e)
        {
            e.printStackTrace();
        }
    }
}
