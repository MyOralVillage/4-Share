package org.myoralvillage.cashcalculatormodule.services;

import org.myoralvillage.cashcalculatormodule.models.CurrencyModel;
import org.myoralvillage.cashcalculatormodule.models.DenominationModel;

import java.util.ArrayList;


public class CountingService {
    public ArrayList<Integer> allocation(double sum, CurrencyModel curr) {
        sum = Math.abs(sum);
        boolean haveLess;
        int number;
        double currValue, checkValue;
        ArrayList<Integer> result = new ArrayList<>();

        for (DenominationModel currDeno: curr.getDenominations()) {
            currValue = currDeno.getValue().doubleValue();
            haveLess = false;
            number = (int) (sum / currValue);
            for (DenominationModel denoCheck: curr.getDenominations()) {
                checkValue = denoCheck.getValue().doubleValue();
                if ((sum - currValue * number) % checkValue == 0) {
                    haveLess = true;
                    break;
                }
            }
            if (haveLess) {
                sum -= currValue * number;
                result.add(number);
            } else {
                result.add(0);
            }
        }
        return result;
    }
}
