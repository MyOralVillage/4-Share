package org.myoralvillage.cashcalculatormodule.models;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

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

    public void addCountryCode(String twoLetter, String threeLetter){
        countries.add(new CountryCode(twoLetter, threeLetter));
    }

    public String findThreeLetterCode(String value){
        for (CountryCode code: countries){
            if(code.getTwoLetterCode().equals(value))
                return code.getThreeLetterCode();
        }
        return null;
    }

    public String findTwoLetterCode(String value){
        for (CountryCode code: countries){
            if(code.getThreeLetterCode().equals(value))
                return code.getTwoLetterCode();
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
                    // Build CurrencyModel instance from the values in the xml
                    String value2 = array.getString(i);
                    String value3 = array.getString(i+1);

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
        private String twoLetterCode;
        private String threeLetterCode;

        public CountryCode(String twoLetterCode, String threeLetterCode){
            this.twoLetterCode = twoLetterCode;
            this.threeLetterCode = threeLetterCode;
        }

        public String getThreeLetterCode() {
            return threeLetterCode;
        }

        public String getTwoLetterCode() {
            return twoLetterCode;
        }
    }
}
