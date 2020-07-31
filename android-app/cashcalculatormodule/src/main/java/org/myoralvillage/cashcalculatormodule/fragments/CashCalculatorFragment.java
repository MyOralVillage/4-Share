package org.myoralvillage.cashcalculatormodule.fragments;


import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import org.myoralvillage.cashcalculatormodule.R;
import org.myoralvillage.cashcalculatormodule.models.AppStateModel;
import org.myoralvillage.cashcalculatormodule.models.CurrencyModel;
import org.myoralvillage.cashcalculatormodule.models.DenominationModel;
import org.myoralvillage.cashcalculatormodule.services.AppService;
import org.myoralvillage.cashcalculatormodule.views.CountingTableView;
import org.myoralvillage.cashcalculatormodule.views.CurrencyScrollbarView;
import org.myoralvillage.cashcalculatormodule.views.NumberPadView;
import org.myoralvillage.cashcalculatormodule.views.listeners.CountingTableListener;
import org.myoralvillage.cashcalculatormodule.views.listeners.CurrencyScrollbarListener;
import org.myoralvillage.cashcalculatormodule.views.listeners.NumberPadListener;

import androidx.annotation.NonNull;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

/**
 * CashCalculatorFragment creates the whole Cash Calculator application, including the Counting Table
 * and the Currency Scrollbar, packaged as one.
 *
 * @author Alexander Yang
 * @author Hamza Mahfooz
 * @author Jiaheng Li
 * @author Lingjing Zou
 * @author Peter Panagiotis Roubatsis
 * @author Yujie Wu
 * @author Zhipeng Zou
*/
public class CashCalculatorFragment extends Fragment {
    /**
     * A constant variable to store and lookup the state of the app when a new activity is started.
     * It stores the app state in the Activity's bundle so that it can be accessed by the next
     * activity.
     *
     * @see Bundle
     */
    private final String APP_STATE_KEY = "appState";

    /**
     * The view of the Cash Calculator.
     */
    private View view;

    /**
     * The service class used to perform the main operations of the Cash Calculator.
     *
     * @see AppService
     */
    private AppService service;

    /**
     * The model class used to represent the type of currency as well as the set of denominations
     * available.
     *
     * @see CurrencyModel
     */
    private CurrencyModel currCurrency;

    /**
     * The view that displays images of currency to represent a number.
     *
     * @see CountingTableView
     */
    private CountingTableView countingTableView;

    /**
     * The view of the Scrollbar to monitor and render the display of the denominations in the
     * scrollbar as well as the scrollbar itself.
     *
     * @see CurrencyScrollbarView
     */
    private CurrencyScrollbarView currencyScrollbarView;

    /**
     * The view class used to monitor and render the display of the number pad.
     *
     * @see NumberPadView
     */
    private NumberPadView numberPadView;

    /**
     * Displays the number when in numeric mode.
     *
     */
    private TextView numberInputView;
    private Locale locale;

    /**
     * Called to have the <code>CashCalculatorFragment</code> instantiate its user interface view.
     *
     * @param inflater _
     * @param parent _
     * @param savedInstanceState _
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        Bundle extras = getActivity().getIntent().getExtras();
        if (extras != null && extras.containsKey(APP_STATE_KEY))
            service = new AppService((AppStateModel) extras.getSerializable(APP_STATE_KEY));
        else service = new AppService();

        view = inflater.inflate(R.layout.fragment_activity, parent, false);
        numberInputView = view.findViewById(R.id.number_input_view);
        numberInputView.setVisibility(View.INVISIBLE);

        return view;
    }


    /**
     * Gets the value of current total amount of money of the Cash Calculator.
     *
     * @return the total amount of money.
     */
    public BigDecimal getValue(){
        return service.getValue();
    }

    /**
     * Initializes the <code>CashCalculatorFragment</code> based on the currency code.
     *
     * @param currencyCode The currency code that the application is set to.
     */
    public void initialize(String currencyCode) {
        this.locale = Locale.getDefault();
        for (Locale l : Locale.getAvailableLocales()) {
            try {
                Currency currency = Currency.getInstance(l);
                if (currency == null) {
                    continue;
                }

                if (currency.getCurrencyCode().equals(currencyCode)) {
                    locale = l;
                    break;
                }
            } catch (IllegalArgumentException ignored) {}
        }

        initializeCurrencyScrollbar(currencyCode);
        initializeCountingView();
        initializeNumberPad();

        updateAll();
    }

    /**
     * Initializes the <code>CountingTableView</code>.
     *
     * @see CountingTableView
     */
    private void initializeCountingView() {
        TextView sum = view.findViewById(R.id.sum_view);
        countingTableView = view.findViewById(R.id.counting_table);
        countingTableView.initialize(currCurrency, service.getAppState(), locale);
        if (service.getAppState().getAppMode() == AppStateModel.AppMode.NUMERIC) {
            sum.setVisibility(View.INVISIBLE);
            numberInputView.setVisibility(View.VISIBLE);
            numberInputView.setText(formatCurrency(service.getValue()));
        }
        countingTableView.setListener(new CountingTableListener() {
            @Override
            public void onSwipeAddition() {
                if (!service.isInHistorySlideshow()) {
                    service.add();
                    switchState();
                    getActivity().overridePendingTransition(R.anim.activity_left_in,R.anim.activity_left_out);
                    getActivity().finish();
                }
            }

            @Override
            public void onSwipeSubtraction() {
                if (!service.isInHistorySlideshow()) {
                    service.subtract();
                    switchState();
                    getActivity().overridePendingTransition(R.anim.activity_right_in,R.anim.activity_right_out);
                    getActivity().finish();
                }
            }

            @Override
            public void onSwipeMultiplication() {
                // Dragging towards the top
                if (!service.isInHistorySlideshow()) {
                    service.multiply();
                    switchState();
                    getActivity().overridePendingTransition(R.anim.activity_down_in,R.anim.activity_down_out);
                    getActivity().finish();
                }
            }

            @Override
            public void onDenominationChange(DenominationModel denomination, int oldCount, int newCount) {
                BigDecimal amount = denomination.getValue().multiply(new BigDecimal(oldCount - newCount));
                if (service.getValue().compareTo(BigDecimal.ZERO) >= 0) {
                    service.setValue(service.getValue().subtract(amount));
                } else {
                    service.setValue(service.getValue().add(amount));
                }
                updateAll();
            }

            @Override
            public void onTapCalculateButton() {
                service.calculate();
                switch (service.getAppState().getAppMode()) {
                    case NUMERIC:
                        if (numberInputView.getVisibility() == View.INVISIBLE) {
                            BigDecimal actual = service.getValue();
                            service.setValue(BigDecimal.ZERO);
                            countingTableView.initialize(currCurrency, service.getAppState(), locale);
                            sum.setVisibility(View.INVISIBLE);
                            numberInputView.setVisibility(View.VISIBLE);
                            numberInputView.setText(formatCurrency(actual));
                            numberPadView.setValue(actual);
                        }
                        else {
                            numberInputView.setText(formatCurrency(service.getValue()));
                            numberPadView.setValue(service.getValue());
                        }
                        break;
                }
                updateAll();
            }

            @Override
            public void onTapClearButton() {
                switch (service.getAppState().getAppMode()) {
                    case NUMERIC:
                        sum.setVisibility(View.INVISIBLE);
                        numberInputView.setVisibility(View.VISIBLE);
                        numberInputView.setText(formatCurrency(BigDecimal.ZERO));
                        numberPadView.setValue(BigDecimal.ZERO);
                        break;
                }
                service.reset();
                updateAll();
            }

            @Override
            public void onTapEnterHistory() {
                numberInputView.setVisibility(View.INVISIBLE);
                service.enterHistorySlideshow();
                updateAll();
                sum.setVisibility(View.VISIBLE);
            }

            @Override
            public void onTapNextHistory() {
                numberInputView.setVisibility(View.INVISIBLE);
                service.gotoNextHistorySlide();
                updateAll();
                sum.setVisibility(View.VISIBLE);
            }

            @Override
            public void onTapPreviousHistory() {
                numberInputView.setVisibility(View.INVISIBLE);
                service.gotoPreviousHistorySlide();
                updateAll();
                sum.setVisibility(View.VISIBLE);
            }
        });
    }

    /**
     * Called when the value displayed is changed.
     */
    private void updateCountingTable() {
        countingTableView.setAppState(service.getAppState());
    }

    /**
     * Initializes the <code>CurrencyScrollBarView</code> to the given currency code.
     *
     * @param currencyCode The currency code that the denominations being displayed is set to.
     *
     * @see CurrencyScrollbarView
     */
    private void initializeCurrencyScrollbar(String currencyCode){
        currencyScrollbarView = view.findViewById(R.id.currency_scrollbar);
        currencyScrollbarView.setCurrency(currencyCode);
        this.currCurrency = currencyScrollbarView.getCurrency();

        currencyScrollbarView.setCurrencyScrollbarListener(new CurrencyScrollbarListener() {
            @Override
            public void onTapDenomination(DenominationModel denomination) {
                service.setValue(service.getValue().add(denomination.getValue()));
                updateAll();
            }

            @Override
            public void onVerticalSwipe() {
                //switchAppMode();
            }


        });
    }

    /**
     * Switches the Cash Calculator between numeric mode and image mode.
     *
     * @see AppService
     */
    public void switchAppMode() {
        service.switchAppMode();
        if (service.getAppState().getAppMode() == AppStateModel.AppMode.NUMERIC)
            service.setValue(BigDecimal.ZERO);

        TextView sum = getView().findViewById(R.id.sum_view);

        switch(service.getAppState().getAppMode()) {
            case IMAGE:
                sum.setVisibility(View.VISIBLE);
                numberInputView.setVisibility(View.INVISIBLE);
                break;
            case NUMERIC:
                sum.setVisibility(View.INVISIBLE);
                numberInputView.setVisibility(View.VISIBLE);
                numberInputView.setText(formatCurrency(service.getValue()));
                service.setValue(BigDecimal.ZERO);
                break;
        }

        updateAll();
    }

    /**
     * Initializes the <code>NumberPadView</code> when the application is in numeric mode.
     *
     * @see NumberPadView
     */
    private void initializeNumberPad() {
        TextView sum = view.findViewById(R.id.sum_view);
        numberPadView = view.findViewById(R.id.number_pad_view);

        numberPadView.setListener(new NumberPadListener() {
            @Override
            public void onCheck(BigDecimal value) {
                if (numberInputView.getVisibility() == View.INVISIBLE) {
                    //do nothing
                }
                else {
                    sum.setVisibility(View.VISIBLE);
                    service.setValue(value);
                    numberInputView.setVisibility(View.INVISIBLE);
                    service.getAppState().setAppMode(AppStateModel.AppMode.IMAGE);
                    countingTableView.initialize(currCurrency, service.getAppState(), locale);
                    service.getAppState().setAppMode(AppStateModel.AppMode.NUMERIC);
                    updateAll();
                }
            }

            @Override
            public void onBack(BigDecimal value) {
                numberInputView.setText(formatCurrency(value));
                service.setValue(value);
            }

            @Override
            public void onTapNumber(BigDecimal value) {
                if (numberInputView.getVisibility() == View.INVISIBLE) {
                    service.setValue(BigDecimal.ZERO);
                    countingTableView.initialize(currCurrency, service.getAppState(), locale);
                    updateAll();
                }
                service.setValue(value);
                sum.setVisibility(View.INVISIBLE);
                numberInputView.setVisibility(View.VISIBLE);
                numberInputView.setText(formatCurrency(value));
            }

            @Override
            public void onVerticalSwipe() {
                //switchAppMode();
            }
        });
    }

    private String formatCurrency(BigDecimal value) {
        return String.format(locale,"%s",
                getAdaptedNumberFormat()
                        .format(value)
        );
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



    /**
     * Called when the application is updated.
     */
    private void updateAppMode() {
        switch(service.getAppState().getAppMode()) {
            case IMAGE:
                numberPadView.setVisibility(View.INVISIBLE);
                currencyScrollbarView.setVisibility(View.VISIBLE);
                break;
            case NUMERIC:
                numberPadView.setVisibility(View.VISIBLE);
                currencyScrollbarView.setVisibility(View.INVISIBLE);
                break;
        }
    }

    /**
     * Called whenever the application switches its mathematical operation mode.
     */
    private void switchState() {
        Intent intent = new Intent(getActivity(), getActivity().getClass());
        intent.putExtra(APP_STATE_KEY, service.getAppState());
        startActivity(intent);
    }

    /**
     * Called whenever a gesture is performed on the Cash Calculator and upon initialization.
     */
    private void updateAll() {
        updateCountingTable();
        updateAppMode();
    }
}
