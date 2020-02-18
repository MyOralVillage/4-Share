package org.myoralvillage.cashcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import org.myoralvillage.cashcalculatormodule.HelloWorldMessage;
import org.myoralvillage.cashcalculatormodule.models.CurrencyModel;
import org.myoralvillage.cashcalculatormodule.models.DenominationModel;
import org.myoralvillage.cashcalculatormodule.views.CurrencyScrollbarView;
import org.myoralvillage.cashcalculatormodule.views.listeners.CurrencyTapListener;
import org.myoralvillage.cashcalculatormodule.models.CountingMethod;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private CurrencyModel currCurrency;
    private HelloWorldMessage message = new HelloWorldMessage();
    private double current_sum = 0; // Whether should I put it in onCreate

    CountingMethod countingMethod = new CountingMethod();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView textView = findViewById(R.id.message_view);
        final TextView sumView = findViewById(R.id.sum_view);
        final TextView divisionView = findViewById(R.id.allocation);
        textView.setText(message.getMessage());

        final CurrencyScrollbarView currencyScrollbarView = findViewById(R.id.currency_scrollbar);
        currencyScrollbarView.setCurrency("CAD");
        this.currCurrency = currencyScrollbarView.getCurrency();
        currencyScrollbarView.setCurrencyTapListener(new CurrencyTapListener() {
            @Override
            public void onTapDenomination(DenominationModel denomination) {
                current_sum = countingMethod.adding(denomination.getValue().doubleValue(), current_sum);
                textView.setText(String.format(Locale.CANADA, "Tapped on %s", denomination.getValue()));
                sumView.setText(String.format(Locale.CANADA, "%s %s", currCurrency.getCurrency().getSymbol(), current_sum));

                // The part below will be delete after the image arrangement part finished
                String divisionText = "";
                ArrayList<Integer> allocation = countingMethod.allocation(current_sum, currCurrency);
                Iterator denos = currCurrency.getDenominations().iterator();
                for (int i = 0; i < allocation.size(); i++){
                    int number = allocation.get(i);
                    DenominationModel deno = (DenominationModel) denos.next();
                    if (number != 0) {
                        String new_str = deno.getValue().toString();
                        new_str += " has " + number + "\n";
                        divisionText += new_str;
                    }
                }
                divisionView.setText(divisionText);
            }
        });
    }
}
