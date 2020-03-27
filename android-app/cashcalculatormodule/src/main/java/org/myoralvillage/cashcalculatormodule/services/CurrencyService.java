package org.myoralvillage.cashcalculatormodule.services;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.myoralvillage.cashcalculatormodule.R;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;

public class CurrencyService {

    private static final String BASE_URL =
            "https://cash-calculator-admin.herokuapp.com/api/currencies/";

    private Callback<String[]> callable;

    public CurrencyService(Callback<String[]> callable) {
        this.callable = callable;
    }

    public void run() {
        new Thread(() -> callable.consume(getCurrencies())).start();
    }

    private String readStream(InputStream in) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder stringBuilder = new StringBuilder();

        for (int ch = reader.read(); ch != -1; ch = reader.read()) {
            stringBuilder.append((char) ch);
        }

        return stringBuilder.toString();
    }

    private String[] getCurrencies() {
        String country = Locale.getDefault().getCountry();
        String[] strings = null;

        try {
            URL url = new URL(BASE_URL + country);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());

            String json = readStream(in);
            if (json != null) {
                JSONArray currencies = new JSONObject(json)
                        .getJSONArray("currencies");

                strings = new String[currencies.length()];

                for (int i = 0; i < currencies.length(); i++) {
                    strings[i] = currencies.getString(i);
                }
            }

            urlConnection.disconnect();
        } catch (IOException | JSONException ignored) {}

        return strings;
    }

    public interface Callback<V> {
        void consume(V v);
    }

    public static int getCurrencyResource(String currency) {
        int id = -1;
        for (Field field : R.drawable.class.getFields()) {
            if (field.getName().equals(currency.toLowerCase())) {
                try {
                    id = field.getInt(null);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                break;
            }
        }

        return id;
    }
}
