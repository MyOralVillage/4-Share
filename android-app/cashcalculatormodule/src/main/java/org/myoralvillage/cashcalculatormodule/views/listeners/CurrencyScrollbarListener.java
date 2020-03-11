package org.myoralvillage.cashcalculatormodule.views.listeners;

import org.myoralvillage.cashcalculatormodule.models.DenominationModel;

public interface CurrencyScrollbarListener {
    void onTapDenomination(DenominationModel denomination);
    void onVerticalSwipe();
}
