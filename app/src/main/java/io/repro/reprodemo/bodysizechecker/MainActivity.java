package io.repro.reprodemo.bodysizechecker;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Set;

import com.google.firebase.iid.FirebaseInstanceId;

public class MainActivity extends AppCompatActivity {
    private static final String NECK = "NECK";
    private static final String SLEEVE = "SLEEVE";
    private static final String WAIST = "WAIST";
    private static final String INSEAM = "INSEAM";
    private EditText editNeck;
    private EditText editSleeve;
    private EditText editWaist;
    private EditText editInseam;

    private static final String TAG = "MainActivity";

    String token = FirebaseInstanceId.getInstance().getToken();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.bodybuilder)
                        .setContentTitle("My notification")
                        .setContentText("アプリ起動したね!")
                        .setBadgeIconType(1)
                        .setUsesChronometer(true)
                        .setAutoCancel(true);
// Creates an explicit intent for an Activity in your app
        Intent heightIntent = new Intent(this, HeightActivity.class);

// The stack builder object will contain an artificial back stack for the
// started Activity.
// This ensures that navigating backward from the Activity leads out of
// your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
// Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(HeightActivity.class);
// Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(heightIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
// mId allows you to update the notification later on.
        mNotificationManager.notify(0, mBuilder.build());

        mNotificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        int notifyID = 0;
//        NotificationCompat.Builder mNotifyBuilder  = new NotificationCompat.Builder(this)
//                .setContentTitle("New Message")
//                .setContentText("You've received New message!")
//                .setSmallIcon(R.drawable.ic_stat_ic_notification)
//                .setBadgeIconType(1)
//                .setWhen(60000)
//                .setAutoCancel(true);
//        mNotifyBuilder.setBadgeIconType(3);
//        mNotificationManager.notify(null,123,mNotifyBuilder.build());



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create channel to show notifications.
            String channelId = getString(R.string.default_notification_channel_id);
            String channelName = getString(R.string.default_notification_channel_name);
            NotificationManager notificationManager =
                    getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(new NotificationChannel(channelId,
                    channelName, NotificationManager.IMPORTANCE_LOW));
        }
        if (getIntent().getExtras() != null) {
            for (String key : getIntent().getExtras().keySet()) {
                Object value = getIntent().getExtras().get(key);
                String foo = getIntent().getStringExtra(NECK);
                Toast.makeText(MainActivity.this, foo, Toast.LENGTH_SHORT).show();
                Log.d(TAG, "key: " + key + "value: " + value);
            }
        }

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String neck = pref.getString(NECK, "");
        String sleeve = pref.getString(SLEEVE, "");
        String waist = pref.getString(WAIST, "");
        String inseam = pref.getString(INSEAM, "");
        editNeck = (EditText) findViewById(R.id.neck);
        editSleeve = (EditText) findViewById(R.id.sleeve);
        editWaist = (EditText) findViewById(R.id.waist);
        editInseam = (EditText) findViewById(R.id.inseam);

        editNeck.setText(neck);
        editSleeve.setText(sleeve);
        editWaist.setText(waist);
        editInseam.setText(inseam);


        findViewById(R.id.height_Button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HeightActivity.class);
                startActivity(intent);
            }
        });
    }

    public void onSubscribeTapped(View view) {
        Button subscribeButton = findViewById(R.id.subscribeButton);
        subscribeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseMessaging.getInstance().subscribeToTopic("news");

                String msg = "Subscribed to new topic";
                Log.d(TAG, msg);
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onLogTokenButton(View view) {
        Button logTokenButton = findViewById(R.id.logTokenButton);
        logTokenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String token = FirebaseInstanceId.getInstance().getToken();

                String msg = getString(R.string.msg_token_fmt, token);
                Log.d(TAG, msg);
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();

                MyFirebaseInstanceIDService mfiis = new MyFirebaseInstanceIDService();
                mfiis.onTokenRefresh();
            }
        });
    }

    public void onSaveTapped(View view) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(NECK, editNeck.getText().toString().trim());
        editor.putString(SLEEVE, editSleeve.getText().toString().trim());
        editor.putString(WAIST, editWaist.getText().toString().trim());
        editor.putString(INSEAM, editInseam.getText().toString().trim());
        editor.commit();
    }
}
