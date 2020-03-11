package org.myoralvillage.cashcalculatormodule.views.listeners;

import java.math.BigDecimal;

public interface NumberPadListener {
    void onCheck(BigDecimal value);
    void onBack(BigDecimal value);
    void onTapNumber(BigDecimal value);

    void onVerticalSwipe();
}
