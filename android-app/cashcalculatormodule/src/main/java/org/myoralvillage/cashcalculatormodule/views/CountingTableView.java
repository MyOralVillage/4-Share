package org.myoralvillage.cashcalculatormodule.views;

import android.content.Context;
import android.graphics.Color;
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
import java.text.NumberFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 *  A view that displays images of currency to represent a number.
 *
 * @author Alexander Yang
 * @author Hamza Mahfooz
 * @author Peter Panagiotis Roubatsis
 * @see RelativeLayout
 */
public class CountingTableView extends RelativeLayout {
    /**
     * The view that displays the images of currency.
     *
     * @see CountingTableSurfaceView
     */
    private CountingTableSurfaceView countingTableSurfaceView;

    /**
     * The application state being rendered by this view
     *
     * @see AppStateModel
     */
    private AppStateModel appState;

    /**
     * A listener for handling user events that occur in this view
     *
     * @see CountingTableListener
     */
    private CountingTableListener listener = null;

    /**
     * Assists in partitioning the total value of this view to their appropriate denominations.
     *
     * @see CountingService
     */
    private CountingService countingService = new CountingService();

    /**
     * Stores the type of currency as well as the set of denominations.
     *
     * @see CurrencyModel
     */
    private CurrencyModel currencyModel;

    /**
     * Displays the total value in the top right corner of the view.
     *
     * @see TextView
     */
    private TextView sumView;

    /**
     * Displays an image that can be tapped, thereby executing the mathematical operation symbolised
     * by this image.
     *
     * @see ImageView
     */
    private ImageView calculateButton;

    /**
     * Displays an image that can be tapped, thereby removing the denominations on this view.
     *
     * @see ImageView
     */
    private ImageView clearButton;

    /**
     * Displays an image that can be tapped, thereby entering the history mode of this application.
     *
     * @see ImageView
     */
    private ImageView enterHistoryButton;

    /**
     * Displays an image that can be tapped, thereby going to the next history slide of this
     * application.
     *
     * @see ImageView
     */
    private ImageView rightHistoryButton;

    /**
     * Displays an image that can be tapped, thereby going to the previous history slide of this
     * application.
     *
     * @see ImageView
     */
    private ImageView leftHistoryButton;
    private Locale locale;

    /**
     * Constructs a <code>CountingTableSurfaceView</code> in the given Android context with the
     * given attributes.
     *
     * @param context the context of the application.
     * @param attrs A collection of attributes found in the xml layout.
     */
    public CountingTableView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(getContext(), R.layout.counting_table, this);
    }

    /**
     * Initializes this view with its currency and initial state.
     *
     * @param currencyModel the currencyModel of this view.
     * @param appState the appState of this view.
     *
     * @see CurrencyModel
     * @see AppStateModel
     */
    public void initialize(CurrencyModel currencyModel, AppStateModel appState, Locale locale) {
        this.currencyModel = currencyModel;
        this.appState = appState;
        this.locale = locale;

        initializeSumView();
        initializeSurface();
        initializeCalculateButton();
        initializeClearButton();
        initializeHistoryButtons();
    }

    private void initializeSumView() {
        sumView = findViewById(R.id.sum_view);
        sumView.setTextColor(Color.BLACK);
    }

    private void updateSumView() {
        sumView.setText(String.format(locale,"%s",
                getAdaptedNumberFormat()
                        .format(appState.getCurrentOperation().getValue())
        ));
    }

    private NumberFormat getAdaptedNumberFormat() {
        DecimalFormat df = (DecimalFormat) NumberFormat.getCurrencyInstance(locale);
        DecimalFormat dfUS = (DecimalFormat) NumberFormat.getCurrencyInstance(new Locale("ENGLISH", "US"));
        DecimalFormatSymbols dfs = df.getDecimalFormatSymbols();
        DecimalFormatSymbols dfsUS = dfUS.getDecimalFormatSymbols();
        dfsUS.setInternationalCurrencySymbol(dfs.getInternationalCurrencySymbol());
        dfsUS.setCurrency(dfs.getCurrency());
        dfsUS.setCurrencySymbol(dfs.getCurrencySymbol());
        df.setDecimalFormatSymbols(dfsUS);
        switch(df.getPositivePrefix()) {
            case "Rs":
                df.setPositivePrefix("Rs. ");
        }
        switch(df.getNegativePrefix()) {
            case "-Rs":
                df.setNegativePrefix("Rs. -");
        }
        switch(df.getPositiveSuffix()) {
            case "৳":
                df.setPositiveSuffix(" ৳");
        }
        switch(df.getNegativeSuffix()) {
            case "৳":
                df.setNegativeSuffix(" ৳");
        }
        return df;
    }

    private void initializeSurface() {
        countingTableSurfaceView = findViewById(R.id.counting_table_surface);
        if (appState.getAppMode() == AppStateModel.AppMode.IMAGE) {
            countingTableSurfaceView.initDenominationModels(currencyModel.getDenominations());
        }
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

    /**
     * Sets the listener to allow handling of the view's events.
     *
     * @param listener the listener of this view
     * @see CountingTableListener
     */
    public void setListener(CountingTableListener listener) {
        this.listener = listener;
    }

    public CountingTableListener getListener() {
        return listener;
    }

    public CountingTableSurfaceView getCountingTableSurfaceView() {
        return countingTableSurfaceView;
    }

    /**
     * Sets the appState of this view and render the updated state.
     *
     * @param appState the appState of the application.
     * @see AppStateModel
     */
    public void setAppState(AppStateModel appState) {
        this.appState = appState;
        updateAll();
    }

    /**
     * Set the background to a given resource. The resource should refer to a Drawable object or 0
     * to remove the background.
     *
     * @param resid the identifier of the resource.
     */
    @Override
    public void setBackgroundResource(int resid) {
        countingTableSurfaceView.setBackgroundResource(resid);
    }
}
