package org.myoralvillage.cashcalculatormodule.models;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Set;
import java.util.TreeSet;

public class CurrencyModel {
    private Currency currency;
    private Set<DenominationModel> denominations;

    public CurrencyModel(String currencyCode) {
        // Sort greatest to least, instead of least to greatest
        this.denominations = new TreeSet<>((o1, o2) -> o2.compareTo(o1));

        this.currency = Currency.getInstance(currencyCode);
    }

    public void addDenomination(BigDecimal value, int imageResource, int imageResourceFolded, float scaleFactor) {
        this.denominations.add(new DenominationModel(value, imageResource, imageResourceFolded, scaleFactor));
    }

    public Currency getCurrency() {
        return currency;
    }

    public Set<DenominationModel> getDenominations() {
        return denominations;
    }

    public static CurrencyModel loadCurrencyModel(String currencyCode, Resources resources, Context context) {
        CurrencyModel model = new CurrencyModel(currencyCode);

        // Get an array from the android resources with name "currency_{currencyCode}"
        String arrayName = String.format("currency_%s", currencyCode);
        int arrayId = resources.getIdentifier(arrayName, "array",
                context.getPackageName());

        if (arrayId != 0) {
            TypedArray array = resources.obtainTypedArray(arrayId);
            try {
                for (int i = 0; i < array.length(); i += 4) {
                    // Build CurrencyModel instance from the values in the xml
                    String value = array.getString(i);
                    int imageResourceId = array.getResourceId(i + 1, 0);
                    int imageResourceIDFolded = array.getResourceId(i + 2, 0);
                    float scaleFactor = array.getFloat(i + 3, 1.0f);

                    if (value != null && imageResourceId != 0)
                        model.addDenomination(new BigDecimal(value), imageResourceId, imageResourceIDFolded, scaleFactor);
                }
            } finally {
                // Required to call as part of the TypedArray lifecycle
                array.recycle();
            }
        }

        return model;
    }
}
