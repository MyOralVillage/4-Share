package org.myoralvillage.cashcalculator;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.myoralvillage.cashcalculatormodule.models.AppStateModel;
import org.myoralvillage.cashcalculatormodule.models.CurrencyModel;
import org.myoralvillage.cashcalculatormodule.models.MathOperationMode;
import org.myoralvillage.cashcalculatormodule.services.AppService;
import org.myoralvillage.cashcalculatormodule.services.CountingService;
import org.myoralvillage.cashcalculatormodule.views.CountingTableView;
import org.myoralvillage.cashcalculatormodule.views.CurrencyScrollbarView;
import org.myoralvillage.cashcalculatormodule.views.NumberPadView;
import org.myoralvillage.cashcalculatormodule.views.listeners.SwipeListener;

import java.math.BigDecimal;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private final String APP_STATE_KEY = "appState";
    private AppService service;
    private CurrencyModel currCurrency;
    private CountingTableView countingTableView;
    private NumberPadView numberPadView;
    private TextView entryView;
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

        countingTableView = findViewById(R.id.counting_table);
        countingTableView.initDenominationModels(currCurrency.getDenominations());

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
                numberPadView.setVisibility(View.VISIBLE);
                entryView.setVisibility(View.VISIBLE);
                entryView.setText("R0");
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

        numberPadView = findViewById(R.id.number_pad);
        entryView = findViewById(R.id.entry);
        entryView.setVisibility(View.INVISIBLE);
        final StringBuilder stringBuilder = new StringBuilder();
        numberPadView.setOnItemClickListener((parent, view, position, id) -> {
            String text = ((TextView) view).getText().toString();
            switch (position) {
                case 0:
                    service.setValue(new BigDecimal(Integer.valueOf(stringBuilder.toString())));
                    stringBuilder.setLength(0);
                    entryView.setVisibility(View.INVISIBLE);
                    numberPadView.setVisibility(View.INVISIBLE);
                    refreshCountingTable();
                    return;
                case 2:
                    if (stringBuilder.length() > 0) {
                        stringBuilder.setLength(stringBuilder.length() - 1);
                    }
                    break;
                default:
                    stringBuilder.append(text);
                    break;
            }
            entryView.setText(String.format(Locale.CANADA, "R%s",
                    stringBuilder.length() > 0 ? stringBuilder.toString() : "0"));
        });
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

        if (service.getOperationMode() == MathOperationMode.STANDARD && service.getValue().equals(BigDecimal.ZERO))
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
