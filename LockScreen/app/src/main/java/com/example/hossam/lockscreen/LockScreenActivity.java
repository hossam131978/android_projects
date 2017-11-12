package com.example.hossam.lockscreen;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class LockScreenActivity extends AppCompatActivity {

    public  static  boolean version_23_overlay_permission=false;
    public final static int OVERLAY_PERMISSION_REQUEST_CODE = 10;
    public WindowManager wm;
    public LinearLayout li;
    public View view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_main);
        //  registerReceiver();
        Toast.makeText(this, "lock screen activity starts", Toast.LENGTH_SHORT).show();

        if (Build.VERSION.SDK_INT<23){
             drawWindowManager();
        }
        else{
            get23OverlayPermission();
            if(version_23_overlay_permission)
            {     drawWindowManager();     }

            else {
                Toast.makeText(this, "permission not allowed", Toast.LENGTH_LONG).show();
            }
        }
    }


    // create window manager and attach it to screen
    private void drawWindowManager() {
        Toast.makeText(this, "window manager  started", Toast.LENGTH_LONG).show();
        wm=(WindowManager)getApplicationContext().getSystemService(WINDOW_SERVICE);
        final   WindowManager.LayoutParams wmlp=new WindowManager.LayoutParams();
        final LayoutInflater li=(LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
          view=li.inflate(R.layout.layout_lock_screen,null);
        view.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

        wmlp.height=WindowManager.LayoutParams.MATCH_PARENT;
        wmlp.width=WindowManager.LayoutParams.MATCH_PARENT;
        wmlp.type=  WindowManager.LayoutParams.TYPE_PHONE ;
        wmlp.flags= WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
        // |WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
        ;
       // wmlp.format = PixelFormat.TRANSPARENT;
        wmlp.gravity= Gravity.CENTER|Gravity.CENTER;

        wm.addView(view,wmlp);
        Toast.makeText(this, "window manager  finished", Toast.LENGTH_LONG).show();

    }

    //check if the permission is granted from the user
    @TargetApi(Build.VERSION_CODES.M | Build.VERSION_CODES.N |Build.VERSION_CODES.N_MR1|Build.VERSION_CODES.O)
    public void checkDrawOverlayPermission() {
        if (Settings.canDrawOverlays(this))
            version_23_overlay_permission=true;
        else
            version_23_overlay_permission=false;
    }

    //get overlay permission to level more than 22 (23 and above)
    private void get23OverlayPermission() {
        checkDrawOverlayPermission();
        if(!version_23_overlay_permission)
        {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, OVERLAY_PERMISSION_REQUEST_CODE);


        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Toast.makeText(this, "no permission : "+grantResults[0]+" : "+ PackageManager.PERMISSION_GRANTED, Toast.LENGTH_SHORT).show();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode,  Intent data) {
        Toast.makeText(this, "on activity result ", Toast.LENGTH_SHORT).show();

        {
            if (requestCode == OVERLAY_PERMISSION_REQUEST_CODE) {
                Toast.makeText(this, "on activity result code enter ", Toast.LENGTH_SHORT).show();

                checkDrawOverlayPermission();
                if(!version_23_overlay_permission)
                    Toast.makeText(this, "please enter permission ", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "on destroy  lockscreen activity", Toast.LENGTH_SHORT).show();
    }

    public    int buttons_clicked=0;
    public    int button1_clicked=0;
    public    int button2_clicked=0;
    public    int button3_clicked=0;

    public void ButtonClicked(View view2) {
        Button bt=(Button)view2;
        if ((bt.getId()==R.id.button1)&&(button1_clicked==0))
        {
            bt.setBackgroundColor(ContextCompat.getColor(this , R.color.colorGreen));
            buttons_clicked++;
            button1_clicked=1;
        }
        else
        {
            if ((bt.getId()==R.id.button2)&&(button2_clicked==0))
            {
                bt.setBackgroundColor(ContextCompat.getColor(this , R.color.colorGreen));
                buttons_clicked++;
                button2_clicked=1;
            }
            else
            {
                if ((bt.getId()==R.id.button3)&&(button3_clicked==0))
                {
                    bt.setBackgroundColor(ContextCompat.getColor(this , R.color.colorGreen));
                    buttons_clicked++;
                    button3_clicked=1;
                }

            }
        }


        if (buttons_clicked==3){
            try{
                wm.removeView(view);
                LockScreenActivity.this.finish();
            }
            catch (Exception e){


                Toast.makeText(this, "exception :: "+ e.getMessage(), Toast.LENGTH_LONG).show();}


           // startLockScreenService();
             }
    }

}
