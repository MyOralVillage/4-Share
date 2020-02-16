package org.myoralvillage.cashcalculatormodule.models;

import java.math.BigDecimal;

public class DenominationModel implements Comparable<DenominationModel> {
    private BigDecimal value;
    private int imageResource;

    public DenominationModel(BigDecimal value, int imageResource) {
        this.value = value;
        this.imageResource = imageResource;
    }

    public BigDecimal getValue() {
        return value;
    }

    public int getImageResource() {
        return imageResource;
    }

    @Override
    public int compareTo(DenominationModel o) {
        return this.value.compareTo(o.value);
    }
}
