package org.myoralvillage.cashcalculatormodule.services;

import org.junit.Test;
import org.myoralvillage.cashcalculatormodule.models.CurrencyModel;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class CountingServiceTest {

    @Test
    public void testDivisionP() {
        CountingService cService = new CountingService();
        CurrencyModel model = new CurrencyModel("CAD");
        model.addDenomination(new BigDecimal(25), 0);
        model.addDenomination(new BigDecimal(50), 0);
        model.addDenomination(new BigDecimal(100), 0);

        ArrayList<Integer> expected = new ArrayList<>();
        expected.add(1);
        expected.add(0);
        expected.add(0);

        ArrayList<Integer> result = cService.allocate(new BigDecimal(100), model);
        assertEquals(expected, result);

    }

    @Test
    public void testDivisionN() {
        CountingService cService = new CountingService();
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

        assertEquals(expected, cService.allocate(new BigDecimal(-228), model));
    }

    @Test
    public void testSmallCurrencyCombination() {
        CountingService cService = new CountingService();
        CurrencyModel model = new CurrencyModel("PKR");
        model.addDenomination(new BigDecimal(50), 0);
        model.addDenomination(new BigDecimal(20), 0);
        model.addDenomination(new BigDecimal(10), 0);
        model.addDenomination(new BigDecimal(5), 0);
        model.addDenomination(new BigDecimal(2), 0);

        ArrayList<Integer> expected = new ArrayList<>();
        expected.add(0);
        expected.add(0);
        expected.add(1);
        expected.add(0);
        expected.add(3);

        assertEquals(expected, cService.allocate(new BigDecimal(16), model));
    }

    @Test
    public void testIdealAllocation() {
        CountingService cService = new CountingService();
        CurrencyModel model = new CurrencyModel("PKR");
        model.addDenomination(new BigDecimal(50), 0);
        model.addDenomination(new BigDecimal(20), 0);
        model.addDenomination(new BigDecimal(10), 0);
        model.addDenomination(new BigDecimal(5), 0);
        model.addDenomination(new BigDecimal(2), 0);

        ArrayList<Integer> expected = new ArrayList<>();
        expected.add(0);
        expected.add(1);
        expected.add(1);
        expected.add(1);
        expected.add(4);

        assertEquals(expected, cService.allocate(new BigDecimal(43), model));
    }
}
