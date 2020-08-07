package org.myoralvillage.cashcalculatormodule.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnScrollChangedListener;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import org.myoralvillage.cashcalculatormodule.R;
import org.myoralvillage.cashcalculatormodule.models.AreaModel;
import org.myoralvillage.cashcalculatormodule.models.CurrencyModel;
import org.myoralvillage.cashcalculatormodule.models.DenominationModel;
import org.myoralvillage.cashcalculatormodule.services.BitmapService;
import org.myoralvillage.cashcalculatormodule.views.listeners.CurrencyScrollbarListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A view of the Scrollbar to monitor and render the display of the denominations in the scrollbar
 * as well as the scrollbar itself.
 *
 * @author Alexander Yang
 * @author Hamza Mahfooz
 * @author Lingjing Zou
 * @author Peter Panagiotis Roubatsis
 *
 * @see HorizontalScrollView
 */
public class CurrencyScrollbarView extends HorizontalScrollView {
    private LinearLayout linearLayout;
    private CurrencyScrollbarListener currencyScrollbarListener;
    private CurrencyModel currCurrency;
    private ScrollbarDenominationsView denominationsView;
    /**
     * Retrieves the Currency model associated with this view.
     *
     * @return the currency model of this view.
     */
    public CurrencyModel getCurrency() {
        return this.currCurrency;
    }

    /**
     * Constructs a <code>CurrencyScrollbarView</code> in the given Android context.
     *
     * @param context The context of the application.
     */
    public CurrencyScrollbarView(Context context) {
        super(context);
        initialize();
    }

    /**
     * Constructs a <code>CurrencyScrollbarView</code> in the given Android context with the given
     * attributes.
     *
     * @param context the context of the application.
     * @param attrs A collection of attributes found in the xml layout.
     */
    public CurrencyScrollbarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    /**
     * Initializes this view and its variables.
     */
    private void initialize() {
        currencyScrollbarListener = null;
        setBackgroundResource(R.drawable.scrollbar_background);

        linearLayout = new LinearLayout(getContext());
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT
        ));
        linearLayout.setGravity(Gravity.BOTTOM);

        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        addView(linearLayout);

        denominationsView = new ScrollbarDenominationsView(getContext());
        denominationsView.setOnTouchListener(new TapDetector(getResources().getDisplayMetrics().ydpi) {


            @Override
            public void onTap(MotionEvent e) {
                int index = denominationsView.getAreaModel().getBoxIndexFromPoint(e.getX(), e.getY());
                Iterator<DenominationModel> denominationModelIterator = currCurrency.getDenominations().iterator();
                DenominationModel current = null;

                for(int i = 0; i <= index; i++)
                    current = denominationModelIterator.next();

                if (index >= 0) currencyScrollbarListener.onTapDenomination(current);
            }

            @Override
            void onVerticalSwipe() {
                if (currencyScrollbarListener != null)
                    currencyScrollbarListener.onVerticalSwipe();
            }
        });

        linearLayout.addView(denominationsView);

        post(() -> {
            int view = getWidth()/2;
            int scrollbar = linearLayout.getWidth()/2;
            scrollTo(scrollbar - view, 0);
        });
    }

    /**
     * Sets the listener to allow handling of the scrollbar's events.
     *
     * @param currencyScrollbarListener the listener of this view.
     * @see CurrencyScrollbarListener
     */
    public void setCurrencyScrollbarListener(CurrencyScrollbarListener currencyScrollbarListener) {
        this.currencyScrollbarListener = currencyScrollbarListener;
    }

    /**
     * Sets the currency model of this view based off the currency code.
     *
     * @param currencyCode A 3-letter String used to identify the currency.
     * @see java.util.Currency
     */
    public void setCurrency(String currencyCode) {
        CurrencyModel currency = CurrencyModel.loadCurrencyModel(
                currencyCode, getResources(), getContext());

        this.currCurrency = currency;

        float inchesToPixels = getResources().getDisplayMetrics().ydpi;

        for (DenominationModel denomination : currency.getDenominations()) {
            denominationsView.addBitmap(BitmapFactory.decodeResource(getResources(), denomination.getImageResource()),
                    denomination.getScaleFactor(), (int) (denomination.getVerticalOffsetInInches() * inchesToPixels));
        }
    }

    /**
     * A view of the CurrencyScrollbar to monitor and render the display of the denominations.
     */
    private static class ScrollbarDenominationsView extends View {
        /**
         * A constant variable to denote the offset of the denominations for a pleasant display.
         */
        private static final int PADDING = 8;

        /**
         * the bitmaps of the denominations.
         *
         * @see Bitmap
         */
        private List<Bitmap> bitmaps = new ArrayList<>();

        /**
         * An offset value from the top of the scrollbar for each denominations for a pleasant display.
         */
        private List<Integer> verticalOffsetsInPixels = new ArrayList<>();

        /**
         * the width of a denomination to the beginning of the view.
         */
        private int width = 0;

        /**
         * An area in which to draw the denominations.
         */
        private AreaModel areaModel = new AreaModel();

        /**
         * Assists in the scaling of the denominations.
         *
         * @see BitmapService
         */
        private BitmapService bitmapService = BitmapService.getInstance();

        /**
         * Constructs a <code>ScrollbarDenominationsView</code> in the given Android context.
         *
         * @param context the context of the application.
         */
        public ScrollbarDenominationsView(Context context) {
            super(context);
        }

        /**
         * This method performs the drawing on this view.
         *
         * @param canvas the area to draw.
         */
        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            areaModel.clearArea();

            int currentOffset = 0;
            for (int i = 0; i < bitmaps.size(); i++) {
                Bitmap bmp = bitmaps.get(i);
                Integer verticalOffset = verticalOffsetsInPixels.get(i);

                drawDenomination(bmp, canvas, currentOffset, verticalOffset);
                currentOffset += bmp.getWidth() + PADDING;
            }
        }

        /**
         * Retrieves the area model associated with this view.
         *
         * @return the area model of this view.
         */
        public AreaModel getAreaModel() {
            return areaModel;
        }

        /**
         * Draws the denomination.
         *
         * @param bmp the bitmap of this denomination.
         * @param canvas the area to draw the denomination.
         * @param currentOffset the width offset from the beginning of this view.
         * @param verticalOffset the height offset from the top of this view.
         */
        private void drawDenomination(Bitmap bmp, Canvas canvas, int currentOffset, int verticalOffset) {
            if (bmp.getHeight() > getHeight()) {
                areaModel.addBox(new AreaModel.Box(currentOffset, verticalOffset, bmp.getWidth(), bmp.getHeight()));
                canvas.drawBitmap(bmp, currentOffset, verticalOffset, null);
            } else {
                int minY = getHeight() - bmp.getHeight() + verticalOffset;
                areaModel.addBox(new AreaModel.Box(currentOffset, minY, bmp.getWidth(), bmp.getHeight()));
                canvas.drawBitmap(bmp, currentOffset, minY, null);
            }
        }

        /**
         * Inserts a scales bitmap to the list of bitmaps as well as to the vertical offset list.
         *
         * @param bmp the bitmap to be added.
         * @param scaleFactor a float variable to scale the bitmap.
         * @param verticalOffsetInPixels the height offset from the top of this view.
         */
        public void addBitmap(Bitmap bmp, float scaleFactor, int verticalOffsetInPixels) {
            Bitmap scaledBitmap = bitmapService.resizeCashBitmap(bmp, getContext(), scaleFactor);

            width += scaledBitmap.getWidth() + PADDING;
            setLayoutParams(new LinearLayout.LayoutParams(width, ViewGroup.LayoutParams.MATCH_PARENT));

            verticalOffsetsInPixels.add(verticalOffsetInPixels);
            bitmaps.add(scaledBitmap);
        }

        /**
         * Calls this view <code>onClickListener</code>
         *
         * @return True if there was an assigned OnClickListener that was called; false otherwise.
         *
         * @see android.view.View.OnClickListener
         */
        @Override
        public boolean performClick() {
            return super.performClick();
        }
    }

    private static abstract class TapDetector implements OnTouchListener{
        private static final long MAX_DURATION = 250;
        private static final float MIN_SWIPE_DISTANCE_IN_INCHES = 0.2f;
        private float downX, downY;
        private float ydpi;

        TapDetector(float ydpi) {
            this.ydpi = ydpi;
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                downX = event.getX();
                downY = event.getY();
                return true;
            }
            else if (isSwipe(event)) {
                onVerticalSwipe();
                return true;
            }
            else if (isUpAndInTimespan(event)) {
                onTap(event);
                return true;
            }

            return false;
        }

        private boolean isUpAndInTimespan(MotionEvent event) {
            return (event.getEventTime() - event.getDownTime()) <= MAX_DURATION && event.getAction() == MotionEvent.ACTION_UP;
        }

        private boolean isSwipe(MotionEvent event) {
            return isUpAndInTimespan(event) &&
                    Math.abs(event.getY() - downY) > 2 * Math.abs(event.getX() - downX) &&
                    Math.abs(event.getY() - downY) > (MIN_SWIPE_DISTANCE_IN_INCHES * ydpi);
        }

        abstract void onTap(MotionEvent e);
        abstract void onVerticalSwipe();
    }
}