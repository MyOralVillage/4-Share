package org.myoralvillage.cashcalculator;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import org.myoralvillage.cashcalculatormodule.views.CustomVideoView;

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
    private void skipButtonListener() {
        Button setting = findViewById(R.id.skip);
        setting.setOnClickListener((e) -> {
            switchToSplash();
        });
    }

    private void replayButtonListener(VideoView video) {
        Button setting = findViewById(R.id.rePlay);
        setting.setOnClickListener((e) -> {
            video.start();
        });
    }

    private void switchToSplash() {
        startActivity(new Intent(this, SplashActivity.class));
        finish();
    }
}
