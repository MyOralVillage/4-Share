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

public class CurrencyScrollbarView extends HorizontalScrollView {
    private final int PADDING_VERTICAL_PX = dpToPixels(4);
    private final int PADDING_HORIZONTAL_PX = dpToPixels(8);

    private LinearLayout linearLayout;

    public CurrencyScrollbarView(Context context) {
        super(context);
        initialize();
    }

    public CurrencyScrollbarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    private void initialize() {
        setBackgroundColor(getResources().getColor(R.color.scrollbar_background));

        linearLayout = new LinearLayout(getContext());
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT
        ));

        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        addView(linearLayout);
    }

    public void setCurrency(CurrencyModel currency) {
        linearLayout.removeAllViews();

        for (DenominationModel denomination : currency.getDenominations())
            addDenomination(denomination);
    }

    private void addDenomination(DenominationModel denominationModel) {
        ImageView imageView = new ImageView(getContext());
        imageView.setImageResource(denominationModel.getImageResource());
        imageView.setAdjustViewBounds(true);

        imageView.setPadding(PADDING_HORIZONTAL_PX, PADDING_VERTICAL_PX, PADDING_HORIZONTAL_PX, PADDING_VERTICAL_PX);
        linearLayout.addView(imageView);
    }

    private static int dpToPixels(int dp) {
        return (int)(dp * Resources.getSystem().getDisplayMetrics().density);
    }
}
