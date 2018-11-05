package com.zoup.android.demo.demo_server;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.IBinder;
import android.os.RemoteException;

public class WeatherService extends Service {
    private String weatherInfo = "白天晴到多云，夜间有小雨，空气质量良";

    public WeatherService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sendNotification();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return stub;

    }

    private WeatherAidlInterface.Stub stub = new WeatherAidlInterface.Stub() {
        @Override
        public String getWeatherInfo() throws RemoteException {
            return weatherInfo;
        }
    };

    @TargetApi(Build.VERSION_CODES.O)
    private void sendNotification() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        NotificationChannel notificationChannel = new NotificationChannel("channel_1", "channel_name", NotificationManager.IMPORTANCE_LOW);
        Notification notification = new Notification.Builder(this, "channel_1")
                .setContentTitle("今天天气预报")
                .setContentText(weatherInfo)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_background))
                .build();
        notificationManager.createNotificationChannel(notificationChannel);
        notificationManager.notify(1, notification);
    }
}
