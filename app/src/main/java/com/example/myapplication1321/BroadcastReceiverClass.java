package com.example.myapplication1321;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class BroadcastReceiverClass extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Broadcast received", Toast.LENGTH_SHORT).show();

    }
}
