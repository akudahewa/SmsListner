package com.example.smsreader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;


public class MainActivity extends AppCompatActivity {

    private SMSLog broadcastReceiver = null;
    int SMS_PERMISSION_REQ_CODE_SUBMIT = 101;
    private String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int smsReceivePermission = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.RECEIVE_SMS);
        Log.i(TAG, "Self permission for Receive SMS :" + smsReceivePermission);
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.RECEIVE_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "Requesting permissions for RECEIVE_SMS");
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.RECEIVE_SMS},
                    SMS_PERMISSION_REQ_CODE_SUBMIT);
        }
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "Requesting permissions for SEND_SMS");
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.SEND_SMS},
                    1);
        }

        Intent backgroundService = new Intent(getApplicationContext(), MyService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(backgroundService);
        } else {
            startService(backgroundService);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(broadcastReceiver !=null){
            Log.i(TAG,"Unregistering broadcastReceiver onDestroy");
            unregisterReceiver(broadcastReceiver);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        if(broadcastReceiver!=null){
            Log.i(TAG,"Unregistering broadcastReceiver onPause");
            unregisterReceiver(broadcastReceiver);
        }


    }

}
