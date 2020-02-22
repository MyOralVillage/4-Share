package org.myoralvillage.cashcalculatormodule.models;

import org.junit.Test;

import static org.junit.Assert.*;

import java.math.BigDecimal;

public class DenominationModelTest {

    @Test
    public void testCompareToGreater() {
        DenominationModel a = new DenominationModel(new BigDecimal(100), 0, 0);
        DenominationModel b = new DenominationModel(new BigDecimal(25), 0, 0);
        assertTrue(a.compareTo(b) > 0);
    }

    @Test
    public void testCompareToLess() {
        DenominationModel a = new DenominationModel(new BigDecimal(100), 0, 0);
        DenominationModel b = new DenominationModel(new BigDecimal(25), 0, 0);
        assertTrue(b.compareTo(a) < 0);
    }

    @Test
    public void testCompareToEqual() {
        DenominationModel a = new DenominationModel(new BigDecimal(100), 0, 0);
        DenominationModel b = new DenominationModel(new BigDecimal(100), 0, 0);
        assertTrue(a.compareTo(b) == 0);
    }
}
