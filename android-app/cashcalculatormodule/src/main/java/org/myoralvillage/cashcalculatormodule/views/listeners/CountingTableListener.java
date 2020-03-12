package org.myoralvillage.cashcalculatormodule.views.listeners;

import org.myoralvillage.cashcalculatormodule.models.DenominationModel;

public interface CountingTableListener {
    void onSwipeAddition();
    void onSwipeSubtraction();
    void onSwipeMultiplication();

    void onTapCalculateButton();
    void onTapClearButton();

    void onTapEnterHistory();
    void onTapNextHistory();
    void onTapPreviousHistory();

    void onDenominationChange(DenominationModel denomination, int oldCount, int newCount);
}
