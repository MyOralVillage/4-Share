package org.myoralvillage.cashcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.TextView;

import org.myoralvillage.cashcalculatormodule.models.CurrencyModel;
import org.myoralvillage.cashcalculatormodule.services.AppService;
import org.myoralvillage.cashcalculatormodule.views.CountingTableView;
import org.myoralvillage.cashcalculatormodule.views.CurrencyScrollbarView;
import org.myoralvillage.cashcalculatormodule.services.CountingService;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private AppService service;
    private CurrencyModel currCurrency;

    CountingService countingService = new CountingService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_main);
        service = new AppService();

        final TextView sumView = findViewById(R.id.sum_view);
        final CurrencyScrollbarView currencyScrollbarView = findViewById(R.id.currency_scrollbar);
        currencyScrollbarView.setCurrency("PKR");
        this.currCurrency = currencyScrollbarView.getCurrency();

        final CountingTableView countingTableView = findViewById(R.id.counting_table);
        countingTableView.initDenominationModels(currCurrency.getDenominations());
        currencyScrollbarView.setCurrencyTapListener(denomination -> {
            service.setValue(service.getValue().add(denomination.getValue()));

            sumView.setText(String.format(Locale.CANADA, "%s %s",
                    currCurrency.getCurrency().getSymbol(), service.getValue()));
            countingTableView.setDenominations(currCurrency.getDenominations().iterator(),
                    countingService.allocation(service.getValue().doubleValue(), currCurrency));
        });
    }
}
