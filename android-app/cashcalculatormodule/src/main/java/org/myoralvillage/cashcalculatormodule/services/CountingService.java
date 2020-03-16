package org.myoralvillage.cashcalculatormodule.services;

import org.myoralvillage.cashcalculatormodule.models.CurrencyModel;
import org.myoralvillage.cashcalculatormodule.models.DenominationModel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A Service Class used to partition the total value into the appropriate denominations.
 *
 * @author Peter Panagiotis Roubatsis
 * @see Object
 * @see org.myoralvillage.cashcalculatormodule.views.CountingTableView
 */
public class CountingService {
    /**
     * A hashmap used to allocate the denomination, represented as a <code>CacheKey</code> class,
     * and total value of the denominations.
     *
     * @see CacheKey
     */
    private HashMap<CacheKey, ArrayList<Integer>> allocationCache = new HashMap<>();

    /**
     * Allocates the partitioning of the total value into the appropriate denominations.
     *
     * @param value The total value to be partitioned.
     * @param model The currency of the denominations.
     * @return the list of values allocated from the total value.
     */
    public ArrayList<Integer> allocate(BigDecimal value, CurrencyModel model) {
        List<BigDecimal> denominations = new ArrayList<>();
        for (DenominationModel d : model.getDenominations())
            denominations.add(d.getValue());

        ArrayList<Integer> allocation = allocateHelper(value.abs(), denominations, model.getCurrency().getSymbol());
        return allocation != null ? allocation : allocateZero(denominations.size());
    }

    /**
     * This method helps with the partitioning of the total value into denominations.
     *
     * @param value The total value to be partitioned.
     * @param denominations The currency of the denominations.
     * @param symbol The symbol related to the the currency.
     * @return the list of values allocated from the total value
     */
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

    /**
     *  This method helps with the partitioning of the total value into denominations if the value
     *  is less than zero from <code>allocate</code>.
     *
     * @param size the number of different denominations.
     * @return an array list of zeros.
     */
    private ArrayList<Integer> allocateZero(int size) {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < size; i++)
            list.add(0);

        return list;
    }

    /**
     * Inserts the respective value and symbol into the <code>allocationCache</code>.
     *
     * @param value The value of this allocation.
     * @param symbol The currency symbol related to this allocation.
     * @param allocations the list of allocations to be stored in the cache.
     */
    private void cache(BigDecimal value, String symbol, ArrayList<Integer> allocations) {
        this.allocationCache.put(new CacheKey(symbol, value), allocations);
    }

    /**
     * Checks to see if there exists a list of allocations for the specified value and symbol
     *
     * @param value The total value of the allocations.
     * @param symbol The currency symbol related to the value.
     * @return True if the <code>allocationCache</code> contains the value with the correct symbol
     * ; False otherwise.
     */
    private boolean isCached(BigDecimal value, String symbol) {
        CacheKey key = new CacheKey(symbol, value);
        return this.allocationCache.containsKey(key);
    }

    /**
     * Returns the <code>CacheKey</code> associated with the specified value and symbol.
     *
     * @param value The total value of the allocations to be retrieved.
     * @param symbol The currency symbol related to the value.
     * @return the array list of allocations.
     */
    private ArrayList<Integer> getAllocationFromCache(BigDecimal value, String symbol) {
        CacheKey key = new CacheKey(symbol, value);
        return this.allocationCache.get(key);
    }

    /**
     * A static class used to store a value with its respective currency symbol.
     */
    private static class CacheKey {
        /**
         * The currency symbol related to the value.
         */
        private String symbol;

        /**
         * The value of an allocation.
         */
        private BigDecimal value;

        /**
         * Constructs a new <code>CacheKey</code> with the specified symbol and value.
         *
         * @param symbol The currency symbol related to this value.
         * @param value The value of this class.
         */
        CacheKey(String symbol, BigDecimal value) {
            this.symbol = symbol;
            this.value = value;
        }

        /**
         * Indicates whether some other <code>CacheKey</code> is equal to this one.
         *
         * @param o a <code>CacheKey</code> with which to compare.
         * @return true if this <code>ACacheKey</code> is the same as the obj argument; false
         * otherwise.
         */
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            CacheKey cacheKey = (CacheKey) o;

            if (!symbol.equals(cacheKey.symbol)) return false;
            return value.equals(cacheKey.value);
        }

        /**
         * Returns a hash code value for the <code>CacheKey</code>. Whenever it is invoked on the
         * same <code>CacheKey</code> multiple times during an execution of the application, the
         * hashCode must consistently return the same integer
         *
         * @return  a hash code value for this <code>CacheKey</code>
         */
        @Override
        public int hashCode() {
            int result = symbol.hashCode();
            result = 31 * result + value.hashCode();
            return result;
        }
    }
}
