package org.myoralvillage.cashcalculatormodule.services;

import android.content.Context;

/**
 * A service class used to initialize the currency of the Cash Calculator.
 *
 * @author Peter Panagiotis Roubatsis
 * @author Yujie Wu
 * @see Object
 */
public class SettingService {
    /**
     * The default currency code for the Cash Calculator.
     */
    private static String currencyName;

    public SettingService(Context context) {
        new CurrencyService(context).call(currencies -> currencyName = currencies[0]);
    }

    /**
     * Returns current the currency code for the Cash Calculator.
     *
     * @return the currency code for the Cash Calculator (Usually a 3 letter string).
     */
    public String getCurrencyName() {
        return currencyName;
    }

    /**
     * Changes the current currency for the Cash Calculator.
     *
     * @param newName The new currency code for the Cash Calculator.
     */
    public void setCurrencyName(String newName) {
        currencyName = newName;
    }
}
