package com.manager.utils;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.net.Uri;
import android.os.Build;

import androidx.core.app.NotificationManagerCompat;

import timber.log.Timber;

public class BaseApplication extends Application {
    public static final String CHANNEL_ID = "channel_id";
    public static final String CHANNEL_NAME = "channel_string";
    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());
        FireNotication();
    }

    private void FireNotication(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            //TODO : If version is bigger than 26 , we need to use notification channel
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
