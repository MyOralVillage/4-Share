package org.myoralvillage.cashcalculator;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import org.myoralvillage.cashcalculator.fragments.CashCalculatorFragment;
import org.myoralvillage.cashcalculatormodule.models.CurrencyModel;
import org.myoralvillage.cashcalculatormodule.views.CurrencyScrollbarView;

import java.math.BigDecimal;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        SettingActivity.getSettingService().getCurrencyName();
        setContentView(R.layout.activity_main);
    }

    public static String getCurrency(){
        return SettingActivity.getSettingService().getCurrencyName();
    }

    public static BigDecimal getCurrentValue(){
        return CashCalculatorFragment.getValue();
    }
}