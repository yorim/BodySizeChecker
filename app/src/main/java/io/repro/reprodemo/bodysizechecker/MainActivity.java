package io.repro.reprodemo.bodysizechecker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

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
    String token = FirebaseInstanceId.getInstance().getToken();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String neck = pref.getString(NECK,"");
        String sleeve = pref.getString(SLEEVE,"");
        String waist = pref.getString(WAIST,"");
        String inseam = pref.getString(INSEAM,"");
        editNeck = (EditText)findViewById(R.id.neck);
        editSleeve = (EditText)findViewById(R.id.sleeve);
        editWaist = (EditText)findViewById(R.id.waist);
        editInseam = (EditText)findViewById(R.id.inseam);

        editNeck.setText(neck);
        editSleeve.setText(sleeve);
        editWaist.setText(waist);
        editInseam.setText(inseam);

        findViewById(R.id.height_Button).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this,HeightActivity.class);
                startActivity(intent);
            }
        });
    }
    public void onSaveTapped(View view){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(NECK,editNeck.getText().toString().trim());
        editor.putString(SLEEVE,editSleeve.getText().toString().trim());
        editor.putString(WAIST,editWaist.getText().toString().trim());
        editor.putString(INSEAM,editInseam.getText().toString().trim());
        editor.commit();
    }
}
