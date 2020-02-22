package org.myoralvillage.cashcalculator;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.myoralvillage.cashcalculatormodule.models.AppStateModel;
import org.myoralvillage.cashcalculatormodule.models.CurrencyModel;
import org.myoralvillage.cashcalculatormodule.models.MathOperationModel;
import org.myoralvillage.cashcalculatormodule.services.AppService;
import org.myoralvillage.cashcalculatormodule.services.CountingService;
import org.myoralvillage.cashcalculatormodule.views.CountingTableView;
import org.myoralvillage.cashcalculatormodule.views.CurrencyScrollbarView;
import org.myoralvillage.cashcalculatormodule.views.listeners.SwipeListener;

import java.math.BigDecimal;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private final String APP_STATE_KEY = "appState";
    private AppService service;
    private CurrencyModel currCurrency;
    private CountingTableView countingTableView;
    private TextView sumView;
    private ImageView calculateButton;
    private ImageView clearButton;

    CountingService countingService = new CountingService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey(APP_STATE_KEY))
            service = new AppService((AppStateModel) extras.getSerializable(APP_STATE_KEY));
        else service = new AppService();

        sumView = findViewById(R.id.sum_view);
        final CurrencyScrollbarView currencyScrollbarView = findViewById(R.id.currency_scrollbar);
        currencyScrollbarView.setCurrency("PKR");
        this.currCurrency = currencyScrollbarView.getCurrency();

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

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

        clearButton = findViewById(R.id.clear_button);
        clearButton.setOnClickListener((e) -> {
            service.reset();
            refreshCountingTable();
        });

        countingTableView.setOnTouchListener(new SwipeListener(MainActivity.this) {
            @Override
            public void swipeLeft() {
                // Dragging towards the right
                service.add();
                switchState();
                overridePendingTransition(R.anim.activity_left_in,R.anim.activity_left_out);
                finish();
            }

            @Override
            public void swipeRight() {
                // Dragging towards the left
                service.subtract();
                switchState();
                overridePendingTransition(R.anim.activity_right_in,R.anim.activity_right_out);
                finish();
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
                switchState();
                overridePendingTransition(R.anim.activity_down_in,R.anim.activity_down_out);
                finish();
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

        if (service.getOperationMode() == MathOperationModel.MathOperationMode.STANDARD && service.getValue().equals(BigDecimal.ZERO))
            clearButton.setVisibility(View.INVISIBLE);
        else
            clearButton.setVisibility(View.VISIBLE);

        sumView.setText(String.format(Locale.CANADA, "%s %s",
                currCurrency.getCurrency().getSymbol(), service.getValue()));

        countingTableView.setDenominations(currCurrency.getDenominations().iterator(),
                countingService.allocate(service.getValue(), currCurrency));
    }

    private void switchState() {
        Intent intent = new Intent(MainActivity.this, MainActivity.class);
        intent.putExtra(APP_STATE_KEY, service.getAppState());
        startActivity(intent);
    }
}
