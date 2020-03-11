package org.myoralvillage.cashcalculatormodule.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import org.myoralvillage.cashcalculatormodule.R;
import org.myoralvillage.cashcalculatormodule.models.CurrencyModel;
import org.myoralvillage.cashcalculatormodule.services.CountingService;
import org.myoralvillage.cashcalculatormodule.views.listeners.CountingTableListener;
import org.myoralvillage.cashcalculatormodule.views.listeners.SwipeListener;

import java.math.BigDecimal;

public class CountingTableView extends RelativeLayout {
    private CountingTableSurfaceView countingTableSurfaceView;
    private CountingTableListener listener = null;
    private CountingService countingService = new CountingService();
    private CurrencyModel currencyModel;

    public CountingTableView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(getContext(), R.layout.counting_table, this);
    }

    public void initialize(CurrencyModel currencyModel) {
        this.currencyModel = currencyModel;
        initializeSurface(currencyModel);
    }

    private void initializeSurface(CurrencyModel currencyModel) {
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

    public void setListener(CountingTableListener listener) {
        this.listener = listener;
    }

    public void setValue(BigDecimal value) {
        countingTableSurfaceView.setDenominations(currencyModel.getDenominations().iterator(),
                countingService.allocate(value, currencyModel), value);
    }
}
