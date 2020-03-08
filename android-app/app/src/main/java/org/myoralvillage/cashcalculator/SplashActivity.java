package org.myoralvillage.cashcalculator;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.content.Intent;

import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    Intent tmp;
    boolean exit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        mainActivityButtonListener();
        settingButtonListener();
    }

    private void mainActivityButtonListener() {
        ImageView setting = findViewById(R.id.main);
        setting.setOnClickListener((e) -> {
            exit = true;
            switchToMain();
        });
    }

    private void switchToMain(){
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private void settingButtonListener() {
        Button setting = findViewById(R.id.setting);
        setting.setOnClickListener((e) -> {
            exit = true;
            switchToSetting();
        });
    }

    private void switchToSetting(){
        startActivity(new Intent(this, SettingActivity.class));
        finish();
    }
}
