package org.myoralvillage.cashcalculatormodule.views.listeners;

import org.myoralvillage.cashcalculatormodule.models.DenominationModel;

public interface CountingTableListener {
    void onTableChange(DenominationModel model, int oldCount, int newCount);
}
