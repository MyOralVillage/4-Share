package org.myoralvillage.cashcalculatormodule.views.listeners;

import org.myoralvillage.cashcalculatormodule.models.DenominationModel;

/**
 * A listener interface for receiving gesture detection on the view, <code>CountingTableSurfaceView</code>.
 *
 * @author Peter Panagiotis Roubatsis
 *
 * @see org.myoralvillage.cashcalculatormodule.views.CountingTableSurfaceView
 */
public interface CountingTableSurfaceListener {
    /**
     * Invoked when a denomination is removed via gestures.
     *
     * @param model the denomination being affected.
     * @param oldCount the current number of that denomination.
     * @param newCount the new number of that denomination.
     */
    void onTableRemove(DenominationModel model, int oldCount, int newCount);
}
