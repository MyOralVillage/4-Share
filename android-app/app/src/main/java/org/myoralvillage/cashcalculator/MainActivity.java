package org.myoralvillage.cashcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import org.myoralvillage.cashcalculatormodule.HelloWorldMessage;
import org.myoralvillage.cashcalculatormodule.models.CurrencyModel;
import org.myoralvillage.cashcalculatormodule.models.DenominationModel;
import org.myoralvillage.cashcalculatormodule.views.CurrencyScrollbarView;
import org.myoralvillage.cashcalculatormodule.views.listeners.CurrencyTapListener;
import org.myoralvillage.cashcalculatormodule.models.CountingModel;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private CurrencyModel curr_currency;
    private HelloWorldMessage message = new HelloWorldMessage();
    private double current_sum = 0; // Whether should I put it in onCreate

    CountingModel countingModel = new CountingModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView textView = findViewById(R.id.message_view);
        final TextView sumView = findViewById(R.id.sum_view);
        final TextView divisionView = findViewById(R.id.division);
        textView.setText(message.getMessage());

        final CurrencyScrollbarView currencyScrollbarView = findViewById(R.id.currency_scrollbar);
        currencyScrollbarView.setCurrency("CAD");
        this.curr_currency = currencyScrollbarView.getCurrency();
        currencyScrollbarView.setCurrencyTapListener(new CurrencyTapListener() {
            @Override
            public void onTapDenomination(DenominationModel denomination) {
                current_sum = countingModel.add_or_sub(denomination.getValue().doubleValue(), current_sum,true);
                textView.setText(String.format(Locale.CANADA, "Tapped on %s", denomination.getValue()));
                sumView.setText(String.format(Locale.CANADA, "%s %s", curr_currency.getSymbol(), current_sum));

                // The part below will be delete after the image arrangement part finished
                String divisionText = "";
                ArrayList<ArrayList<Object>> division = countingModel.division(current_sum, curr_currency);
                for (int i = 0; i < division.size(); i++){
                    int number = (int) division.get(i).get(1);
                    DenominationModel deno = (DenominationModel) division.get(i).get(0);
                    if (number != 0) {
                        String new_str = deno.getValue().toString();
                        new_str += " has " + division.get(i).get(1).toString() + "\n";
                        divisionText += new_str;
                    }
                }
                divisionView.setText(divisionText);
            }
        });
    }
}
