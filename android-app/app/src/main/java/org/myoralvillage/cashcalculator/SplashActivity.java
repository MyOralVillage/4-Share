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

import java.util.Currency;
import java.util.Locale;

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

    private void switchToVideo() {
        startActivity(new Intent(this, VideoActivity.class));
        finish();
    }

    private void setDefaultImage(Button setting) {
        new CurrencyService(getApplicationContext()).call(currencies -> {
            String currency = Currency.getInstance(Locale.getDefault()).getCurrencyCode();
            if (currencies != null) {
                currency = currencies[0];
            }

            int id = CurrencyService.getCurrencyResource(currency);

            if (id < 0) {
                id = R.drawable.kes;
            }

            int finalId = id;
            runOnUiThread(() -> setting.setBackgroundResource(finalId));
        });
    }

    private void settingButtonListener() {
        Button setting = findViewById(R.id.setting);

        setDefaultImage(setting);
        setting.setOnClickListener((e) -> {
            exit = true;
            switchToSetting();
        });
    }

    private void switchToSetting() {
        startActivity(new Intent(this, SettingActivity.class));
        finish();
    }
}
