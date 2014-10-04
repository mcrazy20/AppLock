package com.example.j.applock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class checkPhoneBoot extends BroadcastReceiver {
    public checkPhoneBoot() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.

        //Broadcast receiver that starts running our service after the phone boots
        if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
            Intent service = new Intent(context, launchDetection.class);
            SharedPreferences shared = context.getSharedPreferences("com.example.j.applock", Context.MODE_PRIVATE);
            String pin = shared.getString("pin", "1234");
            service.putExtra("pin", pin);
            context.startService(service);
        }
    }
}
