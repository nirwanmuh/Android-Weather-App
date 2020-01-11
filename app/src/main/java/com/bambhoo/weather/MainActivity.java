package com.bambhoo.weather;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bambhoo.weather.Adapter.WeatherForecastAdapter;
import com.bambhoo.weather.Common.Common;
import com.bambhoo.weather.Model.WeatherForecastResult;
import com.bambhoo.weather.Retrofit.IOpenWeatherMap;
import com.bambhoo.weather.Retrofit.RetrofitClient;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    CompositeDisposable compositeDisposable;
    IOpenWeatherMap iOpenWeatherMap;
    ImageView imageView;

    TextView txt_city,txt_nama,txt_temp,txt_desc,txtgreeet;
    RecyclerView recyclerForecast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        compositeDisposable= new CompositeDisposable();
        Retrofit retrofit = RetrofitClient.getInstance();
        iOpenWeatherMap = retrofit.create(IOpenWeatherMap.class);

        recyclerForecast=(RecyclerView)findViewById(R.id.recyler_forecast);
        recyclerForecast.setHasFixedSize(true);
        recyclerForecast.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));


        txtgreeet=findViewById(R.id.txtgreet);
        txt_city=findViewById(R.id.txtcity);
        txt_nama=findViewById(R.id.txtname);
        txt_temp=findViewById(R.id.temperature);
        txt_desc=findViewById(R.id.description);
        imageView=findViewById(R.id.imgtoday);

        txt_nama.setText(Common.currentName);

        long time = System.currentTimeMillis();
        SimpleDateFormat sdf= new SimpleDateFormat("HH");
        String timeString = sdf.format(time);
        switch (timeString){
            case "04":
            case "05":
            case "06":
            case "07":
            case "08":
            case "09":
            case "10":
                txtgreeet.setText("Selamat Pagi");
                break;
            case "11":
            case "12":
            case "13":
            case "14":
                txtgreeet.setText("Selamat Siang");
                break;
            case "15":
            case "16":
            case "17":
            case "18":
                txtgreeet.setText("Selamat Sore");
                break;
            case "19":
            case "20":
            case "21":
            case "22":
            case "23":
            case "24":
            case "01":
            case "02":
            case "03":
                txtgreeet.setText("Selamat Malam");
                break;
        }

        getForecastWeatherInformation();
    }



    private void getForecastWeatherInformation() {
        compositeDisposable.add(iOpenWeatherMap.getForecastWeatherByZip(Common.currentZip, Common.APP_ID,"metric").subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<WeatherForecastResult>() {
                    @Override
                    public void accept(WeatherForecastResult weatherForecastResult) throws Exception {
                        displayForecastWeather(weatherForecastResult);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d("ERROR",""+throwable.getMessage());
                    }
                }));
    }

    private void displayForecastWeather(WeatherForecastResult weatherForecastResult) {
        txt_city.setText(new StringBuilder(weatherForecastResult.city.getName()));
        Picasso.get().load(new StringBuilder("https://openweathermap.org/img/w/")
                .append(weatherForecastResult.list.get(0).weather.get(0).getIcon())
                .append(".png").toString()).into(imageView);
        txt_temp.setText(new StringBuilder(String.valueOf(weatherForecastResult.list.get(0).main.getTemp())).append("°C"));
        txt_desc.setText(new StringBuilder(weatherForecastResult.list.get(0).weather.get(0).getDescription()));

        WeatherForecastAdapter adapter = new WeatherForecastAdapter(getApplicationContext(), weatherForecastResult);
        recyclerForecast.setAdapter(adapter);
    }

}
