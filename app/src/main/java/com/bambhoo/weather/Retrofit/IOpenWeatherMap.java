package com.bambhoo.weather.Retrofit;

import com.bambhoo.weather.Model.WeatherForecastResult;
import io.reactivex.Observable;

import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IOpenWeatherMap {
    @GET("forecast")
    Observable<WeatherForecastResult> getForecastWeatherByZip(@Query("zip") String zip,
                                                              @Query("appid") String appid,
                                                              @Query("units") String unit);
}
