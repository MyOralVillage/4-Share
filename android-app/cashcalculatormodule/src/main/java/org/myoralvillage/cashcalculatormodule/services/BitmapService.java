package org.myoralvillage.cashcalculatormodule.services;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.WindowManager;

/**
 * A service class used to assist in the scaling of the denomination images so that they appear
 * appropriately on a device's screen.
 *
 * @author Peter Panagiotis Roubatsis
 * @see Object
 */
public class BitmapService {
    /**
     * An instance of this class.
     */
    private static BitmapService bitmapService = null;

    /**
     * A constant variable to specify the scale of the currencies.
     */
    private static final float CURRENCY_WIDTH_IN_INCHES = 0.75f;

    /**
     * A constant variable used as an offset such that the denominations do not appear to be "coming
     * out of the screen".
     */
    private static final int SCREEN_FRACTION = 6;

    /**
     * Scales the denomination images based off the <code>scalefactor</code> specified.
     *
     * @param bitmap the bitmaps of the denominations.
     * @param ctx the context of this application.
     * @param scaleFactor A float variable used to resize the denominations.
     * @return A new bitmap, scaled by the scalefactor
     */
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

    /**
     * Returns the instance associated with this class. The first time this function is called will
     * create an instance of this service.
     *
     * @return the instance of this service class.
     */
    public static BitmapService getInstance() {
        if (bitmapService == null) bitmapService = new BitmapService();
        return bitmapService;
    }
}
