package ca.carleton.three_thousand_chore.comp3004;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by estitweg on 2017-10-12.
 */

public class RequestHelper {
    public static String BASE_URL = "http://10.0.2.2:3000";
    private static RequestHelper instance;
    private static int contextHash;

    public RequestQueue queue;

    public static RequestHelper getInstance(Context c) {
        // Only create a new context if the hashcodes don't match.
        if (instance == null || contextHash != c.hashCode()) {
            contextHash = c.hashCode();
            instance = new RequestHelper(c);
        }

        return instance;
    }

    public static RequestHelper getInstance() {
        if (instance == null) {
            throw new IllegalStateException("Can't initialize without a context.");
        }

        return instance;
    }

    public RequestHelper(Context context) {
        queue = Volley.newRequestQueue(context);
    }

    public RequestQueue getQueue() {
        return queue;
    }
}
