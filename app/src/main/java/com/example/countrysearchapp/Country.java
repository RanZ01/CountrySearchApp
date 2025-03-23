package com.example.countrysearchapp;

import java.util.List;

public class Country {
    private String commonName;
    private String flagUrl;
    private List<String> capitals;
    private long population;
    private List<String> languages;

    // Getters and Setters
    public String getCommonName() { return commonName; }
    public void setCommonName(String commonName) { this.commonName = commonName; }

    public String getFlagUrl() { return flagUrl; }
    public void setFlagUrl(String flagUrl) { this.flagUrl = flagUrl; }

    public List<String> getCapitals() { return capitals; }
    public void setCapitals(List<String> capitals) { this.capitals = capitals; }

    public long getPopulation() { return population; }
    public void setPopulation(long population) { this.population = population; }

    public List<String> getLanguages() { return languages; }
    public void setLanguages(List<String> languages) { this.languages = languages; }
}