package org.myoralvillage.cashcalculatormodule.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import org.myoralvillage.cashcalculatormodule.models.DenominationModel;
import org.myoralvillage.cashcalculatormodule.views.listeners.CountingTableListener;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class CountingTableView extends View {

    private CountingTableListener countingTableListener;
    private Map<DenominationModel, Integer> counts;
    private Map<DenominationModel, Bitmap> bitmaps;
    private boolean initialized;

    public CountingTableView(Context context) {
        super(context);
        init();
    }

    public CountingTableView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        counts = new TreeMap<>((o1, o2) -> o2.compareTo(o1));
        bitmaps = new HashMap<>();
        countingTableListener = null;
        initialized = false;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!initialized) {
            return;
        }
        int width = getWidth();
        for (Bitmap bmp : bitmaps.values()) {
            width -= bmp.getWidth();
        }
        int left = 20;
        int cut =  (int) (width / ((float) counts.size()));
        int top = 50;
        for (Map.Entry<DenominationModel, Integer> entry : counts.entrySet()) {
            Bitmap bmp = bitmaps.get(entry.getKey());
            for (int i = 1; i < entry.getValue() + 1; i++) {
                canvas.drawBitmap(bmp, left + 20 * i, top * i, null);
            }
            left += cut + bmp.getWidth();
        }
    }

    private Bitmap scaleBitmap(Bitmap bmp, int scale) {
        int width = scale;
        int height = scale;
        if (bmp.getHeight() > bmp.getWidth()) {
            width = (int) Math.floor(scale * (bmp.getWidth() / ((float) bmp.getHeight())));
        } else if (bmp.getHeight() < bmp.getWidth()) {
            height = (int) Math.floor(scale * (bmp.getHeight() / ((float) bmp.getWidth())));
        }

        return Bitmap.createScaledBitmap(bmp, width, height, false);
    }

    public void initDenominationModels(Set<DenominationModel> denominationModels) {
        for (DenominationModel deno : denominationModels) {
            bitmaps.put(deno, scaleBitmap(BitmapFactory.decodeResource(getResources(),
                    deno.getImageResource()), 160));
            counts.put(deno, 0);
        }
        initialized = true;
    }

    public void setCountingTableListener(CountingTableListener countingTableListener) {
        this.countingTableListener = countingTableListener;
    }

    private void callEvent(DenominationModel deno, int newCount) {
        if (counts.containsKey(deno)) {
            int oldCount = counts.get(deno);
            if (oldCount != newCount && countingTableListener != null) {
                countingTableListener.onTableChange(deno, oldCount, newCount);
            }
        }
        counts.put(deno, newCount);
    }

    public void setDenominations(Iterator<DenominationModel> iterator, List<Integer> allocations) {
        for (int i = 0; i < allocations.size(); i++) {
            DenominationModel deno = iterator.next();
            int newCount = allocations.get(i);
            callEvent(deno, newCount);
        }
        invalidate();
    }

    public void addDenomination(DenominationModel deno) {
        int newCount;
        if (counts.containsKey(deno)) {
            newCount = counts.get(deno) + 1;
        } else {
            newCount = 1;
        }
        callEvent(deno, newCount);
        invalidate();
    }

    public void removeDenomination(DenominationModel deno) {
        if (counts.containsKey(deno)) {
            int value = counts.get(deno) - 1;
            if (value >= 0) {
                counts.put(deno, counts.get(deno));
                callEvent(deno, value);
                invalidate();
            }
        }
    }

}
