package org.myoralvillage.cashcalculator;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import org.myoralvillage.cashcalculator.views.CustomVideoView;
import org.myoralvillage.cashcalculatormodule.services.CurrencyService;

public class NumericVideoActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_launch_video);

        final VideoView video = (CustomVideoView) this.findViewById(R.id.videoView);
        final String uri = "android.resource://" + getPackageName() + "/" + R.raw.numeric_video_updated_edited;
        video.setVideoURI(Uri.parse(uri));
        video.start();
        replayButtonListener(video);
        skipButtonListener();
    }

    private void switchToMain(String currencyCode) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("currencyCode", currencyCode);
        intent.putExtra("numericMode", getIntent().getBooleanExtra("numericMode", false));
        startActivity(intent);
        finish();
    }

    private void skipButtonListener() {
        ImageView setting = findViewById(R.id.skip);
        String[] currencyName = new String[1];
        currencyName[0] = getIntent().getStringExtra("currencyName");
        setting.setOnClickListener(e -> new CurrencyService(getApplicationContext(), currencyName)
                .call(currencies -> switchToMain(currencies[0])));
        // perhaps find a way to go to the flags when skipping the video so that the user can select their currency
    }

    private void replayButtonListener(VideoView video) {
        ImageView setting = findViewById(R.id.replay);
        video.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                setting.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (v.getId() == R.id.replay) {
                            video.start();
                        }
                    }
                });
            }
        });
        setting.setOnClickListener(e -> video.resume());
    }
}


