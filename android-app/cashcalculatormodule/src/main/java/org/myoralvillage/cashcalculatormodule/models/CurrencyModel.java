package org.myoralvillage.cashcalculatormodule.models;

import android.content.res.Resources;

import org.myoralvillage.cashcalculatormodule.R;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Set;
import java.util.TreeSet;

public class CurrencyModel {
    private Currency currency;
    private Set<DenominationModel> denominations;

    public CurrencyModel(String currencyCode) {
        this.denominations = new TreeSet<>();
        this.currency = Currency.getInstance(currencyCode);
    }

    public void addDenomination(BigDecimal value, int imageResource) {
        this.denominations.add(new DenominationModel(value, imageResource));
    }

    public Currency getCurrency() {
        return currency;
    }

    public Set<DenominationModel> getDenominations() {
        return denominations;
    }

    public static CurrencyModel getInstance(String currencyCode) {
        CurrencyModel model = new CurrencyModel(currencyCode);

        switch(currencyCode) {
            case "CAD":
                model.addDenomination(new BigDecimal(100), R.drawable.currency_cad_hundred_dollars);
                model.addDenomination(new BigDecimal(50), R.drawable.currency_cad_fifty_dollars);
                model.addDenomination(new BigDecimal(20), R.drawable.currency_cad_twenty_dollars);
                model.addDenomination(new BigDecimal(10), R.drawable.currency_cad_ten_dollars);
                model.addDenomination(new BigDecimal(5), R.drawable.currency_cad_five_dollars);
                break;
        }

        return model;
    }
}
