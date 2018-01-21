package io.repro.reprodemo.bodysizechecker;

import android.text.TextUtils;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by yuichiorimo on 2018/01/03.
 */

public class MyInstanceIdService extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {
        String token = FirebaseInstanceId.getInstance().getToken();
        // テスト用にLogcatに出力
        if (!TextUtils.isEmpty(token)) {
            android.util.Log.d("FCM-TEST", "token = " + token);
        }
    }
}
