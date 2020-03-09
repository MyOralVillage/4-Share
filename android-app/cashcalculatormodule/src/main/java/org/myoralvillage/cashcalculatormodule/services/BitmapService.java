package org.myoralvillage.cashcalculatormodule.services;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.WindowManager;

public class BitmapService {
    private static BitmapService bitmapService = null;
    private static final float CURRENCY_WIDTH_IN_INCHES = 0.75f;
    private static final int SCREEN_FRACTION = 6;

    public Bitmap resizeCashBitmap(Bitmap bitmap, Context ctx, float scaleFactor) {
        int screenWidth = ((WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getWidth();
        int idealWidth = (int)(ctx.getResources().getDisplayMetrics().xdpi * CURRENCY_WIDTH_IN_INCHES);
        int maxWidth = screenWidth / SCREEN_FRACTION;
        int targetWidth = idealWidth < maxWidth ? idealWidth : maxWidth;

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
