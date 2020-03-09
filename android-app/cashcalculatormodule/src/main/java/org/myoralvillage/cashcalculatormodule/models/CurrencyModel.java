package org.myoralvillage.cashcalculatormodule.models;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Set;
import java.util.TreeSet;

/**
 *  A model class used to represent the type of currency as well as the set of denominations available.
 *
 * @author Alexander Yang
 * @author Hamza Mahfooz
 * @author Peter Panagiotis Roubatsis
 * @see java.lang.Object
 */
public class CurrencyModel {
    /**
     * Represents the currency of the <code>CurrencyModel</code>
     *
     * @see Currency
     */
    private Currency currency;

    /**
     * A set of denominations (bills and coins) for the <code>Currency</code>. The set of
     * denominations is related to the denominations only available for this <code>CurrencyModel</code>.
     *
     * @see DenominationModel
     */
    private Set<DenominationModel> denominations;

    /**
     * Constructs a new <code>CurrencyModel</code> with a <code>TreeSet</code> initialized and a
     * <code>currency</code> instance based of the <code>currencyCode</code>.
     *
     * @param currencyCode A 3 letter string to denote the <code>currency</code>.
     *
     * @see TreeSet
     */
    public CurrencyModel(String currencyCode) {
        // Sort greatest to least, instead of least to greatest
        this.denominations = new TreeSet<>((o1, o2) -> o2.compareTo(o1));

        this.currency = Currency.getInstance(currencyCode);
    }

    /**
     * Adds a denomination to the set of denominations for this object.
     *
     * @param value The value of the denomination.
     * @param imageResource The resource identifier for the denomination in the
     *                      <code>CurrencyScrollBarView</code>.
     * @param imageResourceFolded The resource identifier for the denomination in the
     *                            <code>CountingTableView</code>.
     * @param scaleFactor A float value used to scale the images of the denomination on the screen.
     * @see DenominationModel
     * @see BigDecimal
     */
    public void addDenomination(BigDecimal value, int imageResource, int imageResourceFolded, float scaleFactor, float verticalOffsetInInches) {
        this.denominations.add(new DenominationModel(value, imageResource, imageResourceFolded, scaleFactor, verticalOffsetInInches));
    }

    /**
     * Returns the currency associated with this model.
     *
     * @return The currency of this model.
     */
    public Currency getCurrency() {
        return currency;
    }

    /**
     * Returns the set of denominations associated with this model.
     *
     * @return The set of denominations of this model.
     */
    public Set<DenominationModel> getDenominations() {
        return denominations;
    }

    /**
     * Constructs a <code>CurrencyModel</code> based on the specified <code>currencyCode</code>.
     * @param currencyCode A 3 letter String used to identify the currency.
     * @param resources The application's resources.
     * @param context The application's context.
     * @return A <code>CurrencyModel</code> with the image resources and denominations initialized
     * according to the specified <code>currencyCode</code>.
     *
     * @see BigDecimal
     */
    public static CurrencyModel loadCurrencyModel(String currencyCode, Resources resources, Context context) {
        CurrencyModel model = new CurrencyModel(currencyCode);

        // Get an array from the android resources with name "currency_{currencyCode}"
        String arrayName = String.format("currency_%s", currencyCode);
        int arrayId = resources.getIdentifier(arrayName, "array",
                context.getPackageName());

        if (arrayId != 0) {
            TypedArray array = resources.obtainTypedArray(arrayId);
            try {
                for (int i = 0; i < array.length(); i += 5) {
                    // Build CurrencyModel instance from the values in the xml
                    String value = array.getString(i);
                    int imageResourceId = array.getResourceId(i + 1, 0);
                    int imageResourceIDFolded = array.getResourceId(i + 2, 0);
                    float scaleFactor = array.getFloat(i + 3, 1.0f);
                    float verticalOffsetInInches = array.getFloat(i + 4, 0.0f);

                    if (value != null && imageResourceId != 0)
                        model.addDenomination(new BigDecimal(value), imageResourceId, imageResourceIDFolded, scaleFactor, verticalOffsetInInches);
                }
            } finally {
                // Required to call as part of the TypedArray lifecycle
                array.recycle();
            }
        }

        return model;
    }
}
