package com.example.hossam.lockscreen;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.widget.Toast;

public class LockScreenService extends Service {


    public LockScreenService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        Toast.makeText(this, "on onBind service", Toast.LENGTH_LONG).show();

        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(this, "on create service", Toast.LENGTH_LONG).show();
        registerReceiver();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "on onStartCommand service", Toast.LENGTH_LONG).show();

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "on onDestroy service", Toast.LENGTH_LONG).show();

    }

    @Override
    public boolean onUnbind(Intent intent) {
        Toast.makeText(this, "on onUnbind service", Toast.LENGTH_LONG).show();

        return super.onUnbind(intent);
    }

    public void registerReceiver(){
        try {
            IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);

            filter.addAction(Intent.ACTION_SCREEN_OFF);
            filter.addAction(Intent.ACTION_USER_PRESENT);

            BroadcastReceiver mReceiver = new receiverScreen();

            registerReceiver(mReceiver, filter);
        } catch (Exception e) {

        }
    }

}
