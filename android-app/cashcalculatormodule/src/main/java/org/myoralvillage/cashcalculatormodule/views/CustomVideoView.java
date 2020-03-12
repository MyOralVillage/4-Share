package org.myoralvillage.cashcalculatormodule.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.VideoView;

public class CustomVideoView extends VideoView {
    public CustomVideoView(Context context) {
        super(context);
    }

    public CustomVideoView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public CustomVideoView(Context context, AttributeSet attributeSet, int defStyle) {
        super(context, attributeSet, defStyle);
    }

    protected void onMeasure(int widthMeasure, int heightMeasure) {
        int width = getDefaultSize(0, widthMeasure);
        int height = getDefaultSize(0, heightMeasure);
        setMeasuredDimension(width, height);
    }
}
