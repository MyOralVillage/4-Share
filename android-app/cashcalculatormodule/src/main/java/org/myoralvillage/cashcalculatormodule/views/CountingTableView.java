package org.myoralvillage.cashcalculatormodule.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import org.myoralvillage.cashcalculatormodule.models.DenominationModel;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class CountingTableView extends View {

    private Map<DenominationModel, Integer> counts;
    private Map<DenominationModel, Bitmap> bitmaps;

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
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width = getWidth();
        for (Bitmap bmp : bitmaps.values()) {
            width -= bmp.getWidth();
        }
        int left = 20;
        int cut =  (int) (width / ((float) counts.size()));
        int top = 50;
        for (Map.Entry<DenominationModel, Integer> entry : counts.entrySet()) {
            Bitmap bmp = getBitmap(entry.getKey());
            for (int i = 1; i < entry.getValue() + 1; i++) {
                canvas.drawBitmap(bmp, left + 20 * i, top * i, null);
            }
            left += cut + bmp.getWidth();
        }
        super.onDraw(canvas);
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

    public Bitmap getBitmap(DenominationModel deno) {
        if (!bitmaps.containsKey(deno)) {
            Bitmap bmp = scaleBitmap(BitmapFactory.decodeResource(getResources(),
                    deno.getImageResource()), 160);

            bitmaps.put(deno, bmp);
            return bmp;
        }

        return bitmaps.get(deno);
    }

    public void setDenominations(Iterator<DenominationModel> iterator, List<Integer> allocations) {
        for (int i = 0; i < allocations.size(); i++) {
            DenominationModel deno = iterator.next();
            counts.put(deno, allocations.get(i));
            getBitmap(deno);
        }
        invalidate();
    }

    public void addDenomination(DenominationModel deno) {
        if (counts.containsKey(deno)) {
            counts.put(deno, counts.get(deno) + 1);
        } else {
            counts.put(deno, 1);
        }
        invalidate();
    }

    public void removeDenomination(DenominationModel deno) {
        if (counts.containsKey(deno)) {
            int value = counts.get(deno) - 1;
            if (value >= 0) {
                counts.put(deno, counts.get(deno));
                invalidate();
            }
        }
    }

}
