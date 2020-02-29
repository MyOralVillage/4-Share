package org.myoralvillage.cashcalculatormodule.views;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import org.myoralvillage.cashcalculatormodule.R;
import org.myoralvillage.cashcalculatormodule.models.CurrencyModel;
import org.myoralvillage.cashcalculatormodule.models.DenominationModel;
import org.myoralvillage.cashcalculatormodule.views.listeners.CurrencyTapListener;

public class CurrencyScrollbarView extends HorizontalScrollView {
    private final int PADDING_VERTICAL_PX = dpToPixels(4);
    private final int PADDING_HORIZONTAL_PX = dpToPixels(8);

    private LinearLayout linearLayout;
    private CurrencyTapListener currencyTapListener;
    private CurrencyModel currCurrency;

    public CurrencyModel getCurrency() {
        return this.currCurrency;
    }

    public CurrencyScrollbarView(Context context) {
        super(context);
        initialize();
    }

    public CurrencyScrollbarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    private void initialize() {
        currencyTapListener = null;
        setBackgroundResource(R.drawable.scrollbar_background);

        linearLayout = new LinearLayout(getContext());
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT
        ));

        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        addView(linearLayout);
    }

    public void setCurrencyTapListener(CurrencyTapListener currencyTapListener) {
        this.currencyTapListener = currencyTapListener;
    }

    public void setCurrency(String currencyCode) {
        linearLayout.removeAllViews();

        CurrencyModel currency = CurrencyModel.loadCurrencyModel(
                currencyCode, getResources(), getContext());

        this.currCurrency = currency;
        for (DenominationModel denomination : currency.getDenominations())
            addDenomination(denomination);
    }

    private void addDenomination(final DenominationModel denominationModel) {
        ImageView imageView = new ImageView(getContext());
        imageView.setImageResource(denominationModel.getImageResource());
        imageView.setAdjustViewBounds(true);

        imageView.setPadding(PADDING_HORIZONTAL_PX, PADDING_VERTICAL_PX,
                PADDING_HORIZONTAL_PX, PADDING_VERTICAL_PX);
        imageView.setOnClickListener(v -> {
            if (currencyTapListener != null)
                currencyTapListener.onTapDenomination(denominationModel);
        });

        linearLayout.addView(imageView);
    }

    private static int dpToPixels(int dp) {
        return (int)(dp * Resources.getSystem().getDisplayMetrics().density);
    }
}
