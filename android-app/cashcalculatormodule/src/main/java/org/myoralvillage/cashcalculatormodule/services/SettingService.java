package org.myoralvillage.cashcalculatormodule.services;

import android.content.Context;
import android.content.res.Resources;

import org.myoralvillage.cashcalculatormodule.models.CountrySettingModel;

import java.util.Locale;

/**
 * A service class used to initialize the currency of the Cash Calculator.
 *
 * @author Alexander Yang
 * @author Peter Panagiotis Roubatsis
 * @author Yujie Wu
 * @see Object
 */
public class SettingService {
    /**
     * The model used to initialize the countries and their currencies for the Setting.
     *
     * @see CountrySettingModel
     */

    private static CountrySettingModel countrySettingModel;
    /**
     * The default currency code for the Cash Calculator.
     */
    private static String currencyName;

    /**
     * Constructs a new <code>SettingService</code> with the application's context and resources.
     *
     * @param context The application's resource.
     * @param resources The application's context.
     */
    public SettingService(Context context, Resources resources){
        countrySettingModel = new CountrySettingModel(resources, context);
        currencyName = getDefaultCurrency();
    }

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

        defaultCurrency = countrySettingModel.findCurrencyCode(systemLangauge);

        if (defaultCurrency == null)
            return countrySettingModel.getDefaultCurrencyCode();

        return defaultCurrency;
    }

    /**
     * Returns the list of countryCode in the order that they were added.
     *
     * @return list of countryCode.
     */
    public String[] getDefaultOrder(){
        int num = countrySettingModel.getNumCodes();
        String[] order = new String[num];
        int i = 0;
        for (CountrySettingModel.Code code : countrySettingModel.getCodes()){
            order[i] = code.getCurrencyCode();
            i += 1;
        }

        return order;
    }
}
