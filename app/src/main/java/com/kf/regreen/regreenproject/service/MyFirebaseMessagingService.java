package com.kf.regreen.regreenproject.service;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.kf.regreen.regreenproject.Activities.ExpertsFaqActivity;
import com.kf.regreen.regreenproject.Activities.HomeScreenActivity;
import com.kf.regreen.regreenproject.Utils.Constant;
import com.kf.regreen.regreenproject.Utils.NotificationUtils;
import com.kf.regreen.regreenproject.Utils.PreferencesUtils;

import org.json.JSONException;
import org.json.JSONObject;

import static com.kf.regreen.regreenproject.Utils.Constant.TABPOSITION;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();

    private NotificationUtils notificationUtils;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.e(TAG, "From: " + remoteMessage.getFrom());

        if (remoteMessage == null)
            return;

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            handleNotification(remoteMessage);
        }

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            try {
                JSONObject json = new JSONObject(remoteMessage.getData().toString());
                handleDataMessage(json);
            } catch (Exception e) {
            }
        }
    }

    private void handleNotification(RemoteMessage remoteMessage) {
        if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
            // app is in foreground, broadcast the push message
//            Intent pushNotification = new Intent(Constant.PUSH_NOTIFICATION);
//            pushNotification.putExtra("message", message);
//            LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);
            Intent resultIntent = new Intent(getApplicationContext(), HomeScreenActivity.class);
            resultIntent.putExtra("message", remoteMessage.getNotification().getBody());

            showNotificationMessage(getApplicationContext(), remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody(), "" + remoteMessage.getSentTime(), resultIntent);
            // play notification sound
//            NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
//
//            notificationUtils.playNotificationSound();
        } else {
            // If the app is in background, firebase itself handles the notification
        }
    }

    private void handleDataMessage(JSONObject json) {
        Log.e(TAG, "push json: " + json.toString());
        try {
            JSONObject data = json.getJSONObject("data");

            String title = data.getString("title");
            String message = data.getString("message");
            String timestamp = data.getString("timestamp");
            int type = data.getInt("type");


           /* if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
                // app is in foreground, broadcast the push message
                Intent pushNotification = new Intent(Constant.PUSH_NOTIFICATION);
                pushNotification.putExtra("message", message);
                LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

                // play notification sound
                NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
                notificationUtils.playNotificationSound();
            } else {*/
            // app is in background, show the notification in notification tray

            Intent resultIntent = null;
           /* if (type == 1 || type == 2) {
                resultIntent = new Intent(getApplicationContext(), ExpertsFaqActivity.class);
            } else {*/
            resultIntent = new Intent(getApplicationContext(), HomeScreenActivity.class);
            resultIntent.putExtra("message", message);
            resultIntent.putExtra("type", type);
            PreferencesUtils preferencesUtils = new PreferencesUtils(getApplicationContext());
            int count = 1;
            if (type == 1 || type == 2 || type == 3) {
                if (type == 1 || type == 2) {
                    Intent pushNotification = new Intent(Constant.PUSH_NOTIFICATION);
                    pushNotification.putExtra("message", message);
                    LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);
                    count = count + preferencesUtils.getNotificationExpert();
                    preferencesUtils.setNotificationExpert(count);
                }
                resultIntent.putExtra(TABPOSITION, 1);
            } else if (type == 4 || type == 5) {
                Intent pushNotification = new Intent(Constant.PUSH_NOTIFICATION);
                pushNotification.putExtra("message", message);
                LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);
                count = count + preferencesUtils.getNotificationReward();
                preferencesUtils.setNotificationReward(count);
                resultIntent.putExtra(TABPOSITION, 4);
            }

            // check for image attachment
//            if (TextUtils.isEmpty(imageUrl)) {
            showNotificationMessage(getApplicationContext(), title, message, timestamp, resultIntent);
           /* } else {
                // image is present, show notification with image
                showNotificationMessageWithBigImage(getApplicationContext(), title, message, timestamp, resultIntent, imageUrl);
            }*/
//            }
        } catch (JSONException e) {
            Log.e(TAG, "Json Exception: " + e.toString());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.toString());
        }
    }

    /**
     * Showing notification with text only
     */
    private void showNotificationMessage(Context context, String title, String message, String timeStamp, Intent intent) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent);
    }

    /**
     * Showing notification with text and image
     */
    private void showNotificationMessageWithBigImage(Context context, String title, String message, String timeStamp, Intent intent, String imageUrl) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent, imageUrl);
    }
}
