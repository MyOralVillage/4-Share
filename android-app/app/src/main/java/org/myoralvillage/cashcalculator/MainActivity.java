package org.myoralvillage.cashcalculator;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import org.myoralvillage.cashcalculatormodule.fragments.CashCalculatorFragment;

public class MainActivity extends AppCompatActivity {

    private static String currencyCode;
    private static boolean numericMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);
        CashCalculatorFragment fragment = (CashCalculatorFragment) getSupportFragmentManager()
                .findFragmentById(R.id.CountingTableFragment);

        numericMode = false;
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            if (extras.containsKey("currencyCode")) {
                currencyCode = extras.getString("currencyCode");
            }
            if (extras.containsKey("numericMode")) {
                numericMode = extras.getBoolean("numericMode", false);
            }

        }

        if (fragment != null) {
            fragment.initialize(currencyCode);
            if (numericMode) {
                fragment.switchAppMode();
            }
        }
    }
}
