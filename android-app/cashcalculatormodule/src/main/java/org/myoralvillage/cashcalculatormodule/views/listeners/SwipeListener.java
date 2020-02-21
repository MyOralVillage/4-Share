package org.myoralvillage.cashcalculatormodule.views.listeners;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class SwipeListener implements View.OnTouchListener {
    private GestureDetector gestureDetector;
    public SwipeListener(Context c) {
        gestureDetector = new GestureDetector(c, new GestureListener());
    }
    public boolean onTouch(final View view, final MotionEvent motionEvent) {
        return gestureDetector.onTouchEvent(motionEvent);
    }
    private final class GestureListener extends GestureDetector.SimpleOnGestureListener {
        private static final int THRESHOLD = 100;
        private static final int THRESHOLD_VELOCITY = 100;

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onFling(MotionEvent downEvent, MotionEvent moveEvent, float velocityX, float velocityY) {
            boolean result = false;
            try {
                float diffY = moveEvent.getY() - downEvent.getY();
                float diffX = moveEvent.getX() - downEvent.getX();
                if (Math.abs(diffX) > Math.abs(diffY)) {
                    //Swipe Left or Right
                    if (Math.abs(diffX) > THRESHOLD && Math.abs(velocityX) > THRESHOLD_VELOCITY) {
                        if (diffX > 0) {
                            //Swipe Right
                            swipeRight();
                        } else {
                            //Swipe Left
                            swipeLeft();
                        }
                        result = true;
                    }
                }
                else {
                    //Swipe up or down
                    if (Math.abs(diffY) > THRESHOLD && Math.abs(velocityY) > THRESHOLD_VELOCITY) {
                        if (diffY > 0) {
                            //Swipe down
                            swipeDown();
                        } else {
                            //Swipe up
                            swipeUp();
                        }
                        result = true;
                    }
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }
    }

    public void swipeUp() {
    }
    public void swipeDown() {
    }
    public void swipeLeft() {
    }
    public void swipeRight() {
    }
}