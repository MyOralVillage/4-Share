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
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import org.myoralvillage.cashcalculatormodule.R;
import org.myoralvillage.cashcalculatormodule.models.AreaModel;
import org.myoralvillage.cashcalculatormodule.models.CurrencyModel;
import org.myoralvillage.cashcalculatormodule.models.DenominationModel;
import org.myoralvillage.cashcalculatormodule.services.BitmapService;
import org.myoralvillage.cashcalculatormodule.views.listeners.CurrencyTapListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CurrencyScrollbarView extends HorizontalScrollView {
    private LinearLayout linearLayout;
    private CurrencyTapListener currencyTapListener;
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
        currencyTapListener = null;
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
        denominationsView.setOnTouchListener(new TapDetector() {
            @Override
            public void onTap(MotionEvent e) {
                int index = denominationsView.getAreaModel().getBoxIndexFromPoint(e.getX(), e.getY());
                Iterator<DenominationModel> denominationModelIterator = currCurrency.getDenominations().iterator();
                DenominationModel current = null;

                for(int i = 0; i <= index; i++)
                    current = denominationModelIterator.next();

                if (index >= 0) currencyTapListener.onTapDenomination(current);
            }
        });

        linearLayout.addView(denominationsView);
    }

    public void setCurrencyTapListener(CurrencyTapListener currencyTapListener) {
        this.currencyTapListener = currencyTapListener;
    }

    public void setCurrency(String currencyCode) {
        CurrencyModel currency = CurrencyModel.loadCurrencyModel(
                currencyCode, getResources(), getContext());

        this.currCurrency = currency;

        for (DenominationModel denomination : currency.getDenominations()) {
            denominationsView.addBitmap(BitmapFactory.decodeResource(getResources(), denomination.getImageResource()),
                    denomination.getScaleFactor());
        }
    }

    private static class ScrollbarDenominationsView extends View {
        private static final int PADDING = 8;
        private List<Bitmap> bitmaps = new ArrayList<>();
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
            for (Bitmap bmp : bitmaps) {
                drawDenomination(bmp, canvas, currentOffset);
                currentOffset += bmp.getWidth() + PADDING;
            }
        }

        public AreaModel getAreaModel() {
            return areaModel;
        }

        private void drawDenomination(Bitmap bmp, Canvas canvas, int currentOffset) {
            if (bmp.getHeight() > getHeight()) {
                areaModel.addBox(new AreaModel.Box(currentOffset, 0, bmp.getWidth(), bmp.getHeight()));
                canvas.drawBitmap(bmp, currentOffset, 0, null);
            } else {
                areaModel.addBox(new AreaModel.Box(currentOffset, getHeight() - bmp.getHeight(), bmp.getWidth(), bmp.getHeight()));
                canvas.drawBitmap(bmp, currentOffset, getHeight() - bmp.getHeight(), null);
            }
        }

        public void addBitmap(Bitmap bmp, float scaleFactor) {
            int screenWidth = ((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getWidth();
            Bitmap scaledBitmap = bitmapService.resizeCashBitmap(bmp, getResources(), scaleFactor, screenWidth);

            width += scaledBitmap.getWidth() + PADDING;
            setLayoutParams(new LinearLayout.LayoutParams(width, ViewGroup.LayoutParams.MATCH_PARENT));

            bitmaps.add(scaledBitmap);
        }

        @Override
        public boolean performClick() {
            return super.performClick();
        }
    }

    private static abstract class TapDetector implements OnTouchListener {
        private static final long MAX_DURATION = 250;

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if ((event.getEventTime() - event.getDownTime()) <= MAX_DURATION && event.getAction() == MotionEvent.ACTION_UP)
                onTap(event);

            return true;
        }

        abstract void onTap(MotionEvent e);
    }
}
