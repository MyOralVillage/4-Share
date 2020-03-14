package org.myoralvillage.cashcalculatormodule.views.listeners;

import org.myoralvillage.cashcalculatormodule.models.DenominationModel;

/**
 * A listener interface for receiving gesture detection on the view, <code>CountingTableView</code>.
 *
 * @author Hamza Mahfooz
 * @author Peter Panagiotis Roubatsis
 *
 * @see org.myoralvillage.cashcalculatormodule.views.CountingTableView
 */
public interface CountingTableListener {
    /**
     * Invoked when a swipe is detected on the <code>CountingTableView</code> to indicate Addition.
     */
    void onSwipeAddition();

    /**
     * Invoked when a swipe is detected on the <code>CountingTableView</code> to indicate Subtraction.
     */
    void onSwipeSubtraction();

    /**
     * Invoked when a swipe is detected on the <code>CountingTableView</code> to indicate Multiplication.
     */
    void onSwipeMultiplication();

    /**
     * Invoked when the arithmetic symbol on the <code>CountingTableView</code> is tapped.
     */
    void onTapCalculateButton();

    /**
     * Invoked when the icon, symbolising clear the denominations, on the <code>CountingTableView</code>
     * is tapped.
     */
    void onTapClearButton();

    /**
     * Invoked when the icon, symbolising to enter history mode, on the <code>CountingTableView</code>
     * is tapped.
     */
    void onTapEnterHistory();

    /**
     * Invoked when the icon, symbolising to go to the next state in the history mode, on the
     * <code>CountingTableView</code> is tapped.
     */
    void onTapNextHistory();

    /**
     * Invoked when the icon, symbolising to go to the previous state in the history mode, on the
     * <code>CountingTableView</code> is tapped.
     */
    void onTapPreviousHistory();

    /**
     * Invoked when there is a change to the value of the denominations on the <code>CountingTableView</code>.
     *
     * @param denomination the denomination to be updated.
     * @param oldCount the current number of that denomination.
     * @param newCount the new number of that denomination.
     */
    void onDenominationChange(DenominationModel denomination, int oldCount, int newCount);
}
