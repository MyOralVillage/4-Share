package org.myoralvillage.cashcalculatormodule.models;

import org.junit.Test;
import org.myoralvillage.cashcalculatormodule.services.CountingService;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class CountingServiceTest {

    @Test
    public void testDivisionP() {
        CountingService cModel = new CountingService();
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
        CountingService cModel = new CountingService();
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
