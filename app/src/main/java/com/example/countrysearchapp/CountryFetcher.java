package com.example.countrysearchapp;

import android.os.Handler;
import android.os.Looper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CountryFetcher {

    public interface Callback {
        void onSuccess(List<Country> countries);
        void onError(String error);
    }

    public static void fetchCountries(String name, Callback callback) {
        new Thread(() -> {
            try {
                URL url = new URL("https://restcountries.com/v3.1/name/" + name);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");

                if (conn.getResponseCode() != 200) {
                    throw new Exception("HTTP Error: " + conn.getResponseCode());
                }

                InputStream input = conn.getInputStream();
                InputStreamReader reader = new InputStreamReader(input);

                StringBuilder response = new StringBuilder();
                int data;
                while ((data = reader.read()) != -1) {
                    response.append((char) data);
                }

                List<Country> countries = parseResponse(response.toString());
                new Handler(Looper.getMainLooper()).post(() -> callback.onSuccess(countries));

            } catch (Exception e) {
                new Handler(Looper.getMainLooper()).post(() ->
                        callback.onError(e.getMessage())
                );
            }
        }).start();
    }

    private static List<Country> parseResponse(String json) throws Exception {
        List<Country> countries = new ArrayList<>();
        JSONArray countriesArray = new JSONArray(json);

        for (int i = 0; i < countriesArray.length(); i++) {
            JSONObject countryObj = countriesArray.getJSONObject(i);
            Country country = new Country();

            country.setCommonName(countryObj.getJSONObject("name").getString("common"));
            country.setFlagUrl(countryObj.getJSONObject("flags").getString("png"));

            JSONArray capitalArray = countryObj.getJSONArray("capital");
            List<String> capitals = new ArrayList<>();
            for (int j = 0; j < capitalArray.length(); j++) {
                capitals.add(capitalArray.getString(j));
            }
            country.setCapitals(capitals);

            country.setPopulation(countryObj.getLong("population"));

            JSONObject languagesObj = countryObj.getJSONObject("languages");
            List<String> languages = new ArrayList<>();
            Iterator<String> keys = languagesObj.keys();
            while (keys.hasNext()) {
                String key = keys.next();
                languages.add(languagesObj.getString(key));
            }
            country.setLanguages(languages);

            countries.add(country);
        }

        return countries;
    }
}