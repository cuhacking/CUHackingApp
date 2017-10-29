package ca.carleton.three_thousand_chore.comp3004;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jackmccracken on 2017-10-27.
 */

public class JsonArrayRequest<T> extends com.android.volley.toolbox.JsonArrayRequest {
    public JsonArrayRequest(String url, final JsonObjectRequest.ObjectCreationHandler<T> objectCreationHandler, final JsonObjectRequest.CompletionHandler<List<T>> completionHandler) {
        super(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                List<T> createdObject = new ArrayList<>();
                try {
                    for (int i = 0; i < response.length(); i++) {
                        createdObject.add(objectCreationHandler.fromJson(response.getJSONObject(i)));
                    }

                    completionHandler.requestSucceeded(createdObject);
                } catch (JSONException e) {
                    Log.e(JsonObjectRequest.class.getSimpleName(), "Server returned bad JSON: " + e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                completionHandler.requestFailed(errorToString(error));
            }
        });
    }

    private static String errorToString(VolleyError error) {
        if (error.getMessage() != null) {
            return error.getMessage();
        }
        else if (error.getCause() != null) {
            return error.getCause().getMessage();
        }
        else if (error instanceof TimeoutError) {
            return "Timeout while connecting to the server. Please try again";
        }
        else {
            return "Unknown error";
        }
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json");
        headers.putAll(super.getHeaders());
        return headers;
    }
}
