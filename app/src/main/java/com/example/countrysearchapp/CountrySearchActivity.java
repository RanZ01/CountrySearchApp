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

        // 初始化视图
        etCountryName = findViewById(R.id.etCountryName);
        btnSearch = findViewById(R.id.btnSearch);
        progressBar = findViewById(R.id.progressBar);
        rvCountries = findViewById(R.id.rvCountries);

        // 设置 RecyclerView
        rvCountries.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CountryAdapter();
        rvCountries.setAdapter(adapter);

        // 设置搜索按钮点击事件
        btnSearch.setOnClickListener(v -> {
            String query = etCountryName.getText().toString().trim();
            if (query.isEmpty()) {
                Toast.makeText(this, "Please enter a country name", Toast.LENGTH_SHORT).show();
                return;
            }

            progressBar.setVisibility(View.VISIBLE);
            fetchCountries(query);
        });
    }

    private void fetchCountries(String name) {
        CountryFetcher.fetchCountries(name, new CountryFetcher.Callback() {
            @Override
            public void onSuccess(List<Country> countries) {
                progressBar.setVisibility(View.GONE);
                adapter.setCountries(countries);
            }

            @Override
            public void onError(String error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(CountrySearchActivity.this, "Error: " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }
}