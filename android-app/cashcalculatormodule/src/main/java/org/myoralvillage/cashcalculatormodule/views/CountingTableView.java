package org.myoralvillage.cashcalculatormodule.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import org.myoralvillage.cashcalculatormodule.R;
import org.myoralvillage.cashcalculatormodule.models.AppStateModel;
import org.myoralvillage.cashcalculatormodule.models.CurrencyModel;
import org.myoralvillage.cashcalculatormodule.services.CountingService;
import org.myoralvillage.cashcalculatormodule.views.listeners.CountingTableListener;
import org.myoralvillage.cashcalculatormodule.views.listeners.SwipeListener;

public class CountingTableView extends RelativeLayout {
    private CountingTableSurfaceView countingTableSurfaceView;
    private AppStateModel appState;
    private CountingTableListener listener = null;
    private CountingService countingService = new CountingService();
    private CurrencyModel currencyModel;

    private ImageView calculateButton;

    public CountingTableView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(getContext(), R.layout.counting_table, this);
    }

    public void initialize(CurrencyModel currencyModel, AppStateModel appState) {
        this.currencyModel = currencyModel;
        this.appState = appState;

        initializeSurface();
        initializeCalculateButton();
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

    private void updateCountingSurface() {
        countingTableSurfaceView.setDenominations(currencyModel.getDenominations().iterator(),
                countingService.allocate(appState.getCurrentOperation().getValue(), currencyModel),
                appState.getCurrentOperation().getValue());
    }

    private void updateAll() {
        updateCountingSurface();
        updateCalculateButton();
    }

    public void setListener(CountingTableListener listener) {
        this.listener = listener;
    }

    public void setAppState(AppStateModel appState) {
        this.appState = appState;
        updateAll();
    }
}
