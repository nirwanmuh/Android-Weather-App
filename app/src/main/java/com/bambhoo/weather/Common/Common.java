package com.bambhoo.weather.Common;

import android.location.Location;

import com.bambhoo.weather.Adapter.WeatherForecastAdapter;
import com.bambhoo.weather.Model.WeatherForecastResult;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Common {

    public static final String APP_ID="06f1ed4cfeedb03dae78556ec43ede59";
    public static String Zipcode=null;

    public static String convertUnixToDate(long dt) {
        Date date=new Date(dt*1000L);
        SimpleDateFormat sdf= new SimpleDateFormat("dd EEE MM yyyy HH:mm");
        String formatted = sdf.format(date);
        return formatted;
    }

    public static String currentName;
    public static String currentZip;
    public static WeatherForecastAdapter currentadapter;
}
