package org.myoralvillage.cashcalculatormodule.services;

public class SettingService {
    private static String currencyName = "PKR";

    public String getCurrencyName(){
        return currencyName;
    }

    public void setCurrencyName(String newName){
        currencyName = newName;
    }
}
