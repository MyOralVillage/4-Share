package org.myoralvillage.cashcalculator;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import org.myoralvillage.cashcalculatormodule.services.SettingService;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

public class SettingActivity extends AppCompatActivity {
    private ArrayList<Button> currency = new ArrayList<>();
    private static SettingService settingService = new SettingService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_setting);

        currency.add(findViewById(R.id.PKR));
        currencyButtonListener();
    }

    public static SettingService getSettingService(){
        return settingService;
    }

    private void currencyButtonListener() {
        //Button pkr = findViewById(R.id.pkr);
        for (Button button:currency){
            button.setOnClickListener((e) -> {
                settingService.setCurrencyName("PKR");
                switchToMainActivity();
            });
        }
    }

    private void switchToMainActivity(){
        startActivity(new Intent(this, MainActivity.class));
    }
}
