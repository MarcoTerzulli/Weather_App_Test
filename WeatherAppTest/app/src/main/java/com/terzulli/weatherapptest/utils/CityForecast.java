package com.terzulli.weatherapptest.utils;

import java.util.ArrayList;

/**
 * TODO levare / modificare questo commento descrittivo
 * Classe per memorizzare le previsioni giornaliere di una determinata cittò,
 * incluse le previsioni orarie dei vari giorni
 */
public class CityForecast {

    private final String city;
    private final String country;
    private final String state;
    private final float latitude;
    private final float longitude;
    private final ArrayList<DailyForecast> dailyForecast;

    public CityForecast(String city, String country, String state, float latitude, float longitude,
                        ArrayList<DailyForecast> dailyForecast) {
        this.city = city;
        this.country = country;
        this.state = state;
        this.latitude = latitude;
        this.longitude = longitude;
        this.dailyForecast = dailyForecast;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getState() {
        return state;
    }

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public ArrayList<DailyForecast> getDailyForecast() {
        return dailyForecast;
    }
}
