package com.example.j.applock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class checkPhoneBoot extends BroadcastReceiver {
    public checkPhoneBoot() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
            Intent service = new Intent(context, launchDetection.class);
            context.startService(service);
        }
    }
}
