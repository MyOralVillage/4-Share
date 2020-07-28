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


public class TutorialActivity extends AppCompatActivity {
    private CashCalculatorFragment fragment;
    private CurrencyScrollbarView currencyScrollbar;
    private CurrencyModel currency;
    private ArrayList<Animator> animations;
    private CountingTableView countingTable;
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
        fragment.initialize("PKR");
        currencyScrollbar = fragment.getCurrencyScrollbarView();
        currency = currencyScrollbar.getCurrency();
        countingTable = fragment.getCountingTableView();
        animations = new ArrayList<>();
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
        scrollScrollbar(1500, 1000, 2000);
        clickOnDenomination(currency.getDenominations().size() - 1, 3500);
        scrollScrollbar(0, 4000, 2000);
        clickOnDenomination(1, 6500);
        changeToAddition(7000);

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
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return true;
    }
}
