package com.example.countrysearchapp;

import java.util.List;

public class Country {
    private String commonName;
    private String nativeOfficialName;
    private String flagUrl;
    private List<String> capitals;
    private String region;
    private long population;
    private List<String> languages;
    private List<String> timezones;

    public String getCommonName() { return commonName; }
    public void setCommonName(String commonName) { this.commonName = commonName; }

    public String getNativeOfficialName() { return nativeOfficialName; }
    public void setNativeOfficialName(String nativeOfficialName) { this.nativeOfficialName = nativeOfficialName; }

    public String getFlagUrl() { return flagUrl; }
    public void setFlagUrl(String flagUrl) { this.flagUrl = flagUrl; }

    public List<String> getCapitals() { return capitals; }
    public void setCapitals(List<String> capitals) { this.capitals = capitals; }

    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }

    public long getPopulation() { return population; }
    public void setPopulation(long population) { this.population = population; }

    public List<String> getLanguages() { return languages; }
    public void setLanguages(List<String> languages) { this.languages = languages; }

    public List<String> getTimezones() { return timezones; }
    public void setTimezones(List<String> timezones) { this.timezones = timezones; }
}