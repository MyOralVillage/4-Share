package org.myoralvillage.cashcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import org.myoralvillage.cashcalculatormodule.HelloWorldMessage;
import org.myoralvillage.cashcalculatormodule.models.DenominationModel;
import org.myoralvillage.cashcalculatormodule.views.CurrencyScrollbarView;
import org.myoralvillage.cashcalculatormodule.views.listeners.CurrencyTapListener;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private HelloWorldMessage message = new HelloWorldMessage();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView textView = findViewById(R.id.message_view);
        textView.setText(message.getMessage());

        CurrencyScrollbarView currencyScrollbarView = findViewById(R.id.currency_scrollbar);
        currencyScrollbarView.setCurrency("CAD");
        currencyScrollbarView.setCurrencyTapListener(new CurrencyTapListener() {
            @Override
            public void onTapDenomination(DenominationModel denomination) {
                textView.setText(String.format(Locale.CANADA, "Tapped on %s", denomination.getValue()));
            }
        });
    }
}
