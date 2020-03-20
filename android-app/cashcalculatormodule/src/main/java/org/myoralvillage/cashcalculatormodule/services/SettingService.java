package org.myoralvillage.cashcalculatormodule.services;

import java.util.Locale;

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
    private static String currencyName = getDefaultCurrency();

    /**
     * Returns current the currency code for the Cash Calculator.
     *
     * @return the currency code for the Cash Calculator (Usually a 3 letter string).
     */
    public String getCurrencyName(){
        return currencyName;
    }

    /**
     * Changes the current currency for the Cash Calculator.
     *
     * @param newName The new currency code for the Cash Calculator.
     */
    public void setCurrencyName(String newName){
        currencyName = newName;
    }

    /**
     * Change the default currency to match the system country.
     * @return default currency.
     */
    private static String getDefaultCurrency(){
        String defaultCurrency;
        String systemLangauge = Locale.getDefault().getCountry();
        switch (systemLangauge){
            case "PK":
                defaultCurrency = "PKR";
                break;
            default:
                defaultCurrency = "KES";
                break;
        }
        return defaultCurrency;
    }
}
