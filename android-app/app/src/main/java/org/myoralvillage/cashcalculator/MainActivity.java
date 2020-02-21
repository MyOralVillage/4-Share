package org.myoralvillage.cashcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
    private CountingTableView countingTableView;
    private TextView sumView;
    private Button calculateButton;

    CountingService countingService = new CountingService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_main);
        service = new AppService();

        sumView = findViewById(R.id.sum_view);
        final CurrencyScrollbarView currencyScrollbarView = findViewById(R.id.currency_scrollbar);
        currencyScrollbarView.setCurrency("PKR");
        this.currCurrency = currencyScrollbarView.getCurrency();

        countingTableView = findViewById(R.id.counting_table);
        countingTableView.initDenominationModels(currCurrency.getDenominations());

        currencyScrollbarView.setCurrencyTapListener(denomination -> {
            service.setValue(service.getValue().add(denomination.getValue()));
            refreshCountingTable();
        });

        Button leftButton = findViewById(R.id.left_button);
        Button rightButton = findViewById(R.id.right_button);
        Button upButton = findViewById(R.id.up_button);

        leftButton.setOnClickListener((e) -> {
            service.subtract();
            refreshCountingTable();
        });

        rightButton.setOnClickListener((e) -> {
            service.add();
            refreshCountingTable();
        });

        upButton.setOnClickListener((e) -> {
            service.multiply();
            refreshCountingTable();
        });

        calculateButton = findViewById(R.id.calculate_button);
        calculateButton.setOnClickListener((e) -> {
            service.calculate();
            refreshCountingTable();
        });

        refreshCountingTable();
    }

    private void refreshCountingTable() {
        calculateButton.setVisibility(View.VISIBLE);
        switch(service.getOperationMode()) {
            case STANDARD:
                calculateButton.setVisibility(View.INVISIBLE);
                break;
            case ADD:
                calculateButton.setText("+");
                break;
            case SUBTRACT:
                calculateButton.setText("-");
                break;
            case MULTIPLY:
                calculateButton.setText("X");
                break;
        }

        sumView.setText(String.format(Locale.CANADA, "%s %s",
                currCurrency.getCurrency().getSymbol(), service.getValue()));

        countingTableView.setDenominations(currCurrency.getDenominations().iterator(),
                countingService.allocation(service.getValue().doubleValue(), currCurrency));
    }
}
