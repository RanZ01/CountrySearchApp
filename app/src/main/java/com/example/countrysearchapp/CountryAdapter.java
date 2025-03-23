package com.example.countrysearchapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.CountryViewHolder> {

    private List<Country> countries;

    public void setCountries(List<Country> countries) {
        this.countries = countries;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CountryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_country, parent, false);
        return new CountryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CountryViewHolder holder, int position) {
        Country country = countries.get(position);
        holder.tvCommonName.setText(country.getCommonName());
        holder.tvCapital.setText("Capital: " + String.join(", ", country.getCapitals()));
        holder.tvPopulation.setText("Population: " + country.getPopulation());
        holder.tvLanguages.setText("Languages: " + String.join(", ", country.getLanguages()));

        // 加载国旗图片
        ImageLoader.loadImage(country.getFlagUrl(), holder.ivFlag);
    }

    @Override
    public int getItemCount() {
        return countries != null ? countries.size() : 0;
    }

    static class CountryViewHolder extends RecyclerView.ViewHolder {
        ImageView ivFlag;
        TextView tvCommonName, tvCapital, tvPopulation, tvLanguages;

        public CountryViewHolder(@NonNull View itemView) {
            super(itemView);
            ivFlag = itemView.findViewById(R.id.ivFlag);
            tvCommonName = itemView.findViewById(R.id.tvCommonName);
            tvCapital = itemView.findViewById(R.id.tvCapital);
            tvPopulation = itemView.findViewById(R.id.tvPopulation);
            tvLanguages = itemView.findViewById(R.id.tvLanguages);
        }
    }
}