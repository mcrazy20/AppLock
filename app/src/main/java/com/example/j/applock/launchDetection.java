package com.example.j.applock;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.IBinder;
import android.os.Looper;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class launchDetection extends Service {
    private boolean checked = false;
    public launchDetection() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
    public int onStartCommand(Intent intent, int flags, int startId) {
        boolean check = true;

        while (check) {
            // Return a list of the tasks that are currently running,
            // with the most recent being first and older ones after in order.
            // Taken 1 inside getRunningTasks method means want to take only
            // top activity from stack and forgot the olders.
            ActivityManager am = (ActivityManager) getBaseContext().getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);

            String currentRunningActivityName = taskInfo.get(0).topActivity.getClassName();
            Log.e("TEST5", currentRunningActivityName);

            if (currentRunningActivityName.contains("com.spotify") && !checked) {
                Toast.makeText(getApplicationContext(), "FOUND SPOTIFY YO", Toast.LENGTH_LONG);
                check = false;
                displayAlert();
            }
            else if (checked)
            {
                try {
                    Thread.sleep(3600000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return START_STICKY;

    }

    private void displayAlert()
    {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(R.string.lock_title);
        dialog.setMessage(R.string.lock_dialog);
        final EditText input = new EditText(this);
        input.setRawInputType(
                InputType.TYPE_CLASS_PHONE|
                        InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        dialog.setView(input);
        dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String test = input.getText().toString();
                if (!test.equals("1234"))
                {
                    displayAlert();
                }
                else
                {
                    checked = true;
                }

            }
        });
        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                displayAlert();
            }
        });
        AlertDialog dlg = dialog.create();
        dlg.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        dlg.show();
        //dlg.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
    }
}
