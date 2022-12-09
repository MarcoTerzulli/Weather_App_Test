package com.terzulli.weatherapptest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;

import com.terzulli.weatherapptest.utils.CityForecast;
import com.terzulli.weatherapptest.utils.ForecastAPI;
import com.terzulli.weatherapptest.utils.HourlyForecast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private HorizontalScrollView swipeLayout;
    private RecyclerView recyclerView;
    private Button btnForecast;
    private EditText textLatitude;
    private EditText textLongitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        swipeLayout = this.findViewById(R.id.horizontal_scrollview);
        recyclerView = this.findViewById(R.id.recycler);
        btnForecast = this.findViewById(R.id.btn_get_forecast);
        textLongitude = this.findViewById(R.id.longitde);
        textLatitude = this.findViewById(R.id.latitude);

        // al click del bottone, si ottengono le previsioni
        // TODO ovviamente nell'app reale la gestione sarà diversa,
        //  ma l'idea è di chiamare una funzione che si occupa di tutto
        btnForecast.setOnClickListener(
                view -> updateForecast()
        );
    }

    private void updateForecast() {
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
    }
}