package org.myoralvillage.cashcalculatormodule.models;

import androidx.annotation.Nullable;

import java.math.BigDecimal;

public class DenominationModel implements Comparable<DenominationModel> {
    private BigDecimal value;
    private int imageResource;
    private int imageResourceFolded;

    public DenominationModel(BigDecimal value, int imageResource, int imageResourceFolded) {
        this.value = value;
        this.imageResource = imageResource;
        this.imageResourceFolded = imageResourceFolded;
    }

    public BigDecimal getValue() {
        return value;
    }

    public int getImageResource() {
        return imageResource;
    }

    public int getImageResourceFolded(){
        return imageResourceFolded;
    }

    @Override
    public int compareTo(DenominationModel o) {
        return this.value.compareTo(o.value);
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (!(obj instanceof DenominationModel))
            return false;

        DenominationModel dm = (DenominationModel) obj;
        return value.equals(dm.value) && imageResource == dm.imageResource;
    }
}
