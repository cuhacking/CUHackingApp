package ca.carleton.three_thousand_chore.comp3004.models;

import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import ca.carleton.three_thousand_chore.comp3004.JsonRequest;
import ca.carleton.three_thousand_chore.comp3004.RequestHelper;

/**
 * Created by jackmccracken on 2017-10-09.
 */

public class HelpRequest {
    private int id;
    private String location;
    private String problem;
    private String[] mentors;
    private String status;

    public HelpRequest(int id, String location, String problem, String status) {
        this.id = id;
        this.location = location;
        this.problem = problem;
//        this.mentors = mentors;
        this.status = status;
    }

    public int getId() {
        return id;
    }
    
    public String getLocation() {
        return location;
    }

    public String getProblem() {
        return problem;
    }

    public String[] getMentors() {
        return mentors;
    }

    public String getStatus() {
        return status;
    }

    public static HelpRequest forId(int id) {
        return null;
    }

    public static void createHelpRequest(String location, String problem, int userId, String usersName, final JsonRequest.CompletionHandler<HelpRequest> handler) {
        try {
            JSONObject requestParams = new JSONObject();
            JSONObject helpRequestObject = new JSONObject();
            helpRequestObject.put("location", location);
            helpRequestObject.put("problem", problem);
            requestParams.put("help_request", helpRequestObject);
            requestParams.put("user_name", usersName);
            requestParams.put("user_id", userId);

            JsonRequest<HelpRequest> request = new JsonRequest<>(Request.Method.POST, RequestHelper.BASE_URL + "/help_requests", requestParams, new JsonRequest.ObjectCreationHandler<HelpRequest>() {
                @Override
                public HelpRequest fromJson(JSONObject obj) throws JSONException {
                    return new HelpRequest(obj.getInt("id"), obj.getString("location"), obj.getString("problem"), obj.getString("status"));
                }
            }, handler);
            RequestHelper rh = RequestHelper.getInstance();

            rh.getQueue().add(request);
        } catch (JSONException e) {
            Log.e(HelpRequest.class.getClass().getSimpleName(), "Failed to create help request: " + e.getMessage());
        }
    }
}
