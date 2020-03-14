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

/**
 *  A view class used to monitor and render the display of the overall layout of the table in the
 *  application.
 *
 * @author Alexander Yang
 * @author Hamza Mahfooz
 * @author Peter Panagiotis Roubatsis
 * @see RelativeLayout
 */
public class CountingTableView extends RelativeLayout {
    /**
     * The view that monitors and renders the display of the denominations on this view.
     *
     * @see CountingTableSurfaceView
     */
    private CountingTableSurfaceView countingTableSurfaceView;

    /**
     * The mode that this view is displaying.
     *
     * @see AppStateModel
     */
    private AppStateModel appState;

    /**
     * A listener for receiving gesture detection on this view.
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
     * Displays the total value.
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

    /**
     * Constructs a <code>CountingTableSurfaceView</code> in the given context and attributes.
     *
     * @param context the context of the application.
     * @param attrs A collection of attributes found in the xml layout.
     */
    public CountingTableView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(getContext(), R.layout.counting_table, this);
    }

    /**
     * Initializes this view and its variables.
     *
     * @param currencyModel the currencyModel of this view.
     * @param appState the appState of this view.
     *
     * @see CurrencyModel
     * @see AppStateModel
     */
    public void initialize(CurrencyModel currencyModel, AppStateModel appState) {
        this.currencyModel = currencyModel;
        this.appState = appState;

        initializeSumView();
        initializeSurface();
        initializeCalculateButton();
        initializeClearButton();
        initializeHistoryButtons();
    }

    /**
     * Initializes the display of the total value of this view.
     */
    private void initializeSumView() {
        sumView = findViewById(R.id.sum_view);
    }

    /**
     * Updates the display of the total value of this view to reflect the denominations on screen.
     */
    private void updateSumView() {
        if (appState.getCurrentOperation().getValue().compareTo(BigDecimal.ZERO) < 0)
            sumView.setTextColor(getResources().getColor(R.color.negativeSum));
        else
            sumView.setTextColor(getResources().getColor(R.color.positiveSum));

        sumView.setText(String.format(Locale.CANADA, "%s %s",
                currencyModel.getCurrency().getSymbol(), appState.getCurrentOperation().getValue()));
    }

    /**
     * Initializes the display of this view, as well as the listener.
     */
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

    /**
     * Initializes the display of the mathematical operation to signify the mode of this application
     * as well as monitor any gestures performed on this button.
     */
    private void initializeCalculateButton(){
        calculateButton = findViewById(R.id.calculate_button);
        calculateButton.setOnClickListener((e) -> {
            if (listener != null && !appState.isInHistorySlideshow())
                listener.onTapCalculateButton();
        });
    }

    /**
     * Updates the display of the calculate button.
     */
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

    /**
     * Initializes the display of the clear button as well as monitor any gestures performed on this
     * button.
     */
    private void initializeClearButton(){
        clearButton = findViewById(R.id.clear_button);
        clearButton.setOnClickListener((e) -> {
            if (listener != null)
                listener.onTapClearButton();
        });
    }

    /**
     * Updates the clear button.
     */
    private void updateClearButton() {
        if ((appState.getCurrentOperation().getMode() == MathOperationModel.MathOperationMode.STANDARD
                && appState.getCurrentOperation().getValue().equals(BigDecimal.ZERO)) ||
                appState.isInHistorySlideshow())
            clearButton.setVisibility(View.INVISIBLE);
        else
            clearButton.setVisibility(View.VISIBLE);
    }

    /**
     * Updates the denominations on this view.
     */
    private void updateCountingSurface() {
        countingTableSurfaceView.setDenominations(currencyModel.getDenominations().iterator(),
                countingService.allocate(appState.getCurrentOperation().getValue(), currencyModel),
                appState.getCurrentOperation().getValue());
    }

    /**
     * Initializes the display of the history buttons as well as monitor any gestures performed on
     * this button.
     */
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

    /**
     * Updates the history buttons of this view.
     */
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

    /**
     * Updates the <code>CountingTableView</code> upon any changes to the total value or application
     * state.
     */
    private void updateAll() {
        updateCountingSurface();
        updateCalculateButton();
        updateClearButton();
        updateHistoryButtons();
        updateSumView();
    }

    /**
     * Sets the listener of this view.
     *
     * @param listener the listener of this view
     * @see CountingTableListener
     */
    public void setListener(CountingTableListener listener) {
        this.listener = listener;
    }

    /**
     * Sets the appState of this view.
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
