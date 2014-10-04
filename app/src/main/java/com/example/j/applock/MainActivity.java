package com.example.j.applock;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*ActivityManager am = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningAppProcessInfo = am.getRunningAppProcesses();

        Log.e("MAIN", runningAppProcessInfo.toString());*/
        SharedPreferences shared = this.getSharedPreferences("com.example.j.applock", Context.MODE_PRIVATE);
        String pin = shared.getString("pin", "1234");
        Intent intent = new Intent(this, launchDetection.class);
        intent.putExtra("pin", pin);
        startService(intent);
        //new CheckRunningActivity(getBaseContext()).start();



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void changePin(View V)
    {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(getBaseContext());
        final SharedPreferences shared = this.getSharedPreferences("com.example.j.applock", Context.MODE_PRIVATE);
        final String pin = shared.getString("pin", "1234");
        dialog.setTitle(R.string.change_title1);
        dialog.setMessage(R.string.change_dialog1);
        final EditText input = new EditText(getBaseContext());
        input.setRawInputType(
                InputType.TYPE_CLASS_PHONE |
                        InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        dialog.setView(input);
        dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String test = input.getText().toString();
                if (test.equals(pin)) {
                    final AlertDialog.Builder dialog = new AlertDialog.Builder(getBaseContext());
                    dialog.setTitle(R.string.change_title2);
                    dialog.setMessage(R.string.change_dialog2);
                    final EditText input = new EditText(getBaseContext());
                    input.setRawInputType(
                            InputType.TYPE_CLASS_PHONE |
                                    InputType.TYPE_NUMBER_VARIATION_PASSWORD);
                    dialog.setView(input);
                    dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if (input.toString().length()>0)
                            {
                                shared.edit().putString("pin", input.toString());
                                recreate();
                            }
                        }
                    });
                    dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    AlertDialog dlg = dialog.create();
                    dlg.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
                    dlg.show();

                }

            }
        });
        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog dlg = dialog.create();
        dlg.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        dlg.show();
    }
}
