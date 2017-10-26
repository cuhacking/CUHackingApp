package ca.carleton.three_thousand_chore.comp3004;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by estitweg on 2017-10-12.
 */

public class Requests
{
    public static String BASE_URL = "https://cuhacking.herokuapp.com"; //"http://10.0.2.2:3000";
    private static Requests instance;
    private static int contextHash;

    public RequestQueue queue;

    public static Requests getInstance(Context c) {
        // Only create a new context if the hashcodes don't match.
        if (instance == null || contextHash != c.hashCode()) {
            contextHash = c.hashCode();
            instance = new Requests(c);
        }
        return instance;
    }

    public static Requests getInstance() {
        if (instance == null) {
            throw new IllegalStateException("Can't initialize without a context.");
        }

        return instance;
    }

    public Requests(Context context) {
        queue = Volley.newRequestQueue(context);
    }

    public RequestQueue getQueue() {
        return queue;
    }
}
