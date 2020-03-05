package org.myoralvillage.cashcalculator;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.content.Intent;

import android.widget.Button;

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
        settingButtonListener();
        tmp = new Intent(this, MainActivity.class);
        timer.start();
    }

    private void settingButtonListener() {
        Button setting = findViewById(R.id.setting);
        setting.setOnClickListener((e) -> {
            exit = true;
            switchToSetting();
        });
    }

    Thread timer = new Thread() {
        public void run() {
            try {
                sleep(7000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                if (! exit){
                    startActivity(tmp);
                    finish();
                }
            }
        }
    };

    private void switchToSetting(){
        startActivity(new Intent(this, SettingActivity.class));
        finish();
    }
}
