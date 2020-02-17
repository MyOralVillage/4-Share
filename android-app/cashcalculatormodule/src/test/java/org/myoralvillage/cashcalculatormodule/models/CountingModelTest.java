package org.myoralvillage.cashcalculatormodule.models;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class CountingModelTest {

    @Test
    public void testAdding() {
        CountingModel model = new CountingModel();
        double a = model.add_or_sub(100, 50, true);
        assertTrue(a == 150);
    }

    @Test
    public void testSubtracting() {
        CountingModel model = new CountingModel();
        double a = model.add_or_sub(100, 50, false);
        assertTrue( a == -50);
    }

    @Test
    public void testDivisionP() {
        CountingModel c_model = new CountingModel();
        CurrencyModel model = new CurrencyModel("CAD");
        model.addDenomination(new BigDecimal(25), 0);
        model.addDenomination(new BigDecimal(50), 0);
        model.addDenomination(new BigDecimal(100), 0);

        ArrayList<ArrayList<Object>> result = c_model.division(100, model);

        assertEquals(1, result.get(0).get(1));
        assertEquals(0, result.get(1).get(1));
        assertEquals(0, result.get(2).get(1));

    }

    @Test
    public void testDivisionN() {
        CountingModel c_model = new CountingModel();
        CurrencyModel model = new CurrencyModel("CAD");
        model.addDenomination(new BigDecimal(50), 0);
        model.addDenomination(new BigDecimal(100), 0);
        model.addDenomination(new BigDecimal(20), 0);
        model.addDenomination(new BigDecimal(5), 0);
        model.addDenomination(new BigDecimal(1), 0);


        ArrayList<ArrayList<Object>> result = c_model.division(-228, model);

        assertEquals(2, result.get(0).get(1));
        assertEquals(0, result.get(1).get(1));
        assertEquals(1, result.get(2).get(1));
        assertEquals(1, result.get(3).get(1));
        assertEquals(3, result.get(4).get(1));

    }
}
