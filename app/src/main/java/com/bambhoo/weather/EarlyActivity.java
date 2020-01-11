package com.bambhoo.weather;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bambhoo.weather.Adapter.WeatherForecastAdapter;
import com.bambhoo.weather.Common.Common;
import com.bambhoo.weather.Model.WeatherForecastResult;
import com.bambhoo.weather.Retrofit.IOpenWeatherMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class EarlyActivity extends AppCompatActivity {
    EditText edtnama,edtkodepos;
    Button btnmulai;

    CompositeDisposable compositeDisposable;
    IOpenWeatherMap iOpenWeatherMap;

    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_early);

        if (Build.VERSION.SDK_INT >= 21){
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.white));
        }

        edtnama=findViewById(R.id.edtNama);
        edtkodepos=findViewById(R.id.edtKodepos);

        btnmulai=findViewById(R.id.btnMulai);

        btnmulai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog mDialog = new ProgressDialog(EarlyActivity.this);
                mDialog.setMessage("Please Wait...");
                mDialog.show();
                if(edtnama.getText().toString().isEmpty()||edtkodepos.getText().toString().isEmpty()){
                    mDialog.dismiss();
                    Toast.makeText(EarlyActivity.this," Data tidak boleh kosong!",Toast.LENGTH_LONG).show();
                }
                else if (edtnama.getText().toString().length()>12) {
                    mDialog.dismiss();
                    Toast.makeText(EarlyActivity.this, "Nama maksimum 12 karakter", Toast.LENGTH_LONG).show();
                }
                else {
                    Common.currentName=edtnama.getText().toString();
                    Common.currentZip=edtkodepos.getText().toString();
                    startActivity(new Intent(EarlyActivity.this,MainActivity.class));
                    mDialog.dismiss();
                }
            }
        });
    }
}
