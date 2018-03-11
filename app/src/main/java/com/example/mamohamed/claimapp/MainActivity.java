package com.example.mamohamed.claimapp;

import android.support.v7.app.AppCompatActivity;

/*
import android.Manifest;
import android.app.VoiceInteractor;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
*/

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
public class MainActivity extends AppCompatActivity {

    String WEATHER_URL = "http://api.openweathermap.org/data/2.5/weather";
    String APP_ID = "e72ca729af228beabd5d20e3b7749713";
    // time between location update 5000 miliseconds or 5 seconds
    long MIN_TIME = 5000;

    // distance between location update 1000meter
    long MIN_DISTANCE = 1000;

    final int REQUEST_CODE = 123;

    String LOCATION_PROVIDER = LocationManager.GPS_PROVIDER;


    // member var
    TextView mCityLabel;
    ImageView mWeatherImage;
    TextView mTempratureLabel;
    //TextView t;

    // dECLARE a location manager and listner

    LocationManager mLocationManager;
    LocationListener mLocationListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCityLabel = (TextView) findViewById(R.id.txt);
        mTempratureLabel = (TextView) findViewById(R.id.tempratureText);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("clima", "resume ya bombo" );
        getWeatherForCurrentLocation();

    }

    private void getWeatherForCurrentLocation() {
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.d("Clima","onLocationChanged");

                String longitude = String.valueOf(location.getLongitude());
                String latitude = String.valueOf(location.getLatitude());


                Log.d("Clima","longitude is : " + longitude);
                Log.d("Clima","latitude is : "+ latitude);

                RequestParams params = new RequestParams();
                params.put("lat", latitude);
                params.put("lon", longitude);
                params.put("appid", APP_ID);
                letsDoSomeNetworking(params);



            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                Log.e("Clima","OnProviderDisabled");
            }
        };
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return;
        }

        mLocationManager.requestLocationUpdates(LOCATION_PROVIDER, MIN_TIME, MIN_DISTANCE, mLocationListener);


    }
    private void letsDoSomeNetworking(RequestParams params){
        AsyncHttpClient client = new AsyncHttpClient();

        client.get(WEATHER_URL, params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
             //   t.setText(response.toString());
                WeatherModel weatherData = WeatherModel.fromJson(response);
                updateUI(weatherData);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers,Throwable e, JSONObject response){

                Log.e("clima", "Fail ya 3am"  + e.toString());
                Log.d("clima", "status code"  + statusCode);
                Toast.makeText(MainActivity.this , "Request Failed", Toast.LENGTH_SHORT).show();

            }
        });


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_CODE){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                getWeatherForCurrentLocation();

            }
            else {
                Log.d("Clima","permission Denied");
            }


        }


    }
    private void updateUI(WeatherModel weather ){
        mTempratureLabel.setText(weather.getmTemprature());
        mCityLabel.setText(weather.getmCity());

        //int resource = getResources().getIdentifier(weather.getmIcon(),"drawable",getPackageName());


    }

}
