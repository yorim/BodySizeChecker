package io.repro.reprodemo.bodysizechecker;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by yuichiorimo on 2018/01/06.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    private static final String TAG = "MyFirebaseIIDService";

    @Override
    public void onTokenRefresh(){
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG,"Refreshed Token: " + refreshedToken);
        sendRegistrationToServer(refreshedToken);
    }
    private static void sendRegistrationToServer(String token){

    }
}
