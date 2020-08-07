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
import org.myoralvillage.cashcalculatormodule.views.listeners.CountingTableSurfaceListener;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * A surface view of the CountingTable to monitor and render the display of the denominations.
 *
 * @author Peter Panagiotis Roubatsis
 * @author Zhipeng Zhou
 *
 * @see CountingTableView
 */
public class CountingTableSurfaceView extends View {
    /**
     * A constant variable to denote the maximum amount of denominations that can be stacked before
     * the display changes to a bundle version.
     */
    private static final int THRESHOLD_NUM = 4;
    private static final float STACKED_DENOMINATION_OFFSET_IN_INCHES = 0.05f;
    private static final int OFFSET_STROKE_RATE = 15;
    private CountingTableSurfaceListener countingTableSurfaceListener;
    private Map<DenominationModel, Integer> counts;
    private Map<DenominationModel, Bitmap> bitmaps;
    private Map<DenominationModel, AreaModel> areas;

    /**
     * A boolean to monitor if the denomination model is initialized.
     */
    private boolean initialized;

    private boolean isNegative;

    private BitmapService bitmapService;

    /**
     * This holds the style and color information about how to draw the denominations.
     *
     * @see Paint
     */
    private Paint denoNumber;
    private int maxDenominationHeight = Integer.MIN_VALUE;

    /**
     * Constructs a <code>CountingTableSurfaceView</code> in the given Android context.
     *
     * @param context The context of the application.
     */
    public CountingTableSurfaceView(Context context) {
        super(context);
        init();
    }

    /**
     * Constructs a <code>CountingTableSurfaceView</code> in the given Android context with the given
     * attributes.
     *
     * @param context the context of the application.
     * @param attrs A collection of attributes found in the xml layout.
     */
    public CountingTableSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /**
     * Initializes the variables of this view.
     */
    private void init() {
        bitmapService = BitmapService.getInstance();
        counts = new TreeMap<>((o1, o2) -> o2.compareTo(o1));
        bitmaps = new HashMap<>();
        areas = new HashMap<>();
        countingTableSurfaceListener = null;
        initialized = false;
        isNegative = false;
        denoNumber = new Paint();
    }

    /**
     * This method performs the drawing on this view.
     *
     * @param canvas the area to draw.
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!initialized) {
            return;
        }

        float numSize = getWidth() / 12;
        Typeface font = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD_ITALIC);
        denoNumber.setTypeface(font);

        denoNumber.setTextSize(numSize);
        denoNumber.setStrokeWidth((float) numSize / OFFSET_STROKE_RATE);
        denoNumber.setAntiAlias(true);

        int currentPosition = 0;

        float verticalPaddingInInches = STACKED_DENOMINATION_OFFSET_IN_INCHES * (THRESHOLD_NUM - 1);
        int verticalPaddingInPixels = (int) (verticalPaddingInInches * getResources().getDisplayMetrics().ydpi);
        int rowHeight = maxDenominationHeight + verticalPaddingInPixels;

        for (Map.Entry<DenominationModel, Integer> entry : counts.entrySet()) {
            Bitmap bmp = bitmaps.get(entry.getKey());
            int denominationCount = entry.getValue();
            float horizontalPaddingInInches = STACKED_DENOMINATION_OFFSET_IN_INCHES * (denominationCount <= THRESHOLD_NUM ? (denominationCount - 1) : 1);
            int horizontalPixelPadding = (int) (horizontalPaddingInInches * getResources().getDisplayMetrics().xdpi);
            if (isNegative) bmp = invertBitmap(bmp);

            AreaModel areaModel = areas.get(entry.getKey());
            areaModel.clearArea();

            int positionX = currentPosition % getWidth();
            if ((positionX + bmp.getWidth() + horizontalPixelPadding) > getWidth()) {
                currentPosition = getWidth();
                positionX = currentPosition % getWidth();
            }

            int currencyRow = currentPosition / getWidth();

            if (denominationCount == 0) {
                continue;
            } else if (denominationCount > THRESHOLD_NUM) {
                drawDenoNum(canvas, denominationCount, bmp, positionX, currencyRow * rowHeight, areaModel, numSize);
            } else {
                drawDeno(canvas, denominationCount, bmp, positionX, currencyRow * rowHeight, areaModel);
            }

            currentPosition += bmp.getWidth() + horizontalPixelPadding;
        }
    }

    /**
     * Draws the bitmap of the denominations to this view when the denominations are stacked.
     *
     * @param canvas the area to draw the bitmap.
     * @param num the number of the specific denomination.
     * @param bmp the bitmap for the specific denomination.
     * @param originX the x coordinate of the initial x position to draw the denomination.
     * @param originY the y coordinate of the initial y position to draw the denomination
     * @param areaModel the area in which to draw the denominations.
     *
     * @see Canvas
     * @see AreaModel
     */
    private void drawDeno(Canvas canvas, int num, Bitmap bmp, int originX, int originY,
                          AreaModel areaModel) {

        int offsetX = (int)(STACKED_DENOMINATION_OFFSET_IN_INCHES * getResources().getDisplayMetrics().xdpi);
        int offsetY = (int)(STACKED_DENOMINATION_OFFSET_IN_INCHES * getResources().getDisplayMetrics().ydpi);

        for (int i = 0; i < num; i++) {
            int localX = originX + offsetX * i;
            int localY = originY + offsetY * i;
            canvas.drawBitmap(bmp, localX, localY, null);
        }

        areaModel.addBox(new AreaModel.Box(originX, originY, bmp.getWidth(), bmp.getHeight()));
    }

    /**
     * Draws the bitmap of the denominations to this view when the denominations are greater than
     * <code>THRESHOLD_NUM</code>. A number is drawn on top of a single denomination when it surpasses the threshold.
     *
     * @param canvas the area to draw the bitmap.
     * @param num the number of the specific denomination.
     * @param bmp the bitmap for the specific denomination.
     * @param originX the x coordinate of the initial x position to draw the denomination.
     * @param originY the y coordinate of the initial y position to draw the denomination
     * @param areaModel the area in which to draw the denominations.
     * @param numSize the size area for a denomination.
     *
     * @see Canvas
     * @see AreaModel
     */
    private void drawDenoNum(Canvas canvas, int num, Bitmap bmp, int originX, int originY,
                             AreaModel areaModel, float numSize) {
        areaModel.addBox(new AreaModel.Box(originX, originY, bmp.getWidth(), bmp.getHeight()));
        canvas.drawBitmap(bmp, originX, originY, null);
        int textPositionX;

        if (numSize > (float)(bmp.getWidth() / 2)) {
            textPositionX = originX + (int)(STACKED_DENOMINATION_OFFSET_IN_INCHES / 2.0f * getResources().getDisplayMetrics().xdpi);
        } else {
            textPositionX = originX + (int)(bmp.getWidth() / 3.0f);
        }
        int textPositionY = originY + (int)(bmp.getHeight() / 1.5f);

        if (num >= 100){
            denoNumber.setTextSize(numSize / 2);
            denoNumber.setStrokeWidth((float) numSize / 23);
        }else{
            if (num > 9){
                denoNumber.setTextSize(numSize * 2 / 3);
                denoNumber.setStrokeWidth((float) numSize / 19);
            }else{
                denoNumber.setTextSize(numSize);
                denoNumber.setStrokeWidth((float) numSize / OFFSET_STROKE_RATE);
            }
        }
        denoNumber.setStyle(Paint.Style.FILL);
        denoNumber.setColor(Color.WHITE);
        canvas.drawText(Integer.toString(num), textPositionX, textPositionY, denoNumber);
        denoNumber.setStyle(Paint.Style.STROKE);
        denoNumber.setColor(Color.BLACK);
        canvas.drawText(Integer.toString(num), textPositionX, textPositionY, denoNumber);
    }

    /**
     * Inverts the colors of the denomination to indicate that the value is less than zero.
     *
     * @param bmp the bitmap to be changed.
     * @return the new bitmap.
     */
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

    /**
     * Resize the bitmap by a certain scale factor.
     *
     * @param bmp the bitmap to be scaled.
     * @param scaleFactor a float variable to scale the bitmap.
     * @return the scaled bitmap.
     */
    private Bitmap scaleBitmap(Bitmap bmp, float scaleFactor) {
        return bitmapService.resizeCashBitmap(bmp, getContext(), scaleFactor);
    }

    /**
     * Initialize the view with the denominations that will be used.
     *
     * @param denominationModels the set of denominations being used by this view.
     * @see DenominationModel
     */
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

    /**
     * Sets the listener of this view.
     *
     * @param countingTableSurfaceListener the listener of this view.
     * @see CountingTableSurfaceListener
     */
    public void setCountingTableSurfaceListener(CountingTableSurfaceListener countingTableSurfaceListener) {
        this.countingTableSurfaceListener = countingTableSurfaceListener;
    }

    /**
     * Invoked when a denomination is removed from this view.
     *
     * @param deno the denomination being affected.
     * @param newCount the number of denomination items whe this event is called.
     */
    private void callEvent(DenominationModel deno, int newCount) {
        if (counts.containsKey(deno)) {
            int oldCount = counts.get(deno);
            if (oldCount > newCount && countingTableSurfaceListener != null) {
                countingTableSurfaceListener.onTableRemove(deno, oldCount, newCount);
            }
        }
        counts.put(deno, newCount);
    }

    /**
     * Sets the allocation of the denomination to be displayed.
     *
     * @param iterator the denomination model to insert.
     * @param allocations the amount of that denomination model to insert
     * @param value the total value displayed on this view.
     */
    public void setDenominations(Iterator<DenominationModel> iterator, List<Integer> allocations,
                                 BigDecimal value) {
        for (int i = 0; i < allocations.size(); i++) {
            counts.put(iterator.next(), allocations.get(i));
        }

        isNegative = value.compareTo(BigDecimal.ZERO) < 0;
        invalidate();
    }

    /**
     * Removes a denomination model from this view.
     *
     * @param deno the denomination model to be removed.
     */
    public boolean removeDenomination(DenominationModel deno) {
        boolean successful = false;
        if (counts.containsKey(deno)) {
            int value = counts.get(deno) - 1;
            if (value >= 0) {
                counts.put(deno, counts.get(deno));
                callEvent(deno, value);
                invalidate();
                successful = true;
            }
        }
        return successful;
    }

    /**
     * Invoked when an on touch down motion event is applied for a certain period of time.
     *
     * @param x the x coordinate of this motion event.
     * @param y the y coordinate of this motion event.
     */
    public void handleLongPress(float x, float y) {
        for (Map.Entry<DenominationModel, AreaModel> entry : areas.entrySet()) {
            AreaModel.Box box = entry.getValue().getBoxFromPoint(x, y);
            if (box != null) {
                int color = bitmaps.get(entry.getKey()).
                        getPixel(Math.round(x - box.getX()), Math.round(y - box.getY()));
                if (color != Color.TRANSPARENT) {
                    removeDenomination(entry.getKey());
                    entry.getValue().removeLastBox();
                }
                break;
            }
        }
    }


    @Override
    public boolean performClick() {
        return super.performClick();
    }
}
