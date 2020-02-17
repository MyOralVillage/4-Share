package org.myoralvillage.cashcalculatormodule.models;

import java.util.ArrayList;
import java.util.Set;


public class CountingModel {

    public double add_or_sub(double num, double current_sum, boolean add_tf) {
        double result;
        if (add_tf) {
            result = num + current_sum;
        } else {
            result = current_sum - num;
        }
        return result;
    }



    public ArrayList<ArrayList<Object>> division(double num, CurrencyModel curr) {
        num = Math.abs(num);
        Set<DenominationModel> denominations = curr.getDenominations();
        ArrayList<ArrayList<Object>> result = new ArrayList<>();
        DenominationModel curr_deno;
        int temp_number;
        double value;
        for (Object entry: denominations) {
            ArrayList<Object> deno_info = new ArrayList<>();
            curr_deno = (DenominationModel) entry;
            deno_info.add(curr_deno);
            value = curr_deno.getValue().doubleValue();
            temp_number = (int) (num / value);
            deno_info.add(temp_number);
            num -= value * temp_number;
            result.add(deno_info);
        }

        return result;
    }
}