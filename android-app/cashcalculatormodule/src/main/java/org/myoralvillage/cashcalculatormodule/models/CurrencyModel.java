package org.myoralvillage.cashcalculatormodule.models;

import android.content.res.Resources;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Currency;
import java.util.Set;
import java.util.TreeSet;

public class CurrencyModel {
    private Currency currency;
    private Set<DenominationModel> denominations;

    public CurrencyModel(String currencyCode) {
        this.denominations = new TreeSet<>(new Comparator<DenominationModel>() {
            @Override
            public int compare(DenominationModel o1, DenominationModel o2) {
                // Sort greatest to least, instead of least to greatest
                return o2.compareTo(o1);
            }
        });

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
}
