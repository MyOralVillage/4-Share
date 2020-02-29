package org.myoralvillage.cashcalculatormodule.models;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class CurrencyModelTest {

    @Test
    public void testSortingOrder() {
        CurrencyModel model = new CurrencyModel("CAD");
        model.addDenomination(new BigDecimal(50), 0, 0, 1.0f);
        model.addDenomination(new BigDecimal(100), 0, 0, 1.0f);
        model.addDenomination(new BigDecimal(25), 0, 0, 1.0f);

        DenominationModel[] expected = new DenominationModel[] {
                new DenominationModel(new BigDecimal(100), 0, 0, 1.0f),
                new DenominationModel(new BigDecimal(50), 0, 0, 1.0f),
                new DenominationModel(new BigDecimal(25), 0, 0, 1.0f)
        };

        assertArrayEquals(expected, model.getDenominations().toArray());
    }
}
