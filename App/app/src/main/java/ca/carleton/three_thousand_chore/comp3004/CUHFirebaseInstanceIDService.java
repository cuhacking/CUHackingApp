package ca.carleton.three_thousand_chore.comp3004;

import android.app.Service;

import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by jackmccracken on 2017-10-23.
 */

public class CUHFirebaseInstanceIDService extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {
        // TODO: Talk to the server and say that the token has changed
        super.onTokenRefresh();
    }
}
