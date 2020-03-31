package org.myoralvillage.cashcalculator;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import org.myoralvillage.cashcalculator.views.CustomVideoView;
import org.myoralvillage.cashcalculatormodule.services.CurrencyService;

public class VideoActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_launch_video);

        final VideoView video = (CustomVideoView) this.findViewById(R.id.videoView);
        final String uri = "android.resource://" + getPackageName() + "/" + R.raw.launch_video;
        video.setVideoURI(Uri.parse(uri));
        video.start();
        replayButtonListener(video);
        skipButtonListener();
    }

    private void switchToMain(String currencyCode) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("currencyCode", currencyCode);
        startActivity(intent);
        finish();
    }

    private void skipButtonListener() {
        ImageView setting = findViewById(R.id.skip);
        setting.setOnClickListener(e -> new CurrencyService(getApplicationContext())
                .call(currencies -> switchToMain(currencies[0])));
    }

    private void replayButtonListener(VideoView video) {
        ImageView setting = findViewById(R.id.replay);
        setting.setOnClickListener(e -> video.resume());
    }
}
