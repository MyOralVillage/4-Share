package org.myoralvillage.cashcalculatormodule.services;

import org.myoralvillage.cashcalculatormodule.models.CurrencyModel;
import org.myoralvillage.cashcalculatormodule.models.DenominationModel;

import java.util.ArrayList;
import java.util.Set;


public class CountingService {
    public ArrayList<Integer> allocation(double num, CurrencyModel curr) {
        num = Math.abs(num);
        Set<DenominationModel> denominations = curr.getDenominations();
        ArrayList<Integer> result = new ArrayList<>();
        int denominationCount;
        double value;
        for (DenominationModel curr_deno: denominations) {
            value = curr_deno.getValue().doubleValue();
            denominationCount = (int) (num / value);
            num -= value * denominationCount;
            result.add(denominationCount);
        }

        return result;
    }
}