package com.terzulli.weatherapptest.utils;

import java.util.ArrayList;
import java.util.Calendar;

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

    public int getCurrentTemperature() {
        if (dailyForecast.isEmpty())
            return -1;
        else {
            ArrayList<HourlyForecast> hourlyForecast = dailyForecast.get(0).getHourlyForecast();
            if (hourlyForecast.isEmpty())
                return -1;
            else {
                return hourlyForecast.get(Calendar.getInstance().get(Calendar.HOUR_OF_DAY)).getTemperature();
            }
        }
    }

    public String getCurrentWeatherCode() {
        if (dailyForecast.isEmpty())
            return "";
        else {
            ArrayList<HourlyForecast> hourlyForecast = dailyForecast.get(0).getHourlyForecast();
            if (hourlyForecast.isEmpty())
                return "";
            else {
                return hourlyForecast.get(Calendar.getInstance().get(Calendar.HOUR_OF_DAY)).getWeather();
            }
        }
    }
}
