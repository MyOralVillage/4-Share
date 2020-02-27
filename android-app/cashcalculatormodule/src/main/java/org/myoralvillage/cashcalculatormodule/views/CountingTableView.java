package org.myoralvillage.cashcalculatormodule.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

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

    private static final int OFFSET_VALUE_DENO = 50;
    private static final int OFFSET_VALUE_NUM = 50;

    //Value to scale the initial denominations location
    private static final float INITIAL_OFFSET_LEFT = (float) 0.005;
    private static final float INITIAL_OFFSET_TOP = (float) 0.04;

    //Value to scale the size of the denominations
    private static final float DENOM_SIZE = (float) 0.2;

    private CountingTableListener countingTableListener;
    private Map<DenominationModel, Integer> counts;
    private Map<DenominationModel, Bitmap> bitmaps;
    private boolean initialized;
    private boolean isNegative = false;
    private Paint denoNumber = new Paint();

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

        Typeface font = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD_ITALIC);
        denoNumber.setTypeface(font);
        denoNumber.setTextSize(160);
        denoNumber.setStrokeWidth(10);
        int width = getWidth();
        int columnNumber = bitmaps.size() / 2 + 1;
        int cellWidth = width / columnNumber;
        int top = (int) Math.floor((getHeight() * INITIAL_OFFSET_TOP) / 10.0) * 10;
        int columnIndex, cellIndex = 0;
        int locateX, locateY;

        //Variable used to keep track of top or bottom level
        int level = 0;

        for (Map.Entry<DenominationModel, Integer> entry : counts.entrySet()) {
            columnIndex = cellIndex % columnNumber;
            Bitmap bmp = bitmaps.get(entry.getKey());
            if (isNegative) bmp = invertBitmap(bmp);

            if (entry.getValue() == 0) {
                continue;
            }

            if (entry.getValue() > 4) {
                locateX = columnIndex * cellWidth + OFFSET_VALUE_DENO;
                locateY = (top * 3) + level;
                canvas.drawBitmap(bmp, locateX, locateY, null);
                locateX = columnIndex * cellWidth + OFFSET_VALUE_NUM;
                denoNumber.setStyle(Paint.Style.FILL);
                denoNumber.setColor(Color.WHITE);
                canvas.drawText(Integer.toString(entry.getValue()), locateX, locateY + getHeight() / 4, denoNumber);
                denoNumber.setStyle(Paint.Style.STROKE);
                denoNumber.setColor(Color.BLACK);
                canvas.drawText(Integer.toString(entry.getValue()), locateX, locateY + getHeight() / 4, denoNumber);
            } else {
                for (int i = 1; i < entry.getValue() + 1; i++) {
                    locateX = columnIndex * cellWidth + 20 * i;
                    locateY = (top * i) + level;
                    canvas.drawBitmap(bmp, locateX, locateY, null);
                }
            }

            cellIndex += 1;

            if (cellIndex + 1 > columnNumber) {
                level = (int) (getHeight() / 2.0);
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

    public void initDenominationModels(Set<DenominationModel> denominationModels, int width, int height) {
        for (DenominationModel deno : denominationModels) {
            bitmaps.put(deno, scaleBitmap(BitmapFactory.decodeResource(getResources(),
                    deno.getImageResourceFolded()), width, height));
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

    public void setDenominations(Iterator<DenominationModel> iterator, List<Integer> allocations, BigDecimal value) {
        for (int i = 0; i < allocations.size(); i++) {
            DenominationModel deno = iterator.next();
            int newCount = allocations.get(i);
            callEvent(deno, newCount);
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

    @Override
    public boolean performClick() {
        return super.performClick();
    }
}
