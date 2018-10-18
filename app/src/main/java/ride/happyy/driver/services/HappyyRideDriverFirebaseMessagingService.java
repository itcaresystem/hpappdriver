package ride.happyy.driver.services;

import android.content.Intent;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import ride.happyy.driver.activity.RequestConfirmationActivity;
import ride.happyy.driver.activity.SplashActivity;
import ride.happyy.driver.activity.TripDetailsActivity;
import ride.happyy.driver.config.Config;
import ride.happyy.driver.model.BasicBean;
import ride.happyy.driver.net.parsers.RequestParser;

public class HappyyRideDriverFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "HFMService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        String request_id="";
        // ...

        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.i(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.

        if (remoteMessage.getData().size() > 0) {
            Log.i(TAG, "Message data payload: " + remoteMessage.getData());
            Log.i(TAG, "Response: " + remoteMessage.getData().get("response"));
//happyriderequesttone
            request_id = remoteMessage.getData().get("request_id");
            initiateDriverRatingService(request_id);
        }
        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.i(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            String body = remoteMessage.getNotification().getBody();


         //   initiateDriverRatingService ("123");

        }

    }
        public void initiateDriverRatingService (String requestID){


            Log.i(TAG, "initiateDriverRatingService: >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> SERVICE STARTED>>>>>>>>>>>>>>>>>>>>>");

            Intent intent = new Intent(this, RequestConfirmationActivity.class);
            intent.putExtra("request_id", requestID);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

        }
    }


