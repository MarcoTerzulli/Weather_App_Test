package com.terzulli.weatherapptest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.terzulli.weatherapptest.utils.HourlyForecast;

import java.util.ArrayList;

public class HourlyForecastItemAdapter extends RecyclerView.Adapter<HourlyForecastItemAdapter.ItemsViewHolder> {

    private final Context context;
    private final ArrayList<HourlyForecast> hourlyForecastList;

    public HourlyForecastItemAdapter(Context context, ArrayList<HourlyForecast> hourlyForecast) {
        this.context = context;
        this.hourlyForecastList = hourlyForecast;
    }

    @NonNull
    @Override
    public ItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // qui si definisce l'associazione tra il nostro view holder e l'elemento grafico della scheda
        View view = LayoutInflater.from(context).inflate(R.layout.item_forecast_for_list, parent, false);

        return new ItemsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemsViewHolder holder, int position) {
        if (hourlyForecastList == null || hourlyForecastList.size() == 0) {
            return;
        }

        HourlyForecast thisHour = hourlyForecastList.get(position);

        // TODO switch per selezione icona in base al tipo di condizione metereologica

        holder.itemHour.setText(thisHour.getHour());
        holder.itemTemperature.setText(thisHour.getTemperature());

    }

    @Override
    public int getItemCount() {
        if (hourlyForecastList == null)
            return 0;
        return hourlyForecastList.size();
    }

    public static class ItemsViewHolder extends RecyclerView.ViewHolder {
        private final ImageView itemIcon;
        private final TextView itemHour, itemTemperature;

        public ItemsViewHolder(@NonNull View itemView) {
            super(itemView);

            // qui si definisce l'associzione tra gli elementi "della scheda" ed il codice java

            itemIcon = itemView.findViewById(R.id.item_icon);
            itemHour = itemView.findViewById(R.id.item_hour);
            itemTemperature = itemView.findViewById(R.id.item_temperature);
        }
    }

}
