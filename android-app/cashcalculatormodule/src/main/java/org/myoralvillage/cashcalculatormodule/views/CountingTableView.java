package org.myoralvillage.cashcalculatormodule.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import org.myoralvillage.cashcalculatormodule.models.AreaModel;
import org.myoralvillage.cashcalculatormodule.models.DenominationModel;
import org.myoralvillage.cashcalculatormodule.views.listeners.CountingTableListener;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class CountingTableView extends View {

    private static final int OFFSET_VALUE = 50;

    //Value to scale the initial denominations location
    private static final float INITIAL_OFFSET_LEFT = (float) 0.005;
    private static final float INITIAL_OFFSET_TOP = (float) 0.04;

    //Value to scale the size of the denominations
    private static final float DENOM_SIZE = (float) 0.2;

    private CountingTableListener countingTableListener;
    private Map<DenominationModel, Integer> counts;
    private Map<DenominationModel, Bitmap> bitmaps;
    private Map<DenominationModel, AreaModel> areas;
    private boolean initialized;
    private boolean isNegative;

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
        areas = new HashMap<>();
        countingTableListener = null;
        initialized = false;
        isNegative = false;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!initialized) {
            return;
        }

        boolean first = true;
        int width = getWidth();
        for (Bitmap bmp : bitmaps.values()) {
            width -= bmp.getWidth();
        }
        int left = 20;
        int initialLeft = (int) Math.floor((getWidth() * INITIAL_OFFSET_LEFT) / 10.0) * 10;
        int cut =  (int) (width / ((float) counts.size()));
        int top = (int) Math.floor((getHeight() * INITIAL_OFFSET_TOP) / 10.0) * 10;

        //Variable used to keep track of top or bottom level
        int level = 0;

        for (Map.Entry<DenominationModel, Integer> entry : counts.entrySet()) {
            int sum_left = initialLeft;
            Bitmap bmp = bitmaps.get(entry.getKey());
            if (isNegative) bmp = invertBitmap(bmp);

            if (entry.getValue() == 0) {
                continue;
            }

            AreaModel areaModel = areas.get(entry.getKey());
            areaModel.clearArea();
            for (int i = 1; i < entry.getValue() + 1; i++) {
                int minX = left + 20 * i;
                int minY = (top * i) + level;
                areaModel.addBox(new AreaModel.Box(minX, minY, bmp.getWidth(), bmp.getHeight()));
                canvas.drawBitmap(bmp, minX, minY, null);
                sum_left += 20;
            }

            left += sum_left + cut + (OFFSET_VALUE) + bmp.getWidth();
            if (left >= (getWidth()- bmp.getWidth())){
                //Reached end of the screen width, so display below current bills
                level = (int) (getHeight() / 2.0);
                if (first){
                    left = (int) (getWidth() * 0.35);
                    first = false;
                }
                else{
                    left = initialLeft;
                }
            }
        }
    }

    private Bitmap invertBitmap(Bitmap bmp) {
        Bitmap output = bmp.copy(Bitmap.Config.ARGB_8888, true);

        for (int x = 0; x < bmp.getWidth(); x++) {
            for (int y = 0; y < bmp.getHeight(); y++) {
                int pixel = bmp.getPixel(x, y);
                output.setPixel(x, y, pixel ^ 0x00FFFFFF);
            }
        }

        return output;
    }

    private Bitmap scaleBitmap(Bitmap bmp,int widthApp, int heightApp) {
        int scale;
        int width, height;

        if (bmp.getHeight() > bmp.getWidth()) {
            scale = (int) Math.floor((widthApp * DENOM_SIZE) / 100.0) * 100;
            width = (int) Math.floor(scale * (bmp.getWidth() / ((float) bmp.getHeight())));
            height = scale;
        } else if (bmp.getHeight() < bmp.getWidth()) {
            scale = (int) Math.floor((heightApp * DENOM_SIZE) / 100.0) * 100;
            width = scale;
            height = (int) Math.floor(scale * (bmp.getHeight() / ((float) bmp.getWidth())));
        }
        else{
            scale = (int) Math.floor((widthApp * DENOM_SIZE) / 100.0) * 100;
            width = scale;
            height = scale;
        }
        return Bitmap.createScaledBitmap(bmp, width, height, false);
    }

    public void initDenominationModels(Set<DenominationModel> denominationModels,
                                       int width,int height) {
        for (DenominationModel deno : denominationModels) {
            bitmaps.put(deno, scaleBitmap(BitmapFactory.decodeResource(getResources(),
                    deno.getImageResourceFolded()), width, height));
            counts.put(deno, 0);
            areas.put(deno, new AreaModel());
        }
        initialized = true;
    }

    public void setCountingTableListener(CountingTableListener countingTableListener) {
        this.countingTableListener = countingTableListener;
    }

    private void callEvent(DenominationModel deno, int newCount) {
        if (counts.containsKey(deno)) {
            int oldCount = counts.get(deno);
            if (oldCount > newCount && countingTableListener != null) {
                countingTableListener.onTableRemove(deno, oldCount, newCount);
            }
        }
        counts.put(deno, newCount);
    }

    public void setDenominations(Iterator<DenominationModel> iterator, List<Integer> allocations,
                                 BigDecimal value) {
        for (int i = 0; i < allocations.size(); i++) {
            counts.put(iterator.next(), allocations.get(i));
        }

        isNegative = value.compareTo(BigDecimal.ZERO) < 0;
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

    public void handleLongPress(float x, float y) {
        for (Map.Entry<DenominationModel, AreaModel> entry : areas.entrySet()) {
            AreaModel.Box box = entry.getValue().getBoxFromPoint(x, y);
            if (box != null) {
                removeDenomination(entry.getKey());
                entry.getValue().removeLastBox();
                break;
            }
        }
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }
}
