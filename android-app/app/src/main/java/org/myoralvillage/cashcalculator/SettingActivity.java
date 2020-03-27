package org.myoralvillage.cashcalculator;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import org.myoralvillage.cashcalculatormodule.services.CurrencyService;
import org.myoralvillage.cashcalculatormodule.services.SettingService;

public class SettingActivity extends AppCompatActivity {
    private static final String[] DEFAULT_ORDER = {"KES", "PKR"};
    private static SettingService settingService = new SettingService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_setting);

        buildLayout();
    }

    public static SettingService getSettingService() {
        return settingService;
    }

    private void buildLayout() {
        new CurrencyService(stored -> {
            String[] currencies = stored != null ? stored : DEFAULT_ORDER;
            runOnUiThread(() -> {
                LinearLayout view = findViewById(R.id.currencies);
                int width = (int) TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP,
                        300f,
                        getResources().getDisplayMetrics()
                );
                int height = (int) TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP,
                        178f,
                        getResources().getDisplayMetrics()
                );
                int margin = (int) TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP,
                        30f,
                        getResources().getDisplayMetrics()
                );

                for (int i = 0; i < currencies.length; i++) {
                    String currency = currencies[i];
                    Button button = new Button(this);
                    LinearLayout.LayoutParams params =
                            new LinearLayout.LayoutParams(width, height);
                    if (i % 2 == 0) {
                        params.setMargins(0, margin, 0, margin);
                    } else if (i + 1 == currencies.length) {
                        params.setMargins(0, 0, 0, margin);
                    }
                    button.setLayoutParams(params);
                    button.setBackgroundResource(CurrencyService.getCurrencyResource(currency));
                    button.setOnClickListener(e -> {
                        settingService.setCurrencyName(currency);
                        switchToMainActivity();
                    });
                    view.addView(button);
                }
            });
        }).run();
    }

    private void switchToMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
