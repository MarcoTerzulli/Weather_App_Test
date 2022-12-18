package com.terzulli.weatherapptest;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private HorizontalScrollView swipeLayout;
    private RecyclerView recyclerView;
    private TextView txtActualCityName, txtActualTemp, txtActualWeather, txtMinTemp, txtMaxTemp;
    private ImageView imageActualWeather, imageSubmitSearch;
    private TextInputEditText inputCitySearch;

    private LocationManager locationManager;
    private final int PERMISSION_CODE = 1;

    private Location lastLocation;
    private String currentCityName = "";
    private String displayedCityName = "";

    private final int LOCATION_REFRESH_TIME = 15000; // 60 seconds to update
    private final int LOCATION_REFRESH_DISTANCE = 500; // 500 meters to update

    private final LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(final Location location) {
            lastLocation = location;

            updateCurrentCityWeather();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        swipeLayout = this.findViewById(R.id.horizontal_scrollview);
        recyclerView = this.findViewById(R.id.recycler);
        txtActualCityName = this.findViewById(R.id.txt_actual_city);
        txtActualTemp = this.findViewById(R.id.city_actual_temp);
        txtActualWeather = this.findViewById(R.id.city_actual_weather);
        txtMinTemp = this.findViewById(R.id.city_min_temp);
        txtMaxTemp = this.findViewById(R.id.city_max_temp);
        txtActualTemp = this.findViewById(R.id.city_actual_temp);
        imageActualWeather = this.findViewById(R.id.city_actual_weather_icon);
        imageSubmitSearch = this.findViewById(R.id.city_search_button);
        inputCitySearch = this.findViewById(R.id.city_search_input_edit_text);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // aggiorno la location corrente
        updateLocation();
        updateCurrentCityWeather();

        imageSubmitSearch.setOnClickListener(view -> {
            String searchedCity = inputCitySearch.getText().toString();

            if (!currentCityName.isEmpty()) {
                txtActualCityName.setText(currentCityName);
                //getWeatherForecast(cityName);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        updateLocation();
    }

    private void updateCurrentCityWeather() {
        if(lastLocation != null) {
            currentCityName = getCityName(lastLocation.getLongitude(), lastLocation.getLatitude());

            Log.d("DEBUG", "Long" + lastLocation.getLongitude() + " - Lat " + lastLocation.getLatitude());
            Log.d("DEBUG", "City " + currentCityName);

            if(currentCityName != null && !currentCityName.isEmpty() && currentCityName.equals(displayedCityName)) {
                txtActualCityName.setText(currentCityName);
            }
        } else {
            Log.d("DEBUG", "Last location null");
        }
    }

    private void checkPermissions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            }, PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_CODE) {
            if ((grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                // aggiorno la location
                updateLocation();
            } else {
                // TODO mettere errore in strings
                Toast.makeText(this, "Please grant the location permissions", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void updateLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            }, PERMISSION_CODE);
            Toast.makeText(this, "Non ho i permessi per la location", Toast.LENGTH_SHORT).show();
        } else {
            // ho i permessi
            //lastLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, (long) LOCATION_REFRESH_TIME,
                    (float) LOCATION_REFRESH_DISTANCE, (android.location.LocationListener) locationListener);
        }
    }

    private String getCityName(double longitude, double latitude) {
        String cityName = "Not found";
        Geocoder geocoder = new Geocoder(getBaseContext(), Locale.getDefault());

        try {
            List<Address> addressList = geocoder.getFromLocation(longitude, latitude, 1);

            if (!addressList.isEmpty()) {
                cityName = addressList.get(0).getAddressLine(0);
                // TODO memorizzarli da qualche parte
                String city = addressList.get(0).getLocality();
                String state = addressList.get(0).getAdminArea();
                String country = addressList.get(0).getCountryName();
            } else {
                Toast.makeText(this, "City not found", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return cityName;
    }

    /*private void updateForecast() {
        // TODO queste informazioni andrebbero ottenute ovviamente dal gps
        // TODO gestione del caso in cui il gps sia disattivato:
        //  si mostra un errore, o una location di dafault, o l'ultima location ottenuta con successo
        float latitude = Float.parseFloat(textLatitude.getText().toString());
        float longitude = Float.parseFloat(textLongitude.getText().toString());

        // si ottengono le previsioni tramite api
        CityForecast cityForecast = ForecastAPI.getForecast(latitude, longitude);

        // TODO si seleziona il giorno (per adesso today)
        ArrayList<HourlyForecast> hourlyForecast = cityForecast.getDailyForecast().get(0).getHourlyForecast();

        // creazione ed inizializzazione dell'adapter
        HourlyForecastItemAdapter adapter = new HourlyForecastItemAdapter(this, hourlyForecast);
        recyclerView.setAdapter(adapter);

        // si resetta la posione della recycler in modo che venga visualizzata dall'inizio
        recyclerView.scrollToPosition(0);
    }*/
}