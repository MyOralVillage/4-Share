package org.myoralvillage.cashcalculatormodule.models;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;

import java.util.ArrayList;
import java.util.List;

public class CountrySettingModel {
    private List<CountryCode> countries;

    private String defaultCode;

    public CountrySettingModel(Resources resources, Context context) {
        this.countries = new ArrayList<>();
        loadCountryCode(resources, context);
    }

    public List<CountryCode> getCountries(){
        return countries;
    }

    public String getDefaultCode(){
        return defaultCode;
    }

    public int getNumCountries(){
        return countries.size();
    }

    private void addCountryCode(String twoLetter, String threeLetter){
        countries.add(new CountryCode(twoLetter, threeLetter));
    }

    public String findCurrencyCode(String value){
        for (CountryCode code: countries){
            if(code.getCountryCode().equals(value))
                return code.getCurrencyCode();
        }
        return null;
    }

    public String findCountryCode(String value){
        for (CountryCode code: countries){
            if(code.getCurrencyCode().equals(value))
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
                    // Build CountryCode instance from the values in the xml
                    String value3 = array.getString(i);
                    String value2 = array.getString(i+1);

                    if (value3 != null && value2 != null) addCountryCode(value2, value3);
                }
                defaultCode = array.getString(array.length() - 1);
            } finally {
                // Required to call as part of the TypedArray lifecycle
                array.recycle();
            }
        }
    }

    public static class CountryCode{
        private String countryCode;
        private String currencyCode;

        CountryCode(String countryCode, String currencyCode){
            this.countryCode = countryCode;
            this.currencyCode = currencyCode;
        }

        public String getCurrencyCode() {
            return currencyCode;
        }

        String getCountryCode() {
            return countryCode;
        }
    }
}
