package org.myoralvillage.cashcalculatormodule.models;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class CurrencyModelTest {

    @Test
    public void testSortingOrder() {
        CurrencyModel model = new CurrencyModel("CAD");
        model.addDenomination(new BigDecimal(50), 0);
        model.addDenomination(new BigDecimal(100), 0);
        model.addDenomination(new BigDecimal(25), 0);

        DenominationModel[] expected = new DenominationModel[] {
                new DenominationModel(new BigDecimal(100), 0),
                new DenominationModel(new BigDecimal(50), 0),
                new DenominationModel(new BigDecimal(25), 0)
        };

        assertArrayEquals(expected, model.getDenominations().toArray());
    }
}
