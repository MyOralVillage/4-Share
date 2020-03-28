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
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Locale;

public class CurrencyService {

    private static final String BASE_URL =
            "https://cash-calculator-admin.herokuapp.com/api/currencies/";
    private static final String FILE_NAME = "db.json";
    private static final String[] DEFAULT_ORDER = {"KES", "PKR", "BDT", "USD", "INR"};
    private static String[] strings = null;

    private Context context;

    public CurrencyService(Context context) {
        this.context = context;
    }

    public void call(Callback<String[]> callback) {
        new Thread(() -> callback.consume(getCurrencies())).start();
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
        if (strings != null && !Arrays.equals(strings, DEFAULT_ORDER)) {
            return strings;
        }

        String country = Locale.getDefault().getCountry();
        File file = new File(context.getFilesDir(), FILE_NAME);

        try {
            String json = null;
            boolean write = true;

            if (isOnline()) {
                URL url = new URL(BASE_URL + country);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());

                json = readStream(in);

                urlConnection.disconnect();
            } else {
                if (file.exists()) {
                    json = readStream(new FileInputStream(file));
                    write = false;
                }
            }

            if (json != null) {
                strings = parseJson(json);
                if (write) {
                    file.createNewFile();
                    try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {
                        bufferedWriter.write(json);
                    }
                }
            }
        } catch (IOException | JSONException ignored) {
        }

        if (strings == null) {
            strings = DEFAULT_ORDER;
        }

        return strings;
    }

    public interface Callback<V> {
        void consume(V v);
    }

    public static int getCurrencyResource(String currency) {
        int id = -1;
        try {
            id = R.drawable.class.getField(currency.toLowerCase()).getInt(null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        return id;
    }
}
