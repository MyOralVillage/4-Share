package org.myoralvillage.cashcalculatormodule.services;

import org.myoralvillage.cashcalculatormodule.models.CurrencyModel;
import org.myoralvillage.cashcalculatormodule.models.DenominationModel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class CountingService {
    private HashMap<CacheKey, ArrayList<Integer>> allocationCache = new HashMap<>();

    public ArrayList<Integer> allocate(BigDecimal value, CurrencyModel model) {
        List<BigDecimal> denominations = new ArrayList<>();
        for (DenominationModel d : model.getDenominations())
            denominations.add(d.getValue());

        return allocateHelper(value.abs(), denominations, model.getCurrency().getSymbol());
    }

    private ArrayList<Integer> allocateHelper(BigDecimal value, List<BigDecimal> denominations, String symbol) {
        if (value.compareTo(BigDecimal.ZERO) < 0)
            return null;

        ArrayList<Integer> allocation = allocateZero(denominations.size());
        if (value.equals(BigDecimal.ZERO))
            return allocation;

        if (isCached(value, symbol))
            return getAllocationFromCache(value, symbol);

        for (int i = 0; i < denominations.size(); i++) {
            BigDecimal denomination = denominations.get(i);
            if (denomination.compareTo(value) <= 0) {
                List<Integer> possibleAllocation = allocateHelper(value.subtract(denomination), denominations, symbol);
                if (possibleAllocation != null) {
                    for (int j = 0; j < allocation.size(); j++) {
                        allocation.set(j, possibleAllocation.get(j));
                    }

                    allocation.set(i, possibleAllocation.get(i) + 1);
                    cache(value, symbol, allocation);
                    return allocation;
                }
            }
        }

        cache(value, symbol, null);
        return null;
    }

    private ArrayList<Integer> allocateZero(int size) {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < size; i++)
            list.add(0);

        return list;
    }

    private void cache(BigDecimal value, String symbol, ArrayList<Integer> allocations) {
        this.allocationCache.put(new CacheKey(symbol, value), allocations);
    }

    private boolean isCached(BigDecimal value, String symbol) {
        CacheKey key = new CacheKey(symbol, value);
        return this.allocationCache.containsKey(key);
    }

    private ArrayList<Integer> getAllocationFromCache(BigDecimal value, String symbol) {
        CacheKey key = new CacheKey(symbol, value);
        return this.allocationCache.get(key);
    }

    private static class CacheKey {
        private String symbol;
        private BigDecimal value;

        CacheKey(String symbol, BigDecimal value) {
            this.symbol = symbol;
            this.value = value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            CacheKey cacheKey = (CacheKey) o;

            if (!symbol.equals(cacheKey.symbol)) return false;
            return value.equals(cacheKey.value);
        }

        @Override
        public int hashCode() {
            int result = symbol.hashCode();
            result = 31 * result + value.hashCode();
            return result;
        }
    }
}
