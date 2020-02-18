package org.myoralvillage.cashcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import org.myoralvillage.cashcalculatormodule.HelloWorldMessage;
import org.myoralvillage.cashcalculatormodule.models.CurrencyModel;
import org.myoralvillage.cashcalculatormodule.models.DenominationModel;
import org.myoralvillage.cashcalculatormodule.views.CurrencyScrollbarView;
import org.myoralvillage.cashcalculatormodule.views.listeners.CurrencyTapListener;
import org.myoralvillage.cashcalculatormodule.services.CountingService;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private CurrencyModel currCurrency;
    private HelloWorldMessage message = new HelloWorldMessage();
    private double currentSum = 0;

    CountingService countingService = new CountingService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView textView = findViewById(R.id.message_view);
        final TextView sumView = findViewById(R.id.sum_view);
        final TextView divisionView = findViewById(R.id.allocation);
        textView.setText(message.getMessage());

        final CurrencyScrollbarView currencyScrollbarView = findViewById(R.id.currency_scrollbar);
        currencyScrollbarView.setCurrency("PKR");
        this.currCurrency = currencyScrollbarView.getCurrency();
        currencyScrollbarView.setCurrencyTapListener(new CurrencyTapListener() {
            @Override
            public void onTapDenomination(DenominationModel denomination) {
                currentSum += denomination.getValue().doubleValue();
                textView.setText(String.format(Locale.CANADA, "Tapped on %s", denomination.getValue()));
                sumView.setText(String.format(Locale.CANADA, "%s %s", currCurrency.getCurrency().getSymbol(), currentSum));

                // TODO: Delete this part below when the counting table is created
                String divisionText = "";
                ArrayList<Integer> allocation = countingService.allocation(currentSum, currCurrency);
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
