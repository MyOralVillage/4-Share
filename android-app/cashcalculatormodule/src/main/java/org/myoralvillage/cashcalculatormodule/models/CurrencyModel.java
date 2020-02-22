package org.myoralvillage.cashcalculatormodule.models;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Set;
import java.util.TreeSet;

public class CurrencyModel {
    private Currency currency;
    private Set<DenominationModel> denominations;

    public CurrencyModel(String currencyCode) {
        // Sort greatest to least, instead of least to greatest
        this.denominations = new TreeSet<>((o1, o2) -> o2.compareTo(o1));

        this.currency = Currency.getInstance(currencyCode);
    }

    public void addDenomination(BigDecimal value, int imageResource, int imageResourceFolded) {
        this.denominations.add(new DenominationModel(value, imageResource, imageResourceFolded));
    }

    public Currency getCurrency() {
        return currency;
    }

    public Set<DenominationModel> getDenominations() {
        return denominations;
    }
}
