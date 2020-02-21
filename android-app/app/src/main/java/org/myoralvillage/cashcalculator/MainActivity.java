package org.myoralvillage.cashcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.myoralvillage.cashcalculatormodule.HelloWorldMessage;
import org.myoralvillage.cashcalculatormodule.models.CurrencyModel;
import org.myoralvillage.cashcalculatormodule.models.DenominationModel;
import org.myoralvillage.cashcalculatormodule.views.CountingTableView;
import org.myoralvillage.cashcalculatormodule.views.CurrencyScrollbarView;
import org.myoralvillage.cashcalculatormodule.views.listeners.CurrencyTapListener;
import org.myoralvillage.cashcalculatormodule.services.CountingService;
import org.myoralvillage.cashcalculatormodule.views.listeners.SwipeListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private CurrencyModel currCurrency;
    private HelloWorldMessage message = new HelloWorldMessage();
    private double currentSum = 0;

    CountingService countingService = new CountingService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_main);

        final TextView textView = findViewById(R.id.message_view);
        final TextView sumView = findViewById(R.id.sum_view);
        textView.setText(message.getMessage());

        final CurrencyScrollbarView currencyScrollbarView = findViewById(R.id.currency_scrollbar);
        currencyScrollbarView.setCurrency("PKR");
        this.currCurrency = currencyScrollbarView.getCurrency();

        final CountingTableView countingTableView = findViewById(R.id.counting_table);
        countingTableView.initDenominationModels(currCurrency.getDenominations());
        currencyScrollbarView.setCurrencyTapListener(denomination -> {
            currentSum += denomination.getValue().doubleValue();
            textView.setText(String.format(Locale.CANADA, "Tapped on %s",
                    denomination.getValue()));
            sumView.setText(String.format(Locale.CANADA, "%s %s",
                    currCurrency.getCurrency().getSymbol(), currentSum));
            countingTableView.setDenominations(currCurrency.getDenominations().iterator(),
                    countingService.allocation(currentSum, currCurrency));
        });

        //final TextView gesture = findViewById(R.id.swipe);
        countingTableView.setOnTouchListener(new SwipeListener(MainActivity.this) {
            @Override
            public void swipeLeft() {
                Toast.makeText(MainActivity.this, "Swipe Left gesture detected", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void swipeRight() {
                Toast.makeText(MainActivity.this, "Swipe Right gesture detected", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void swipeUp() {
                Toast.makeText(MainActivity.this, "Swipe Up gesture detected", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void swipeDown() {
                super.swipeRight();
                Toast.makeText(MainActivity.this, "Swipe Down gesture detected", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
