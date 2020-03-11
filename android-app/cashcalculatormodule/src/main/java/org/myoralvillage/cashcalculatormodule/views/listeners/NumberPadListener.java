package org.myoralvillage.cashcalculatormodule.views.listeners;

import java.math.BigDecimal;

public interface NumberPadListener {
    void check(BigDecimal value);
    void back(BigDecimal value);
    void number(BigDecimal value);
}
