package ca.carleton.three_thousand_chore.comp3004;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

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
                if (error.getMessage() != null) {
                    completionHandler.requestFailed(error.getMessage());
                }
                else if (error.getCause() != null) {
                    completionHandler.requestFailed(error.getCause().getMessage());
                }
                else if (error instanceof TimeoutError) {
                    completionHandler.requestFailed("Timeout while connecting to the server. Please try again");
                }
                else {
                    completionHandler.requestFailed("Unknown error");
                }
            }
        });
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json");
        headers.putAll(super.getHeaders());
        return headers;
    }
}
