package org.myoralvillage.cashcalculatormodule.services;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.myoralvillage.cashcalculatormodule.R;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
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
    private static final String FILE_NAME = "db.json";
    private static String[] strings = null;

    private Context context;
    private Callback<String[]> callable;

    public CurrencyService(Context context, Callback<String[]> callable) {
        this.context = context;
        this.callable = callable;
    }

    public void run() {
        new Thread(() -> callable.consume(getCurrencies())).start();
    }

    private String readStream(InputStream in) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            for (int ch = reader.read(); ch != -1; ch = reader.read()) {
                stringBuilder.append((char) ch);
            }
        }

        return stringBuilder.toString();
    }

    private String[] parseJson(String json) throws JSONException {
        JSONArray currencies = new JSONObject(json)
                .getJSONArray("currencies");

        String[] strings = new String[currencies.length()];

        for (int i = 0; i < currencies.length(); i++) {
            strings[i] = currencies.getString(i);
        }

        return strings;
    }

    private boolean isOnline() {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            return false;
        }

        NetworkInfo[] info = connectivity.getAllNetworkInfo();
        if (info == null) {
            return false;
        }

        for (NetworkInfo networkInfo : info) {
            if (networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                return true;
            }
        }

        return false;
    }

    private String[] getCurrencies() {
        if (strings != null) {
            return strings;
        }

        String country = Locale.getDefault().getCountry();
        File file = new File(context.getFilesDir(), FILE_NAME);

        try {
            String json;

            if (isOnline()) {
                URL url = new URL(BASE_URL + country);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());

                json = readStream(in);
                if (json != null) {
                    strings = parseJson(json);
                    try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {
                        bufferedWriter.write(json);
                    }
                }

                urlConnection.disconnect();
            } else {
                if (file.exists()) {
                    json = readStream(new FileInputStream(file));
                    if (json != null) {
                        strings = parseJson(json);
                    }
                }
            }
        } catch (IOException | JSONException ignored) {
        }

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
