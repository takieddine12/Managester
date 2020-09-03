package com.manager.managester.services;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.manager.managester.R;
import com.manager.managester.ui.MainActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.manager.managester.utils.BaseApplication.CHANNEL_ID;

public class NotificationService extends Service {
    private DatabaseReference databaseReference;
    private NotificationManagerCompat compat;
    private static final int NOTIFICATION_CODE = 1;
    private static final int START_ACTIVITY_CODE = 11;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        databaseReference = FirebaseDatabase.getInstance().getReference("product_details");
        compat = NotificationManagerCompat.from(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        FireNotification();
        return START_NOT_STICKY;
    }

    private void FireNotification() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snap : snapshot.getChildren()) {
                    Date _date = Calendar.getInstance().getTime();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                    String device_date = simpleDateFormat.format(_date);
                    String product_date = snap.child("productionexpiry").getValue().toString();
                    if (device_date.equals(product_date)) {
                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),
                                START_ACTIVITY_CODE,intent,PendingIntent.FLAG_UPDATE_CURRENT);
                        NotificationCompat.Builder notificationCompat = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                                .setContentTitle("Here we write product name")
                                .setContentText("Here we write product message")
                                .setSmallIcon(R.drawable.notification_icon)
                                .addAction(R.drawable.notification_icon,"",pendingIntent)
                                .setAutoCancel(true);

                        compat.notify(NOTIFICATION_CODE, notificationCompat.build());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
