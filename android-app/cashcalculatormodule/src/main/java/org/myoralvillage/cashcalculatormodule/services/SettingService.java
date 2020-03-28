package org.myoralvillage.cashcalculatormodule.services;

import android.content.Context;
import android.content.res.Resources;

import org.myoralvillage.cashcalculatormodule.models.CountrySettingModel;

import java.util.List;
import java.util.Locale;

/**
 * A service class used to initialize the currency of the Cash Calculator.
 *
 * @author Peter Panagiotis Roubatsis
 * @author Yujie Wu
 * @see Object
 */
public class SettingService {
    public static CountrySettingModel countrySettingModel;
    /**
     * The default currency code for the Cash Calculator.
     */
    private static String currencyName;

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

        defaultCurrency = countrySettingModel.findThreeLetterCode(systemLangauge);

        if (defaultCurrency == null)
            return countrySettingModel.getDefaultCode();

        return defaultCurrency;
    }

    public String[] getDefaultOrder(){
        int num = countrySettingModel.getNumCountries();
        String[] order = new String[num];
        int i = 0;
        for (CountrySettingModel.CountryCode countryCode : countrySettingModel.getCountries()){
            order[i] = countryCode.getThreeLetterCode();
        }

        return order;
    }
}
