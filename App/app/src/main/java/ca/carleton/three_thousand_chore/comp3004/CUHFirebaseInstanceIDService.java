package ca.carleton.three_thousand_chore.comp3004;

import android.app.Service;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import ca.carleton.three_thousand_chore.comp3004.models.User;

/**
 * Created by jackmccracken on 2017-10-23.
 */

public class CUHFirebaseInstanceIDService extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {
        // TODO: Talk to the server and say that the token has changed
        super.onTokenRefresh();

        final SharedPreferences preferences = getSharedPreferences(getString(R.string.preferences_file_key), Context.MODE_PRIVATE);

        if (preferences.contains(getString(R.string.user_id_key))) {
            // Update user with new token
            new User(preferences.getInt(getString(R.string.user_id_key), -1)).setToken(FirebaseInstanceId.getInstance().getToken());
        }

        Log.i("FirebaseIDService Log", FirebaseInstanceId.getInstance().getToken());
    }
}
