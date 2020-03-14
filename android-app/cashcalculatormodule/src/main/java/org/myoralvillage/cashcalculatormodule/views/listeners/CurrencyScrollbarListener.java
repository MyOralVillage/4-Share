package org.myoralvillage.cashcalculatormodule.views.listeners;

import org.myoralvillage.cashcalculatormodule.models.DenominationModel;

/**
 * A listener interface for receiving gesture detection on the view, <code>CurrencyScrollbarView</code>.
 *
 * @author Peter Panagiotis Roubatsis
 * @see org.myoralvillage.cashcalculatormodule.views.CurrencyScrollbarView
 */
public interface CurrencyScrollbarListener {
    /**
     * Invoked when a denomination is tapped on the view, <code>CurrencyScrollbarView</code>.
     *
     * @param denomination the denomination being affected.
     * @see DenominationModel
     */
    void onTapDenomination(DenominationModel denomination);

    /**
     * Invoked when a vertical swipe gesture is detected on the view, <code>CurrencyScrollbarView</code>.
     */
    void onVerticalSwipe();
}
