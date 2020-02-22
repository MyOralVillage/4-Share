package org.myoralvillage.cashcalculatormodule.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import org.myoralvillage.cashcalculatormodule.R;

public class NumberPadView extends GridView {

    static final String[] elements = new String[]{
            "check", "0", "back",
            "1", "2", "3",
            "4", "5", "6",
            "7", "8", "9"};

    public NumberPadView(Context context) {
        super(context);
        init();
    }

    public NumberPadView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setVisibility(INVISIBLE);
        setBackgroundResource(R.drawable.scrollbar_background);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_list_item_1, elements);
        setAdapter(adapter);
    }

}
