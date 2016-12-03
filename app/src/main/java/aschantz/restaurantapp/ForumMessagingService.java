package aschantz.restaurantapp;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by aschantz on 12/2/16.
 */
public class ForumMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        String from = remoteMessage.getFrom();
        String payload = "";
        if (remoteMessage.getData().size() > 0) {
            payload += remoteMessage.getData();
        }
        String body = remoteMessage.getNotification().getBody();

        Log.d("FIREBASE_AITForum_PUSH", from+"\n"+payload+"\n"+body);
    }
}
