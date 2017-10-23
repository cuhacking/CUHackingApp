package ca.carleton.three_thousand_chore.comp3004;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import ca.carleton.three_thousand_chore.comp3004.models.User;

/**
 * Created by jackmccracken on 2017-10-19.
 */

public class JsonRequest<T> extends JsonObjectRequest {
    public interface CompletionHandler<T> {
        void requestSucceeded(T object);
        void requestFailed(String errorMessage);
    }

    public interface ObjectCreationHandler<T> {
        T fromJson(JSONObject obj) throws JSONException;
    }

    // For creating objects
    public JsonRequest(int method, String url, JSONObject jsonRequest,
                       final ObjectCreationHandler<T> objectCreationHandler,
                       final CompletionHandler<T> completionHandler) {
        super(method, url, jsonRequest, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                T createdObject = null;
                try {
                    createdObject = objectCreationHandler.fromJson(response);
                    completionHandler.requestSucceeded(createdObject);
                } catch (JSONException e) {
                    Log.e(JsonRequest.class.getSimpleName(), "Server returned bad JSON: " + e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                completionHandler.requestFailed(errorToString(error));
            }
        });
    }

    // For editing objects
    public JsonRequest(int method, String url, JSONObject jsonRequest, final CompletionHandler<JSONObject> objectModifiedHandler) {
        super(method, url, jsonRequest, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                objectModifiedHandler.requestSucceeded(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                objectModifiedHandler.requestFailed(errorToString(error));
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
