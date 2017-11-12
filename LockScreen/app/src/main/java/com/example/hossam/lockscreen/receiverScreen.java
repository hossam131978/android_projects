package com.example.hossam.lockscreen;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by hossa on 03-Sep-17.
 */
public class receiverScreen extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context,final Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)){

            Toast.makeText(context, "ACTION_SCREEN_ON  " , Toast.LENGTH_SHORT).show();
           //context.startActivity(new Intent(context,MainActivity.class));
           //  context.startService(new Intent(context,LockScreenService.class));
            startLockScreenActivity(context,intent);
        }
        if ( intent.getAction().equals(Intent.ACTION_SCREEN_OFF)){
            Toast.makeText(context, "ACTION_SCREEN_OFF", Toast.LENGTH_SHORT).show();

        //    startLockScreenActivity(context,intent);
        }


        if (intent.getAction().equals(Intent.ACTION_USER_PRESENT)){
            Toast.makeText(context, "ACTION_USER_PRESENT", Toast.LENGTH_SHORT).show();


        }
    }

    public void startLockScreenActivity(final Context context,final Intent intent){

        Intent i = new Intent(context.getApplicationContext(), LockScreenActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra("fromBroadcast",true);
        context.startActivity(i);
    }


}