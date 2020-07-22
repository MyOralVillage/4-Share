package org.myoralvillage.cashcalculator;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;



public class TutorialActivity extends AppCompatActivity implements View.OnClickListener {

    String currencyName = null;
    boolean numericMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_tutorial);

        Button intro_video = findViewById(R.id.intro_video);
        intro_video.setOnClickListener(this);
        Button advanced_video = findViewById(R.id.advanced_video);
        advanced_video.setOnClickListener(this);
        Button numeric_video = findViewById(R.id.numeric_video);
        numeric_video.setOnClickListener(this);

    }

    private void switchtointrovideo() {
        Intent intent = new Intent(this, IntroVideoActivity.class);
        intent.putExtra("currencyName", currencyName);
        intent.putExtra("numericMode", numericMode);
        startActivity(intent);
        finish();
    }

    private void switchtoadvancedvideo() {
        Intent intent = new Intent(this, AdvancedVideoActivity.class);
        intent.putExtra("currencyName", currencyName);
        intent.putExtra("numericMode", numericMode);
        startActivity(intent);
        finish();
    }

    private void switchtonumericvideo() {
        Intent intent = new Intent(this, NumericVideoActivity.class);
        intent.putExtra("currencyName", currencyName);
        intent.putExtra("numericMode", numericMode);
        startActivity(intent);
        finish();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.intro_video:
                switchtointrovideo();
                break;

            case R.id.advanced_video:
                switchtoadvancedvideo();
                break;

            case R.id.numeric_video:
                switchtonumericvideo();
                break;
        }
    }
}
