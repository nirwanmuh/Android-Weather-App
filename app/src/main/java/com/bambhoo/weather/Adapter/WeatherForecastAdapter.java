package com.bambhoo.weather.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bambhoo.weather.Common.Common;
import com.bambhoo.weather.Model.Weather;
import com.bambhoo.weather.Model.WeatherForecastResult;
import com.bambhoo.weather.R;
import com.squareup.picasso.Picasso;

public class WeatherForecastAdapter extends RecyclerView.Adapter<WeatherForecastAdapter.MyViewHolder> {

    Context context;
    WeatherForecastResult weatherForecastResult;

    public WeatherForecastAdapter(Context context, WeatherForecastResult weatherForecastResult) {
        this.context = context;
        this.weatherForecastResult = weatherForecastResult;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_forecast,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Picasso.get().load(new StringBuilder("https://openweathermap.org/img/w/")
        .append(weatherForecastResult.list.get(position).weather.get(0).getIcon())
        .append(".png").toString()).into(holder.imgWeather);

        holder.txtDate.setText(new StringBuilder(Common.convertUnixToDate(weatherForecastResult.list.get(position).getDt())));

        holder.txtDesc.setText(new StringBuilder(weatherForecastResult.list.get(position).weather.get(0).getDescription()));

        holder.txtTemp.setText(new StringBuilder(String.valueOf(weatherForecastResult.list.get(position).main.getTemp())).append("°C"));
    }

    @Override
    public int getItemCount() {
        return weatherForecastResult.list.size();
    }

    public class MyViewHolder extends  RecyclerView.ViewHolder{
        TextView txtDate,txtDesc,txtTemp;
        ImageView imgWeather;
        public MyViewHolder(View itemView) {
            super(itemView);

            imgWeather = (ImageView)itemView.findViewById(R.id.imgweatherforecast);
            txtDate = (TextView)itemView.findViewById(R.id.txtdateforecast);
            txtDesc = (TextView)itemView.findViewById(R.id.txtdescforecast);
            txtTemp = (TextView)itemView.findViewById(R.id.txttemperatureforecast);

        }
    }
}
