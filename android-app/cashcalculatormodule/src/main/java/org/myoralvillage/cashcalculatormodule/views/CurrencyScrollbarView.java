package org.myoralvillage.cashcalculatormodule.views;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.core.content.ContextCompat;

import org.myoralvillage.cashcalculatormodule.R;
import org.myoralvillage.cashcalculatormodule.models.CurrencyModel;
import org.myoralvillage.cashcalculatormodule.models.DenominationModel;
import org.myoralvillage.cashcalculatormodule.views.listeners.CurrencyTapListener;

import java.math.BigDecimal;
import java.util.Optional;

public class CurrencyScrollbarView extends HorizontalScrollView {
    private final int PADDING_VERTICAL_PX = dpToPixels(4);
    private final int PADDING_HORIZONTAL_PX = dpToPixels(8);

    private LinearLayout linearLayout;
    private Optional<CurrencyTapListener> currencyTapListener = Optional.empty();

    public CurrencyScrollbarView(Context context) {
        super(context);
        initialize();
    }

    public CurrencyScrollbarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    private void initialize() {
        setBackgroundColor(ContextCompat.getColor(getContext(), R.color.scrollbar_background));

        linearLayout = new LinearLayout(getContext());
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT
        ));

        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        addView(linearLayout);
    }

    public void setCurrencyTapListener(CurrencyTapListener currencyTapListener) {
        this.currencyTapListener = Optional.of(currencyTapListener);
    }

    public void setCurrency(String currencyCode) {
        linearLayout.removeAllViews();

        CurrencyModel currency =loadCurrencyModel(currencyCode);
        for (DenominationModel denomination : currency.getDenominations())
            addDenomination(denomination);
    }

    private CurrencyModel loadCurrencyModel(String currencyCode) {
        CurrencyModel model = new CurrencyModel(currencyCode);

        // Get an array from the android resources with name "currency_{currencyCode}"
        String arrayName = String.format("currency_%s", currencyCode);
        int arrayId = getResources().getIdentifier(arrayName, "array",
                getContext().getPackageName());

        if (arrayId != 0) {
            TypedArray array = getResources().obtainTypedArray(arrayId);
            try {
                for (int i = 0; i < array.length(); i += 2) {
                    // Build CurrencyModel instance from the values in the xml
                    String value = array.getString(i);
                    int imageResourceId = array.getResourceId(i + 1, 0);

                    if (value != null && imageResourceId != 0)
                        model.addDenomination(new BigDecimal(value), imageResourceId);
                }
            } finally {
                // Required to call as part of the TypedArray lifecycle
                array.recycle();
            }
        }

        return model;
    }

    private void addDenomination(final DenominationModel denominationModel) {
        ImageView imageView = new ImageView(getContext());
        imageView.setImageResource(denominationModel.getImageResource());
        imageView.setAdjustViewBounds(true);

        imageView.setPadding(PADDING_HORIZONTAL_PX, PADDING_VERTICAL_PX,
                PADDING_HORIZONTAL_PX, PADDING_VERTICAL_PX);
        imageView.setOnClickListener(v ->
                currencyTapListener.ifPresent(listener ->
                        listener.onTapDenomination(denominationModel)));

        linearLayout.addView(imageView);
    }

    private static int dpToPixels(int dp) {
        return (int)(dp * Resources.getSystem().getDisplayMetrics().density);
    }
}
