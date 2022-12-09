package com.terzulli.weatherapptest.utils;

/**
 * TODO levare / modificare questo commento descrittivo
 * Classe per memorizzare la previsione metereologica di una determinata ora
 */
public class HourlyForecast {

    private final int hour;
    private final int temperature;
    private final String weather;

    public HourlyForecast(int hour, int temperature, String weather) {
        this.hour = hour;
        this.temperature = temperature;
        this.weather = weather;
    }

    public int getHour() {
        return hour;
    }

    public int getTemperature() {
        return temperature;
    }

    public String getWeather() {
        return weather;
    }
}
