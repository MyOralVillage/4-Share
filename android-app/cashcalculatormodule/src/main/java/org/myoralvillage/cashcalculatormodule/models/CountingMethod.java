package org.myoralvillage.cashcalculatormodule.models;

import java.util.ArrayList;
import java.util.Set;


public class CountingMethod {

    public double adding(double num, double current_sum) {
        return num + current_sum;
    }

    public double subtracting(double num, double current_sum) {
        return current_sum - num;
    }



    public ArrayList<Integer> allocation(double num, CurrencyModel curr) {
        num = Math.abs(num);
        Set<DenominationModel> denominations = curr.getDenominations();
        ArrayList<Integer> result = new ArrayList<>();
        DenominationModel curr_deno;
        int temp_number;
        double value;
        for (Object entry: denominations) {
            curr_deno = (DenominationModel) entry;
            value = curr_deno.getValue().doubleValue();
            temp_number = (int) (num / value);
            num -= value * temp_number;
            result.add(temp_number);
        }

        return result;
    }
}