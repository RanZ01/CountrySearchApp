package com.example.countrysearchapp;

import static android.text.Html.fromHtml;

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

    // Set data and refresh the list
    public void setCountries(List<Country> countries) {
        this.countries = countries;
        notifyDataSetChanged();
    }

    // Inflate item layout and create ViewHolder
    @NonNull
    @Override
    public CountryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_country, parent, false);
        return new CountryViewHolder(view);
    }

    // Bind country data to UI elements
    @Override
    public void onBindViewHolder(@NonNull CountryViewHolder holder, int position) {
        Country country = countries.get(position);

        holder.tvCommonName.setText(country.getCommonName());
        holder.tvCapital.setText(fromHtml("<b>Capital:</b> " + String.join(", ", country.getCapitals())));
        holder.tvPopulation.setText(fromHtml("<b>Population:</b> " + country.getPopulation()));
        holder.tvLanguages.setText(fromHtml("<b>Languages:</b> " + String.join(", ", country.getLanguages())));
        ImageLoader.loadImage(country.getFlagUrl(), holder.ivFlag);


        holder.tvNativeName.setText(fromHtml("<b>Native Name:</b> " + country.getNativeOfficialName()));
        holder.tvRegion.setText(fromHtml("<b>Region:</b> " + country.getRegion()));
        holder.tvTimezones.setText(fromHtml("<b>Timezones:</b> " + String.join(", ", country.getTimezones())));
    }

    // Return number of items in list
    @Override
    public int getItemCount() {
        return countries != null ? countries.size() : 0;
    }

    // ViewHolder class caches view references for each item
    static class CountryViewHolder extends RecyclerView.ViewHolder {
        ImageView ivFlag;
        TextView tvCommonName, tvCapital, tvPopulation, tvLanguages;
        TextView tvNativeName, tvRegion, tvTimezones;

        public CountryViewHolder(@NonNull View itemView) {
            super(itemView);

            ivFlag = itemView.findViewById(R.id.ivFlag);
            tvCommonName = itemView.findViewById(R.id.tvCommonName);
            tvCapital = itemView.findViewById(R.id.tvCapital);
            tvPopulation = itemView.findViewById(R.id.tvPopulation);
            tvLanguages = itemView.findViewById(R.id.tvLanguages);
            tvNativeName = itemView.findViewById(R.id.tvNativeName);
            tvRegion = itemView.findViewById(R.id.tvRegion);
            tvTimezones = itemView.findViewById(R.id.tvTimezones);
        }
    }
}