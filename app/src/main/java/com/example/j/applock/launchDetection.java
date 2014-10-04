package com.example.j.applock;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class launchDetection extends Service {
    private boolean checked = false;
    boolean inHandler = false;
    String pin = "";
    public launchDetection() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        /*Bundle extras = intent.getExtras();
        if (extras == null)
        {*/
            SharedPreferences shared = this.getSharedPreferences("com.example.j.applock", Context.MODE_PRIVATE);
            pin = shared.getString("pin", "1234");
       /* }
        else
        {
            pin = extras.getString("pin");
        }*/
        final Handler handler = new Handler(){

            @Override
            public void close() {
                inHandler=true;
                final AlertDialog.Builder dialog = new AlertDialog.Builder(getBaseContext());
                dialog.setTitle(R.string.lock_title);
                dialog.setMessage(R.string.lock_dialog);
                final EditText input = new EditText(getBaseContext());
                input.setRawInputType(
                        InputType.TYPE_CLASS_PHONE |
                                InputType.TYPE_NUMBER_VARIATION_PASSWORD);
                dialog.setView(input);
                dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String test = input.getText().toString();
                        if (!test.equals(pin))
                        {
                            close();
                        }
                        else
                        {
                            checked = true;
                        }
                        inHandler=false;

                    }
                });
                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        inHandler=false;
                        Intent intent = new Intent(getApplicationContext(), noAppAccess.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                });
                AlertDialog dlg = dialog.create();
                dlg.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
                dlg.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                dlg.show();
            }

            @Override
            public void flush() {

            }

            @Override
            public void publish(LogRecord logRecord) {

            }
        };
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
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

                    if (currentRunningActivityName.contains("com.spotify") && !checked && !inHandler) {
                        //Toast.makeText(getApplicationContext(), "FOUND SPOTIFY YO", Toast.LENGTH_LONG);
                        check = false;
                        handler.close();
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
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Looper.loop();
            }
        }).start();

        return START_STICKY;

    }
}
