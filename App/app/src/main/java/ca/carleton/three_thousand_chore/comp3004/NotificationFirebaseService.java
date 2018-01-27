package ca.carleton.three_thousand_chore.comp3004;

import android.app.Service;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import com.google.firebase.messaging.FirebaseMessagingService;

import org.json.JSONException;
import org.json.JSONObject;

import ca.carleton.three_thousand_chore.comp3004.models.HelpRequest;
import ca.carleton.three_thousand_chore.comp3004.models.Notification;

/**
 * Created by jackmccracken on 2017-10-23.
 */

public class NotificationFirebaseService extends FirebaseMessagingService {
    public static String HR_BROADCAST_NAME = "NewHelpRequest";
    public static String NOTIFICATION_BROADCAST_NAME = "Notification";

    @Override
    public void onCreate() {
        super.onCreate();
    }

    // Gets run when a notification happens while the app is foregrounded
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        if (remoteMessage.getData().containsKey("help_request")) {
            try {
                String jsonString = (String)remoteMessage.getData().get("help_request");
                JSONObject helpRequestJson = new JSONObject(jsonString);
                HelpRequest request = HelpRequest.fromJson(helpRequestJson);

                Intent intent = new Intent(HR_BROADCAST_NAME);
                intent.putExtra("help_request", request);

                LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if (remoteMessage.getData().containsKey("notification")) {
            try {
                Notification notif = Notification.fromJson(new JSONObject(remoteMessage.getData().get("notification")));
                Intent intent = new Intent(NOTIFICATION_BROADCAST_NAME);
                intent.putExtra("notification", notif);

                LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
