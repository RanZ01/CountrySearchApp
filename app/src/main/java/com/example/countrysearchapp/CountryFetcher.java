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

    // Callback interface to return result or error to UI
    public interface Callback {
        void onSuccess(List<Country> countries);
        void onError(String error);
    }

    // Static method to fetch countries by name
    public static void fetchCountries(String name, Callback callback) {
        new Thread(() -> {
            try {
                // Build URL for REST API
                URL url = new URL("https://restcountries.com/v3.1/name/" + name);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");

                // Check for HTTP success
                if (conn.getResponseCode() != 200) {
                    throw new Exception("HTTP Error: " + conn.getResponseCode());
                }

                // Read input stream and convert to string
                InputStream input = conn.getInputStream();
                InputStreamReader reader = new InputStreamReader(input);

                StringBuilder response = new StringBuilder();
                int data;
                while ((data = reader.read()) != -1) {
                    response.append((char) data);
                }

                // Parse response JSON and post result back to main thread
                List<Country> countries = parseResponse(response.toString());
                new Handler(Looper.getMainLooper()).post(() -> callback.onSuccess(countries));

            } catch (Exception e) {
                // Handle error on main thread
                new Handler(Looper.getMainLooper()).post(() ->
                        callback.onError(e.getMessage())
                );
            }
        }).start(); // Start background thread
    }

    // Parse JSON response into Country objects
    private static List<Country> parseResponse(String json) throws Exception {
        List<Country> countries = new ArrayList<>();
        JSONArray countriesArray = new JSONArray(json);

        for (int i = 0; i < countriesArray.length(); i++) {
            JSONObject countryObj = countriesArray.getJSONObject(i);
            Country country = new Country();

            // Get common name and flag
            country.setCommonName(countryObj.getJSONObject("name").getString("common"));
            country.setFlagUrl(countryObj.getJSONObject("flags").getString("png"));

            // Get official native name (first one if multiple)
            JSONObject nativeNameObj = countryObj.getJSONObject("name").getJSONObject("nativeName");
            if (nativeNameObj.length() > 0) {
                String firstLanguageKey = nativeNameObj.keys().next();
                country.setNativeOfficialName(nativeNameObj.getJSONObject(firstLanguageKey).getString("official"));
            }

            // Region
            country.setRegion(countryObj.getString("region"));

            // Timezones
            JSONArray timezonesArray = countryObj.getJSONArray("timezones");
            List<String> timezones = new ArrayList<>();
            for (int j = 0; j < timezonesArray.length(); j++) {
                timezones.add(timezonesArray.getString(j));
            }
            country.setTimezones(timezones);

            // Capitals
            JSONArray capitalArray = countryObj.getJSONArray("capital");
            List<String> capitals = new ArrayList<>();
            for (int j = 0; j < capitalArray.length(); j++) {
                capitals.add(capitalArray.getString(j));
            }
            country.setCapitals(capitals);

            // Population
            country.setPopulation(countryObj.getLong("population"));

            // Languages (key-value pairs, extract values only)
            JSONObject languagesObj = countryObj.getJSONObject("languages");
            List<String> languages = new ArrayList<>();
            Iterator<String> keys = languagesObj.keys();
            while (keys.hasNext()) {
                String key = keys.next();
                languages.add(languagesObj.getString(key));
            }
            country.setLanguages(languages);

            countries.add(country); // Add to final result
        }
        return countries;
    }
}