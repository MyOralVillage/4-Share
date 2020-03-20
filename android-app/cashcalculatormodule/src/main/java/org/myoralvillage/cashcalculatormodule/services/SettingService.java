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
    private static String currencyName = setDefaultCurrency();

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
    private static String setDefaultCurrency(){
        String defaultCurrency;
        String systemLangauge = Locale.getDefault().getCountry();
        switch (systemLangauge){
            case "KE":
                defaultCurrency = "KES";
                break;
            default:
                defaultCurrency = "PKR";
                break;
        }
        return defaultCurrency;
    }
}
