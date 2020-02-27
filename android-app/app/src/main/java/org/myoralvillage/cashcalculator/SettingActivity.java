package org.myoralvillage.cashcalculator;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_setting);

        pkrButtonListener();
    }

    private void pkrButtonListener() {
        Button pkr = findViewById(R.id.pkr);
        pkr.setOnClickListener((e) -> {
            MainActivity.setCurrencyName("PKR");
            chmod();
        });
    }

    private void chmod(){
        Intent tmp;
        tmp = new Intent(this, MainActivity.class);
        startActivity(tmp);
    }
}
