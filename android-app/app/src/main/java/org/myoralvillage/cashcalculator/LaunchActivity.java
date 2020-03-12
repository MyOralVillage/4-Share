package org.myoralvillage.cashcalculator;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class LaunchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences firstShared = getSharedPreferences("isFirstLaunch", MODE_PRIVATE);
        boolean isFirst = firstShared.getBoolean("isFirstLaunch", true);
        SharedPreferences.Editor editor = firstShared.edit();
        if (isFirst) {
            startActivity(new Intent(this, VideoActivity.class));
            finish();
            editor.putBoolean("isFirstLaunch", false);
            editor.apply();
        } else {
            startActivity(new Intent(this, SplashActivity.class));
            finish();
        }
    }
}
