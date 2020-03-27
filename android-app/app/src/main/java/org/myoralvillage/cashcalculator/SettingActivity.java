package org.myoralvillage.cashcalculator;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import org.myoralvillage.cashcalculatormodule.services.SettingService;

import java.util.HashMap;
import java.util.Map;

public class SettingActivity extends AppCompatActivity {
    private Map<String, Button> currencies = new HashMap<>();
    private static SettingService settingService = new SettingService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_setting);

        currencies.put("PKR", (Button) findViewById(R.id.PKR));
        currencies.put("KES", (Button) findViewById(R.id.KEN));
        currencyButtonListener();
    }

    public static SettingService getSettingService() {
        return settingService;
    }

    private void currencyButtonListener() {
        //Button pkr = findViewById(R.id.pkr);
        for (Map.Entry<String, Button> entry : currencies.entrySet()) {
            entry.getValue().setOnClickListener((e) -> {
                settingService.setCurrencyName(entry.getKey());
                switchToMainActivity();
            });
        }
    }

    private void switchToMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
