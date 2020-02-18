package org.myoralvillage.cashcalculatormodule.models;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class CountingMethodTest {

    @Test
    public void testAdding() {
        CountingMethod model = new CountingMethod();
        double a = model.adding(100, 50);
        assertTrue(a == 150);
    }

    @Test
    public void testSubtracting() {
        CountingMethod model = new CountingMethod();
        double a = model.subtracting(100, 50);
        assertTrue( a == -50);
    }

    @Test
    public void testDivisionP() {
        CountingMethod cModel = new CountingMethod();
        CurrencyModel model = new CurrencyModel("CAD");
        model.addDenomination(new BigDecimal(25), 0);
        model.addDenomination(new BigDecimal(50), 0);
        model.addDenomination(new BigDecimal(100), 0);

        ArrayList<Integer> expected = new ArrayList<>();
        expected.add(1);
        expected.add(0);
        expected.add(0);

        ArrayList<Integer> result = cModel.allocation(100, model);
        assertEquals(expected, result);

    }

    @Test
    public void testDivisionN() {
        CountingMethod cModel = new CountingMethod();
        CurrencyModel model = new CurrencyModel("CAD");
        model.addDenomination(new BigDecimal(50), 0);
        model.addDenomination(new BigDecimal(100), 0);
        model.addDenomination(new BigDecimal(20), 0);
        model.addDenomination(new BigDecimal(5), 0);
        model.addDenomination(new BigDecimal(1), 0);


        ArrayList<Integer> expected = new ArrayList<>();
        expected.add(2);
        expected.add(0);
        expected.add(1);
        expected.add(1);
        expected.add(3);

        assertEquals(expected, cModel.allocation(-228, model));
    }
}
