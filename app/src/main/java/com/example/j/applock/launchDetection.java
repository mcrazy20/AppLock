package com.example.j.applock;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Looper;
import android.text.InputType;
import android.text.format.Time;
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
    public static boolean facebook;
    public static boolean messaging;
    public static boolean gallery;
    boolean inHandler = false;
    String pin = "";
    int numberOfAttempts = 0;
    int numberOfAllowableAttempts = 5;
    int msecondsToSleep = 3600000;
    boolean canEnter = true;
    long stopTime;
    public launchDetection() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public int onStartCommand(Intent intent, int flags, int startId) {

        SharedPreferences shared = this.getSharedPreferences("com.example.j.applock", Context.MODE_PRIVATE);
        facebook = shared.getBoolean("facebook", false);
        messaging = shared.getBoolean("messaging", false);
        gallery = shared.getBoolean("gallery", false);
        pin = shared.getString("pin", "1234");
        Log.d("PINNUMBER", pin);

        //Handler is called from the service thread, brings up the alertdialog (google no like this)
        final Handler handler = new Handler(){

            @Override
            public void close() {
                inHandler=true;
                final AlertDialog.Builder builder = new AlertDialog.Builder(getBaseContext());
                builder.setTitle("How long to unlock?");
                builder.setItems(new CharSequence[]
                                {"5 minutes", "30 minutes", "1 hour", "4 hours"},
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // The 'which' argument contains the index position
                                // of the selected item
                                switch (which) {
                                    case 0:
                                        msecondsToSleep=300000;
                                        break;
                                    case 1:
                                        msecondsToSleep=1800000;
                                        break;
                                    case 2:
                                        msecondsToSleep=3600000;
                                        break;
                                    case 3:
                                        msecondsToSleep=14400000;
                                        break;
                                }
                            }
                        });
                final AlertDialog timeSelect = builder.create();
                timeSelect.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
                timeSelect.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
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
                        if (!test.equals(pin)) {

                            if (numberOfAttempts >= numberOfAllowableAttempts) {
                                canEnter = false;
                                stopTime = System.currentTimeMillis() + 60000;
                                numberOfAttempts = 0;
                                Intent intent = new Intent(getApplicationContext(), noAppAccess.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                //Intent intent = new Intent(Intent.ACTION_MAIN);
                                //intent.addCategory(Intent.CATEGORY_HOME);
                                //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                //startActivity(intent);
                                //ExitDialogFragment exitDialog = new ExitDialogFragment();
                                //exitDialog.show(exitDialog.getFragmentManager(), "exitdialog");
                            } else {
                                numberOfAttempts++;
                                close();
                            }
                        } else {
                            numberOfAttempts = 0;
                            checked = true;
                            timeSelect.show();
                        }
                        inHandler = false;

                    }
                });
                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        inHandler = false;
                        Intent intent = new Intent(getApplicationContext(), noAppAccess.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                });
                AlertDialog dlg = dialog.create();

                //Allows alertdialog to be on top of any activity
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

                    if (((currentRunningActivityName.contains("Gallery") && gallery) || (currentRunningActivityName.contains("facebook") && facebook) ||(currentRunningActivityName.contains("android.mms") && messaging))
                            && !checked && !inHandler) {
                        //Toast.makeText(getApplicationContext(), "FOUND SPOTIFY YO", Toast.LENGTH_LONG);
                        if (!canEnter){
                            if(System.currentTimeMillis() < stopTime){
                                inHandler = false;
                                Intent intent = new Intent(getApplicationContext(), noAppAccess.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                break;
                            } else {
                                canEnter = true;
                            }
                        } if (canEnter) {
                            check = false;
                            handler.close();
                        }
                    }
                    else if (checked)
                    {
                        try {
                            Thread.sleep(msecondsToSleep);
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

class ExitDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Entered Too Many Passwords");
        builder.setMessage("Try again in 5 minutes");
        builder.setNegativeButton("Ok", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        return builder.create();
    }
}