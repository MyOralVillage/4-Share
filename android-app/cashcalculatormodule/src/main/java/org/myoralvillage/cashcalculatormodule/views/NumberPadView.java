package org.myoralvillage.cashcalculatormodule.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import org.myoralvillage.cashcalculatormodule.R;
import org.myoralvillage.cashcalculatormodule.views.listeners.NumberPadListener;

import java.math.BigDecimal;

public class NumberPadView extends LinearLayout {
    private NumberPadListener listener = null;

    public NumberPadView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflate(getContext(), R.layout.number_pad, this);
        initializeNumberpad();
    }

    private void initializeNumberpad(){
        final StringBuilder stringBuilder = new StringBuilder();
        View.OnClickListener listener = v -> {
            String text;

            if (v instanceof Button) {
                text = ((Button) v).getText().toString();
            } else {
                text = v.getContentDescription().toString();
            }

            switch (text) {
                case "check":
                    check(stringBuilderToBigDecimal(stringBuilder));
                    stringBuilder.setLength(0);
                    return;
                case "back":
                    if (stringBuilder.length() > 0) {
                        stringBuilder.setLength(stringBuilder.length() - 1);
                        back(stringBuilderToBigDecimal(stringBuilder));
                    }
                    break;
                default:
                    if (stringBuilder.length() == 0 && text.equals("0")) {
                        return;
                    }
                    stringBuilder.append(text);
                    number(stringBuilderToBigDecimal(stringBuilder));
                    break;
            }
        };

        for (View button : new View[] {findViewById(R.id.zero), findViewById(R.id.one),
                findViewById(R.id.two), findViewById(R.id.three), findViewById(R.id.four),
                findViewById(R.id.five), findViewById(R.id.six), findViewById(R.id.seven),
                findViewById(R.id.eight), findViewById(R.id.nine), findViewById(R.id.check),
                findViewById(R.id.back)}) {
            button.setOnClickListener(listener);
        }
    }

    private void check(BigDecimal value) {
        if (listener != null)
            listener.check(value);
    }

    private void back(BigDecimal value) {
        if (listener != null)
            listener.back(value);
    }

    private void number(BigDecimal value) {
        if (listener != null)
            listener.number(value);
    }

    private static BigDecimal stringBuilderToBigDecimal(StringBuilder stringBuilder) {
        return stringBuilder.length() > 0 ?
                new BigDecimal(Integer.valueOf(stringBuilder.toString())) : BigDecimal.ZERO;
    }

    public void setListener(NumberPadListener listener) {
        this.listener = listener;
    }
}
