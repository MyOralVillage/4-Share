package org.myoralvillage.cashcalculatormodule.models;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;

import java.util.ArrayList;
import java.util.List;

/**
 * A model class used to represent the type of codes and their currencies.
 *
 * @author Alexander Yang
 * @see Object
 */
public class CountrySettingModel {
    /**
     * A list of Codes for a country and their currencies.
     *
     * @see Code
     */
    private List<Code> codes;

    /**
     * The default currency code if the country is not in the list.
     */
    private String defaultCurrencyCode;

    /**
     * Constructs a new <code>CountrySettingModel</code> with the specified application's resources
     * and context.
     *
     * @param resources The resource of this application.
     * @param context The context of this application.
     */
    public CountrySettingModel(Resources resources, Context context) {
        this.codes = new ArrayList<>();
        loadCountryCode(resources, context);
    }

    /**
     * Returns the list of codes associated with this model.
     *
     * @return list of codes.
     * @see Code
     */
    public List<Code> getCodes(){
        return codes;
    }

    /**
     * Returns the default currency code associated with this model.
     *
     * @return the default currency code.
     */
    public String getDefaultCurrencyCode(){
        return defaultCurrencyCode;
    }

    /**
     * Returns the number of codes in the list, <code>codes</code>.
     *
     * @return the size of the list, codes.
     */
    public int getNumCodes(){
        return codes.size();
    }

    /**
     * Adds a new code with the specified country code and currency code to the list of codes in this
     * model.
     *
     * @param country The 2 letter country code.
     * @param currency The 3 letter currency code.
     *
     * @see Code
     */
    private void addCode(String country, String currency){
        codes.add(new Code(country, currency));
    }

    /**
     * Returns the currency code associated with the specified country code.
     *
     * @param country The country code to be searched.
     * @return The resulting currency code; Returns null if not found in the list.
     */
    public String findCurrencyCode(String country){
        for (Code code: codes){
            if(code.getCountryCode().equals(country))
                return code.getCurrencyCode();
        }
        return null;
    }

    /**
     * Returns the country code associated with the specified currency code.
     *
     * @param currency The currency code to be searched.
     * @return The resulting country code; Returns null if not found in the list.
     */
    public String findCountryCode(String currency){
        for (Code code: codes){
            if(code.getCurrencyCode().equals(currency))
                return code.getCountryCode();
        }
        return null;
    }

    private void loadCountryCode(Resources resources, Context context){
        int arrayId = resources.getIdentifier("default_country_order", "array",
                context.getPackageName());

        if (arrayId != 0) {
            TypedArray array = resources.obtainTypedArray(arrayId);
            try {
                for (int i = 0; i < array.length() - 1; i += 2) {
                    // Build Code instance from the values in the xml
                    String currency = array.getString(i);
                    String country = array.getString(i+1);

                    if (currency != null && country != null) addCode(country, currency);
                }
                defaultCurrencyCode = array.getString(0);
            } finally {
                // Required to call as part of the TypedArray lifecycle
                array.recycle();
            }
        }
    }

    /**
     * A model class used to represent the codes for the country and the currency that country use.
     */
    public static class Code {
        /**
         * The country's 2 letter code.
         */
        private String countryCode;

        /**
         * The currency's 3 letter code.
         */
        private String currencyCode;

        /**
         * Constructs a new <code>Code</code> with the specified country code and currency code.
         *
         * @param countryCode A 2 letter String.
         * @param currencyCode A 3 letter String.
         */
        Code(String countryCode, String currencyCode){
            this.countryCode = countryCode;
            this.currencyCode = currencyCode;
        }

        /**
         * Returns the currency code related to this model.
         *
         * @return the currency code.
         */
        public String getCurrencyCode() {
            return currencyCode;
        }

        /**
         * Return the country code related to this model.
         *
         * @return the country code.
         */
        String getCountryCode() {
            return countryCode;
        }
    }
}
