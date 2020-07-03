package org.myoralvillage.cashcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import org.myoralvillage.cashcalculatormodule.fragments.CashCalculatorFragment;

public class TutorialActivity extends AppCompatActivity {
    private CashCalculatorFragment fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_tutorial);
        fragment = (CashCalculatorFragment) getSupportFragmentManager()
                .findFragmentById(R.id.TutorialFragment);

        if (fragment != null) {
            fragment.initialize("PKR");
        }
        animate();
    }
    protected void animate() {
        AnimatorSet set = new AnimatorSet();
        set.playSequentially(getScrollbarAnimation(300, 1000), getScrollbarAnimation(700, 1000), getScrollbarAnimation(100, 1000));
        set.start();
    }
    protected ObjectAnimator getScrollbarAnimation(int xValue, int duration) {
        ObjectAnimator animator = ObjectAnimator.ofInt(fragment.currencyScrollbarView, "scrollX", xValue);
        animator.setDuration(duration);
        return animator;
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
            return true;
    }
}
