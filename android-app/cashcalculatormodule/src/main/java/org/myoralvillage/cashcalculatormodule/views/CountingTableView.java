package org.myoralvillage.cashcalculatormodule.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.myoralvillage.cashcalculatormodule.R;
import org.myoralvillage.cashcalculatormodule.models.AppStateModel;
import org.myoralvillage.cashcalculatormodule.models.CurrencyModel;
import org.myoralvillage.cashcalculatormodule.models.MathOperationModel;
import org.myoralvillage.cashcalculatormodule.services.CountingService;
import org.myoralvillage.cashcalculatormodule.views.listeners.CountingTableListener;
import org.myoralvillage.cashcalculatormodule.views.listeners.SwipeListener;

import java.math.BigDecimal;
import java.util.Locale;

public class CountingTableView extends RelativeLayout {
    private CountingTableSurfaceView countingTableSurfaceView;
    private AppStateModel appState;
    private CountingTableListener listener = null;
    private CountingService countingService = new CountingService();
    private CurrencyModel currencyModel;

    private TextView sumView;

    private ImageView calculateButton;
    private ImageView clearButton;

    private ImageView enterHistoryButton;
    private ImageView rightHistoryButton;
    private ImageView leftHistoryButton;

    public CountingTableView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(getContext(), R.layout.counting_table, this);
    }

    public void initialize(CurrencyModel currencyModel, AppStateModel appState) {
        this.currencyModel = currencyModel;
        this.appState = appState;

        initializeSumView();
        initializeSurface();
        initializeCalculateButton();
        initializeClearButton();
        initializeHistoryButtons();
    }

    private void initializeSumView() {
        sumView = findViewById(R.id.sum_view);
    }

    private void updateSumView() {
        if (appState.getCurrentOperation().getValue().compareTo(BigDecimal.ZERO) < 0)
            sumView.setTextColor(getResources().getColor(R.color.negativeSum));
        else
            sumView.setTextColor(getResources().getColor(R.color.positiveSum));

        sumView.setText(String.format(Locale.CANADA, "%s %s",
                currencyModel.getCurrency().getSymbol(), appState.getCurrentOperation().getValue()));
    }

    private void initializeSurface() {
        countingTableSurfaceView = findViewById(R.id.counting_table_surface);
        countingTableSurfaceView.initDenominationModels(currencyModel.getDenominations());

        countingTableSurfaceView.setOnTouchListener(new SwipeListener(getContext()) {
            @Override
            public void swipeLeft() {
                // Dragging towards the right
                if (listener != null)
                    listener.onSwipeAddition();
            }

            @Override
            public void swipeRight() {
                // Dragging towards the left
                if (listener != null)
                    listener.onSwipeSubtraction();
            }

            @Override
            public void longPress(float x, float y) {
                countingTableSurfaceView.handleLongPress(x, y);
            }

            @Override
            public void swipeUp() {
                // Dragging towards the bottom
            }

            @Override
            public void swipeDown() {
                // Dragging towards the top
                if (listener != null)
                    listener.onSwipeMultiplication();
            }
        });

        countingTableSurfaceView.setCountingTableSurfaceListener((model, oldCount, newCount) -> {
            if (listener != null)
                listener.onDenominationChange(model, oldCount, newCount);
        });
    }

    private void initializeCalculateButton(){
        calculateButton = findViewById(R.id.calculate_button);
        calculateButton.setOnClickListener((e) -> {
            if (listener != null && !appState.isInHistorySlideshow())
                listener.onTapCalculateButton();
        });
    }

    private void updateCalculateButton() {
        calculateButton.setVisibility(View.VISIBLE);
        switch (appState.getCurrentOperation().getMode()) {
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
    }

    private void initializeClearButton(){
        clearButton = findViewById(R.id.clear_button);
        clearButton.setOnClickListener((e) -> {
            if (listener != null)
                listener.onTapClearButton();
        });
    }

    private void updateClearButton() {
        if ((appState.getCurrentOperation().getMode() == MathOperationModel.MathOperationMode.STANDARD
                && appState.getCurrentOperation().getValue().equals(BigDecimal.ZERO)) ||
                appState.isInHistorySlideshow())
            clearButton.setVisibility(View.INVISIBLE);
        else
            clearButton.setVisibility(View.VISIBLE);
    }

    private void updateCountingSurface() {
        countingTableSurfaceView.setDenominations(currencyModel.getDenominations().iterator(),
                countingService.allocate(appState.getCurrentOperation().getValue(), currencyModel),
                appState.getCurrentOperation().getValue());
    }

    private void initializeHistoryButtons(){
        enterHistoryButton = findViewById(R.id.enter_history_button);
        rightHistoryButton = findViewById(R.id.right_history_button);
        leftHistoryButton = findViewById(R.id.left_history_button);

        enterHistoryButton.setOnClickListener((e) -> {
            if (listener != null)
                listener.onTapEnterHistory();
        });

        rightHistoryButton.setOnClickListener((e) -> {
            if (listener != null)
                listener.onTapNextHistory();
        });

        leftHistoryButton.setOnClickListener((e) -> {
            if (listener != null)
                listener.onTapPreviousHistory();
        });
    }


    private void updateHistoryButtons() {
        if (appState.isInHistorySlideshow()) {
            enterHistoryButton.setVisibility(View.INVISIBLE);
            leftHistoryButton.setVisibility(View.VISIBLE);
            rightHistoryButton.setVisibility(View.VISIBLE);
        } else {
            if (appState.getOperations().size() == 1)
                enterHistoryButton.setVisibility(View.INVISIBLE);
            else enterHistoryButton.setVisibility(View.VISIBLE);

            rightHistoryButton.setVisibility(View.INVISIBLE);
            leftHistoryButton.setVisibility(View.INVISIBLE);
        }
    }

    private void updateAll() {
        updateCountingSurface();
        updateCalculateButton();
        updateClearButton();
        updateHistoryButtons();
        updateSumView();
    }

    public void setListener(CountingTableListener listener) {
        this.listener = listener;
    }

    public void setAppState(AppStateModel appState) {
        this.appState = appState;
        updateAll();
    }

    @Override
    public void setBackgroundResource(int resid) {
        countingTableSurfaceView.setBackgroundResource(resid);
    }
}
