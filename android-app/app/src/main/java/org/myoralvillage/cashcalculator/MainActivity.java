package org.myoralvillage.cashcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import org.myoralvillage.cashcalculatormodule.models.CurrencyModel;
import org.myoralvillage.cashcalculatormodule.services.AppService;
import org.myoralvillage.cashcalculatormodule.views.CountingTableView;
import org.myoralvillage.cashcalculatormodule.views.CurrencyScrollbarView;
import org.myoralvillage.cashcalculatormodule.services.CountingService;
import org.myoralvillage.cashcalculatormodule.views.listeners.SwipeListener;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private AppService service;
    private CurrencyModel currCurrency;
    private CountingTableView countingTableView;
    private TextView sumView;
    private ImageView calculateButton;

    CountingService countingService = new CountingService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        service = new AppService();

        sumView = findViewById(R.id.sum_view);
        final CurrencyScrollbarView currencyScrollbarView = findViewById(R.id.currency_scrollbar);
        currencyScrollbarView.setCurrency("PKR");
        this.currCurrency = currencyScrollbarView.getCurrency();

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        System.out.print("Width: " + width);

        countingTableView = findViewById(R.id.counting_table);
        countingTableView.initDenominationModels(currCurrency.getDenominations(), width, height);

        currencyScrollbarView.setCurrencyTapListener(denomination -> {
            service.setValue(service.getValue().add(denomination.getValue()));
            refreshCountingTable();
        });

        calculateButton = findViewById(R.id.calculate_button);
        calculateButton.setOnClickListener((e) -> {
            service.calculate();
            refreshCountingTable();
        });

        countingTableView.setOnTouchListener(new SwipeListener(MainActivity.this) {
            @Override
            public void swipeLeft() {
                // Dragging towards the right
                service.add();
                refreshCountingTable();
            }

            @Override
            public void swipeRight() {
                // Dragging towards the left
                service.subtract();
                refreshCountingTable();
            }

            @Override
            public void swipeUp() {
                // Dragging towards the bottom
                // TODO: Enter numeric/image mode
            }

            @Override
            public void swipeDown() {
                // Dragging towards the top
                service.multiply();
                refreshCountingTable();
            }
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
                calculateButton.setImageResource(R.drawable.operator_plus);
                break;
            case SUBTRACT:
                calculateButton.setImageResource(R.drawable.operator_minus);
                break;
            case MULTIPLY:
                calculateButton.setImageResource(R.drawable.operator_times);
                break;
        }

        sumView.setText(String.format(Locale.CANADA, "%s %s",
                currCurrency.getCurrency().getSymbol(), service.getValue()));

        countingTableView.setDenominations(currCurrency.getDenominations().iterator(),
                countingService.allocation(service.getValue().doubleValue(), currCurrency));
    }
}
