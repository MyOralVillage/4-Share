package org.myoralvillage.cashcalculator;

import java.util.Currency;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import org.myoralvillage.cashcalculator.fragments.CashCalculatorFragment;
import org.myoralvillage.cashcalculatormodule.models.CurrencyModel;
import org.myoralvillage.cashcalculatormodule.views.CurrencyScrollbarView;

import java.math.BigDecimal;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;

//import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
    }

    public  Currency getCurrency(){
        //CurrencyModel currency = CurrencyModel.loadCurrencyModel(SettingActivity.getSettingService().getCurrencyName(), getResources(), getContext());
        CurrencyModel currency = new CurrencyModel(SettingActivity.getSettingService().getCurrencyName());
        return currency.getCurrency();
    }

    public BigDecimal getCurrentValue(){
        CashCalculatorFragment fragment = new CashCalculatorFragment();
        return fragment.getValue();
    }
}