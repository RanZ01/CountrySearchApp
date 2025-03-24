package com.example.countrysearchapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CountrySearchActivity extends AppCompatActivity {

    private EditText etCountryName;
    private Button btnSearch;
    private ProgressBar progressBar;
    private RecyclerView rvCountries;
    private CountryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_search);

        // Bind views
        etCountryName = findViewById(R.id.etCountryName);
        btnSearch = findViewById(R.id.btnSearch);
        progressBar = findViewById(R.id.progressBar);
        rvCountries = findViewById(R.id.rvCountries);

        // Set RecyclerView layout to vertical list
        rvCountries.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CountryAdapter();
        rvCountries.setAdapter(adapter);

        // Set button click listener to start search
        btnSearch.setOnClickListener(v -> {
            String query = etCountryName.getText().toString().trim();
            if (query.isEmpty()) {
                // Show toast if input is empty
                Toast.makeText(this, "Please enter a country name", Toast.LENGTH_SHORT).show();
                return;
            }

            // Show loading spinner and start network request
            progressBar.setVisibility(View.VISIBLE);
            fetchCountries(query);
        });
    }

    // Fetch country data from the network using helper class
    private void fetchCountries(String name) {
        CountryFetcher.fetchCountries(name, new CountryFetcher.Callback() {
            @Override
            public void onSuccess(List<Country> countries) {
                // Hide progress bar and update RecyclerView
                progressBar.setVisibility(View.GONE);
                adapter.setCountries(countries);
            }

            @Override
            public void onError(String error) {
                // Hide progress bar and show error
                progressBar.setVisibility(View.GONE);
                Toast.makeText(CountrySearchActivity.this, "Error: " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }
}