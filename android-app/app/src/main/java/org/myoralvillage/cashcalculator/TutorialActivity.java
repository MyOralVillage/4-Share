package org.myoralvillage.cashcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import org.myoralvillage.cashcalculatormodule.fragments.CashCalculatorFragment;
import org.myoralvillage.cashcalculatormodule.models.CurrencyModel;
import org.myoralvillage.cashcalculatormodule.models.DenominationModel;
import org.myoralvillage.cashcalculatormodule.views.CountingTableView;
import org.myoralvillage.cashcalculatormodule.views.CurrencyScrollbarView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class TutorialActivity extends AppCompatActivity {
    private CashCalculatorFragment fragment;
    private CurrencyScrollbarView currencyScrollbar;
    private CurrencyModel currency;
    private ArrayList<Animator> animations;
    private CountingTableView countingTable;
    private List<Integer> horizontalOffsets;
    private List<Integer> verticalOffsets;
    private int height;
    private int width;
    private int[]fingerLocation;
    private ImageView finger;
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
        currencyScrollbar = fragment.getCurrencyScrollbarView();
        currency = currencyScrollbar.getCurrency();
        countingTable = fragment.getCountingTableView();
        horizontalOffsets = currencyScrollbar.getHorizontalOffsetsInPixels();
        verticalOffsets = currencyScrollbar.getVerticalOffsetsInPixels();
        animations = new ArrayList<>();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;
        fingerLocation = new int[2];
        finger = findViewById(R.id.finger);
        animate();
    }
    private DenominationModel getDenomination(int index) {
        Iterator<DenominationModel> denominationModelIterator = currency.getDenominations().iterator();
        DenominationModel current = null;
        for (int i = 0; i <= index; i++)
            current = denominationModelIterator.next();
        return current;
    }
    private void animate() {
        switch (getIntent().getIntExtra("animationStage", 0)) {
            case 0:
                scrollScrollbar(1500, 1000, 2000);
                tapFinger(100, 100, 1000, 100);
                clickOnDenomination(currency.getDenominations().size() - 1, 3500);
                scrollScrollbar(0, 4000, 2000);
                clickOnDenomination(1, 6500);
                changeToAddition(7000);
                break;
            case 1:
                scrollScrollbar(500, 1000, 2000);
                clickOnDenomination(currency.getDenominations().size() - 2, 3500);
                break;
        }
        AnimatorSet set = new AnimatorSet();
        set.playTogether(animations);
        set.start();
    }
    private void clickOnDenomination(int denominationIndex, int time) {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                currencyScrollbar.getCurrencyScrollbarListener()
                        .onTapDenomination(getDenomination(denominationIndex));
            }
        }, time);
    }
    private void scrollScrollbar(int xValue, int time, int duration) {
        ObjectAnimator animator = ObjectAnimator.ofInt(fragment.getCurrencyScrollbarView(), "scrollX", xValue);
        animator.setDuration(duration);
        animator.setStartDelay(time);
        animations.add(animator);
    }
    private void changeToAddition(int time) {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                countingTable.getListener().onSwipeAddition();
            }
        }, time);
    }
    private void tapFinger(int x, int y, int time, int duration) {
        if (duration < 600) duration = 600;
        finger.getLocationOnScreen(fingerLocation);
        int dX = x - fingerLocation[0];
        int dY = y - fingerLocation[1];
        AnimatorSet as = new AnimatorSet();
        ObjectAnimator xMove = getXAnimation(finger, dX, 0);
        ObjectAnimator yMove = getYAnimation(finger, dY, 0);
        ObjectAnimator fadeIn = getFadeIn(finger, 300);
        ObjectAnimator fadeOut = getFadeOut(finger, 300);
        fadeOut.setStartDelay((duration - 300));
        as.playTogether(xMove, yMove, fadeIn, fadeOut);
        as.setStartDelay(time);
        animations.add(as);
    }
    private ObjectAnimator getXAnimation(View view, float dX, int duration) {
        ObjectAnimator animation = ObjectAnimator.ofFloat(view, "translationX", dX);
        animation.setDuration(duration);
        return animation;
    }

    private ObjectAnimator getYAnimation(View view, float dY, int duration) {
        ObjectAnimator animation = ObjectAnimator.ofFloat(view, "translationY", dY);
        animation.setDuration(duration);
        return animation;
    }

    private ObjectAnimator getFadeOut(View view, int duration) {
        ObjectAnimator animation = ObjectAnimator.ofFloat(view, "alpha", 0f);
        animation.setDuration(duration);
        return animation;
    }

    private ObjectAnimator getFadeIn(View view, int duration) {
        ObjectAnimator animation = ObjectAnimator.ofFloat(view, "alpha", 1f);
        animation.setDuration(duration);
        return animation;
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return true;
    }
}
