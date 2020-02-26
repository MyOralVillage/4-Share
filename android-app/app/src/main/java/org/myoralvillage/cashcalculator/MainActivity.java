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

    private ImageView enterHistoryButton;
    private ImageView rightHistoryButton;
    private ImageView leftHistoryButton;


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
            if (!service.isInHistorySlideshow()) {
                service.calculate();
                refreshCountingTable();
            }
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
                if (!service.isInHistorySlideshow()) {
                    service.add();
                    switchState();
                    overridePendingTransition(R.anim.activity_left_in,R.anim.activity_left_out);
                    finish();

                }
            }

            @Override
            public void swipeRight() {
                // Dragging towards the left
                if (!service.isInHistorySlideshow()) {
                    service.subtract();
                    switchState();
                    overridePendingTransition(R.anim.activity_right_in,R.anim.activity_right_out);
                    finish();
                }
            }

            @Override
            public void swipeUp() {
                // Dragging towards the bottom
                // TODO: Enter numeric/image mode
                numberPadView.setVisibility(View.VISIBLE);
                entryView.setVisibility(View.VISIBLE);
                entryView.setText(String.format(Locale.CANADA, "%s 0",
                        currCurrency.getCurrency().getSymbol()));
            }

            @Override
            public void swipeDown() {
                // Dragging towards the top
                if (!service.isInHistorySlideshow()) {
                    service.multiply();
                    switchState();
                    overridePendingTransition(R.anim.activity_down_in,R.anim.activity_down_out);
                    finish();
                }
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
                    if (stringBuilder.length() == 0) {
                        return;
                    }
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
            entryView.setText(String.format(Locale.CANADA, "%s %s",
                    currCurrency.getCurrency().getSymbol(),
                    stringBuilder.length() > 0 ? stringBuilder.toString() : "0"));
        });

        initializeHistoryButtons();
    }

    private void initializeHistoryButtons() {
        enterHistoryButton = findViewById(R.id.enter_history_button);
        rightHistoryButton = findViewById(R.id.right_history_button);
        leftHistoryButton = findViewById(R.id.left_history_button);

        enterHistoryButton.setOnClickListener((e) -> {
            service.enterHistorySlideshow();
            refreshCountingTable();
        });

        rightHistoryButton.setOnClickListener((e) -> {
            service.gotoNextHistorySlide();
            refreshCountingTable();
        });

        leftHistoryButton.setOnClickListener((e) -> {
            service.gotoPreviousHistorySlide();
            refreshCountingTable();
        });

        refreshHistoryButtons();
    }

    private void refreshHistoryButtons() {
        if (enterHistoryButton != null && rightHistoryButton != null && leftHistoryButton != null) {
            if (service.isInHistorySlideshow()) {
                enterHistoryButton.setVisibility(View.INVISIBLE);
                leftHistoryButton.setVisibility(View.VISIBLE);
                rightHistoryButton.setVisibility(View.VISIBLE);
            } else {
                if (service.getAppState().getOperations().size() == 1)
                    enterHistoryButton.setVisibility(View.INVISIBLE);
                else enterHistoryButton.setVisibility(View.VISIBLE);

                rightHistoryButton.setVisibility(View.INVISIBLE);
                leftHistoryButton.setVisibility(View.INVISIBLE);
            }
        }
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

        refreshHistoryButtons();

        if (service.getOperationMode() == MathOperationModel.MathOperationMode.STANDARD && service.getValue().equals(BigDecimal.ZERO))
            clearButton.setVisibility(View.INVISIBLE);
        else
            clearButton.setVisibility(View.VISIBLE);

        if (service.getValue().compareTo(BigDecimal.ZERO) < 0)
            sumView.setTextColor(getResources().getColor(R.color.negativeSum));
        else
            sumView.setTextColor(getResources().getColor(R.color.colorPrimaryDark));

        sumView.setText(String.format(Locale.CANADA, "%s %s",
                currCurrency.getCurrency().getSymbol(), service.getValue()));

        countingTableView.setDenominations(currCurrency.getDenominations().iterator(),
                countingService.allocate(service.getValue(), currCurrency), service.getValue());
    }

    private void switchState() {
        Intent intent = new Intent(MainActivity.this, MainActivity.class);
        intent.putExtra(APP_STATE_KEY, service.getAppState());
        startActivity(intent);
    }
}
