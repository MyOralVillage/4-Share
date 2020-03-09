package org.myoralvillage.cashcalculatormodule.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

import org.myoralvillage.cashcalculatormodule.models.AreaModel;
import org.myoralvillage.cashcalculatormodule.models.DenominationModel;
import org.myoralvillage.cashcalculatormodule.services.BitmapService;
import org.myoralvillage.cashcalculatormodule.views.listeners.CountingTableListener;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class CountingTableView extends View {
    private static final int THRESHOLD_NUM = 4;
    private static final float OFFSET_PERCENTAGE = (float) 0.07;

    private CountingTableListener countingTableListener;
    private Map<DenominationModel, Integer> counts;
    private Map<DenominationModel, Bitmap> bitmaps;
    private Map<DenominationModel, AreaModel> areas;
    private boolean initialized;
    private boolean isNegative;
    private BitmapService bitmapService;
    private Paint denoNumber;

    private int maxDenominationHeight = Integer.MIN_VALUE;

    public CountingTableView(Context context) {
        super(context);
        init();
    }

    public CountingTableView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        bitmapService = BitmapService.getInstance();
        counts = new TreeMap<>((o1, o2) -> o2.compareTo(o1));
        bitmaps = new HashMap<>();
        areas = new HashMap<>();
        countingTableListener = null;
        initialized = false;
        isNegative = false;
        denoNumber = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!initialized) {
            return;
        }

        Typeface font = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD_ITALIC);
        denoNumber.setTypeface(font);
        denoNumber.setTextSize((float) getWidth() / 10);
        denoNumber.setStrokeWidth((float) getWidth() / 150);

        int currentPosition = 0;

        for (Map.Entry<DenominationModel, Integer> entry : counts.entrySet()) {
            Bitmap bmp = bitmaps.get(entry.getKey());
            if (isNegative) bmp = invertBitmap(bmp);

            AreaModel areaModel = areas.get(entry.getKey());
            areaModel.clearArea();

            int positionX = currentPosition % getWidth();
            if ((positionX + bmp.getWidth()) > getWidth()) {
                currentPosition = getWidth();
                positionX = currentPosition % getWidth();
            }

            int currencyRow = currentPosition / getWidth();

            if (entry.getValue() == 0) {
                continue;
            } else if (entry.getValue() > THRESHOLD_NUM) {
                drawDenoNum(canvas, entry.getValue(), bmp, positionX, currencyRow * maxDenominationHeight, areaModel);
            } else {
                drawDeno(canvas, entry.getValue(), bmp, positionX, currencyRow * maxDenominationHeight, areaModel);
            }

            currentPosition += bmp.getWidth();
        }
    }

    private void drawDeno(Canvas canvas, int num, Bitmap bmp, int originX, int originY,
                          AreaModel areaModel) {
        for (int i = 0; i < num; i++) {
            int localX = originX + (int)(getWidth() / 8 * OFFSET_PERCENTAGE) * i;
            int localY = originY + (int)(getHeight() / 2 * OFFSET_PERCENTAGE) * i;
            canvas.drawBitmap(bmp, localX, localY, null);
        }

        areaModel.addBox(new AreaModel.Box(originX, originY, bmp.getWidth(), bmp.getHeight()));
    }

    private void drawDenoNum(Canvas canvas, int num, Bitmap bmp, int originX, int originY,
                             AreaModel areaModel) {
        areaModel.addBox(new AreaModel.Box(originX, originY, bmp.getWidth(), bmp.getHeight()));
        canvas.drawBitmap(bmp, originX, originY, null);

        int textPositionX = originX + bmp.getWidth() / 2;
        int textPositionY = originY + bmp.getHeight() / 2;

        denoNumber.setStyle(Paint.Style.FILL);
        denoNumber.setColor(Color.WHITE);
        canvas.drawText(Integer.toString(num), textPositionX, textPositionY, denoNumber);
        denoNumber.setStyle(Paint.Style.STROKE);
        denoNumber.setColor(Color.BLACK);
        canvas.drawText(Integer.toString(num), textPositionX, textPositionY, denoNumber);
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


    private Bitmap scaleBitmap(Bitmap bmp, float scaleFactor) {
        return bitmapService.resizeCashBitmap(bmp, getContext(), scaleFactor);
    }

    public void initDenominationModels(Set<DenominationModel> denominationModels) {
        for (DenominationModel deno : denominationModels) {
            Bitmap scaledBitmap = scaleBitmap(BitmapFactory.decodeResource(getResources(),
                    deno.getImageResourceFolded()), deno.getScaleFactor());
            bitmaps.put(deno, scaledBitmap);
            counts.put(deno, 0);
            areas.put(deno, new AreaModel());

            if (scaledBitmap.getHeight() > maxDenominationHeight)
                maxDenominationHeight = scaledBitmap.getHeight();
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
