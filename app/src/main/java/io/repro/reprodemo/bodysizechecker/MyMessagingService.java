package io.repro.reprodemo.bodysizechecker;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

/**
 * Created by yuichiorimo on 2018/01/03.
 */

public class MyMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage.getNotification() != null) {
            RemoteMessage.Notification notification = remoteMessage.getNotification();
            String title = notification.getTitle();
            String body = notification.getBody();

            android.util.Log.d("FCM-TEST", "メッセージタイプ:通知\nタイトル:" + title + "\n本文:" + body);
        } else if(remoteMessage.getData().size() > 0) {
            Map<String,String> data = remoteMessage.getData();
            String subject = data.get("subject");
            String text = data.get("text");

            android.util.Log.d("FCM-TEST", "メッセージタイプ:通知\nタイトル:" + subject + "\n本文:" + text);
        }
    }
}
