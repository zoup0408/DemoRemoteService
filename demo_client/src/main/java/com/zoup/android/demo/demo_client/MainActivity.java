package com.zoup.android.demo.demo_client;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.zoup.android.demo.demo_server.WeatherAidlInterface;

public class MainActivity extends AppCompatActivity {
    private WeatherAidlInterface weatherAidlInterface;
    private ServiceConnection serviceConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindService();
    }

    private void bindService() {
        Intent intent = new Intent();
        intent.setPackage("com.zoup.android.demo.demo_server");
        intent.setAction("com.zoup.android.demo.demo_server.WeatherService");

        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                weatherAidlInterface = WeatherAidlInterface.Stub.asInterface(service);
                try {
                    String weatherInfo = weatherAidlInterface.getWeatherInfo();
                    Toast.makeText(getApplicationContext(), weatherInfo, Toast.LENGTH_SHORT).show();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                weatherAidlInterface = null;
            }
        };

        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (serviceConnection != null) {
            unbindService(serviceConnection);
        }
    }
}
