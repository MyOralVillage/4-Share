package org.myoralvillage.cashcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

public class TutorialActivity extends AppCompatActivity {
    private int height;
    private int width;

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

    private void animate() {
        ImageView finger = findViewById(R.id.finger);
        ObjectAnimator[] animations = {
                getXAnimation(finger, (float) (width * 0.5), 1500),
                getXAnimation(finger, (float) (width * 0.07), 1000),
                getFadeOut(finger, 250),
                getFadeIn(findViewById(R.id.bill1), 100),
                getYAnimation(finger, (float) (height * -0.5), 500),
                getFadeIn(finger, 250),
                getFadeOut(finger, 250),
                getFadeOut(findViewById(R.id.bill1), 100),
        };
        AnimatorSet as = new AnimatorSet();
        for (int i = 0; i < animations.length - 1; i++) {
            as.play(animations[i]).before(animations[i + 1]);
        }
        as.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels;
        width = displayMetrics.heightPixels;
        animate();
    }
}
