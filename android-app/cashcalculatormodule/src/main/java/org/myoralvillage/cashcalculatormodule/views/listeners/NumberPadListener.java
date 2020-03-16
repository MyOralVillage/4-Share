package org.myoralvillage.cashcalculatormodule.views.listeners;

import java.math.BigDecimal;

/**
 * A listener interface for receiving gesture detection on the view, <code>NumberPadView</code>.
 *
 * @author Peter Panagiotis Roubatsis
 *
 * @see org.myoralvillage.cashcalculatormodule.views.NumberPadView
 */
public interface NumberPadListener {
    /**
     * Invoked when the check icon on the view, <code>NumberPadView</code> is tapped.
     *
     * @param value the value that has been entered in the view, <code>NumberPadView</code> before this
     *              icon was tapped.
     */
    void onCheck(BigDecimal value);

    /**
     * Invoked when the back icon on the view, <code>NumberPadView</code> is tapped.
     *
     * @param value the value that has been entered in the view, <code>NumberPadView</code> before this
     *              icon was tapped.
     */
    void onBack(BigDecimal value);

    /**
     * Invoked when a numeric digit on the view, <code>NumberPadView</code> is tapped.
     *
     * @param value the total value that has been entered.
     */
    void onTapNumber(BigDecimal value);

    /**
     * Invoked when a vertical swipe gesture is detected on the view, <code>NumberPadView</code>.
     */
    void onVerticalSwipe();
}
