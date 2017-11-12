package com.example.hossam.lockscreen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    final String password_directory="password";
    final String password_file="password";
    FileIoExtensions fx=new FileIoExtensions(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }



    public void passwordButtons(View view) {

            Toast.makeText(this, "password Buttons", Toast.LENGTH_SHORT).show();
            startService(new Intent(this,LockScreenService.class));
    }



    public void passwordPassword(View view) {
        Toast.makeText(this, "password passwordPassword", Toast.LENGTH_SHORT).show();

    }
    public void stopLockScreen(View view) {
        stopService(new Intent(this,LockScreenService.class));
        Toast.makeText(this, "lock screen stops", Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "on destroy main activity", Toast.LENGTH_SHORT).show();
    }


}

    
