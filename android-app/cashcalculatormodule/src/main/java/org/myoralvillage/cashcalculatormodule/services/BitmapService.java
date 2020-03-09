package org.myoralvillage.cashcalculatormodule.services;

import android.content.res.Resources;
import android.graphics.Bitmap;

public class BitmapService {
    private static BitmapService bitmapService = null;
    private static final float CURRENCY_WIDTH_IN_INCHES = 0.75f;

    public Bitmap resizeCashBitmap(Bitmap bitmap, Resources resources, float scaleFactor) {
        int targetWidth = (int)(resources.getDisplayMetrics().xdpi * CURRENCY_WIDTH_IN_INCHES);

        float scale = (float) targetWidth / bitmap.getWidth();
        int targetHeight = (int) (bitmap.getHeight() * scale);

        targetWidth = (int) (targetWidth * scaleFactor);
        targetHeight = (int) (targetHeight * scaleFactor);

        return Bitmap.createScaledBitmap(bitmap, targetWidth, targetHeight, false);
    }

    public static BitmapService getInstance() {
        if (bitmapService == null) bitmapService = new BitmapService();
        return bitmapService;
    }
}
