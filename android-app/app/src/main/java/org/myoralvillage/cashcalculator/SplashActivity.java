package org.myoralvillage.cashcalculator;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.content.Intent;

import android.widget.Button;
import android.widget.ImageView;

import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
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
            switchToVideo();
        });
    }

    private void switchToVideo(){
        startActivity(new Intent(this, VideoActivity.class));
        finish();
    }

    private static void setDefaultImage(Button setting){
        String systemLangauge = Locale.getDefault().getCountry();
        switch (systemLangauge){
            case "PK":
                setting.setBackgroundResource(R.drawable.pkr);
                break;
            case "BD":
                setting.setBackgroundResource(R.drawable.ban);
                break;
            case "US":
                setting.setBackgroundResource(R.drawable.usa);
                break;
            case "IN":
                setting.setBackgroundResource(R.drawable.india);
                break;
            default:
                setting.setBackgroundResource(R.drawable.kes);
                break;
        }
    }

    private void settingButtonListener() {
        Button setting = findViewById(R.id.setting);

        setDefaultImage(setting);
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
