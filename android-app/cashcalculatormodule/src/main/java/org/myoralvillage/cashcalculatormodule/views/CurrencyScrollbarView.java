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
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import org.myoralvillage.cashcalculatormodule.R;
import org.myoralvillage.cashcalculatormodule.models.AreaModel;
import org.myoralvillage.cashcalculatormodule.models.CurrencyModel;
import org.myoralvillage.cashcalculatormodule.models.DenominationModel;
import org.myoralvillage.cashcalculatormodule.services.BitmapService;
import org.myoralvillage.cashcalculatormodule.views.listeners.CurrencyScrollbarListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CurrencyScrollbarView extends HorizontalScrollView {
    private LinearLayout linearLayout;
    private CurrencyScrollbarListener currencyScrollbarListener;
    private CurrencyModel currCurrency;

    private ScrollbarDenominationsView denominationsView;

    public CurrencyModel getCurrency() {
        return this.currCurrency;
    }

    public CurrencyScrollbarView(Context context) {
        super(context);
        initialize();
    }

    public CurrencyScrollbarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

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
    }

    public void setCurrencyScrollbarListener(CurrencyScrollbarListener currencyScrollbarListener) {
        this.currencyScrollbarListener = currencyScrollbarListener;
    }

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

    private static class ScrollbarDenominationsView extends View {
        private static final int PADDING = 8;
        private List<Bitmap> bitmaps = new ArrayList<>();
        private List<Integer> verticalOffsetsInPixels = new ArrayList<>();

        private int width = 0;
        private AreaModel areaModel = new AreaModel();
        private BitmapService bitmapService = BitmapService.getInstance();

        public ScrollbarDenominationsView(Context context) {
            super(context);
        }

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

        public AreaModel getAreaModel() {
            return areaModel;
        }

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

        public void addBitmap(Bitmap bmp, float scaleFactor, int verticalOffsetInPixels) {
            Bitmap scaledBitmap = bitmapService.resizeCashBitmap(bmp, getContext(), scaleFactor);

            width += scaledBitmap.getWidth() + PADDING;
            setLayoutParams(new LinearLayout.LayoutParams(width, ViewGroup.LayoutParams.MATCH_PARENT));

            verticalOffsetsInPixels.add(verticalOffsetInPixels);
            bitmaps.add(scaledBitmap);
        }

        @Override
        public boolean performClick() {
            return super.performClick();
        }
    }

    private static abstract class TapDetector implements OnTouchListener {
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
