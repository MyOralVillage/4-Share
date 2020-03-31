package org.myoralvillage.cashcalculator;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import org.myoralvillage.cashcalculatormodule.services.CurrencyService;

public class SplashActivity extends AppCompatActivity {

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
        setting.setOnClickListener(e -> switchToVideo());
    }

    private void switchToVideo() {
        startActivity(new Intent(this, VideoActivity.class));
        finish();
    }

    private void setDefaultImage(Button setting) {
        new CurrencyService(getApplicationContext()).call(currencies -> {
            int id = CurrencyService.getCurrencyResource(currencies[0]);
            runOnUiThread(() -> setting.setBackgroundResource(id));
        });
    }

    private void settingButtonListener() {
        Button setting = findViewById(R.id.setting);

        setDefaultImage(setting);
        setting.setOnClickListener(e -> switchToSetting());
    }

    private void switchToSetting() {
        startActivity(new Intent(this, SettingActivity.class));
        finish();
    }
}
