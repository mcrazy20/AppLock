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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

import java.util.List;

import static android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends Activity {

    List<String> li;

    @Override
    public void onCreate(Bundle savedInstanceState) {
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
        final DatabaseHandler db = new DatabaseHandler(getApplicationContext());
        final RelativeLayout rl = (RelativeLayout) findViewById(R.id.rl);
        final RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams
                ((int) LayoutParams.WRAP_CONTENT, (int) LayoutParams.WRAP_CONTENT);
        params.leftMargin = 10;
        params.topMargin = 150;


        Button show = (Button) findViewById(R.id.button1);
        final ListView list = new ListView(this);
        InstalledApps a = new InstalledApps();
        li = a.getInstalledApp(getApplicationContext());

        show.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                ArrayAdapter<String> adp = new ArrayAdapter<String>(getBaseContext(),
                        android.R.layout.simple_dropdown_item_1line, li);
                adp.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

                list.setAdapter(adp);
                list.setLayoutParams(params);

                rl.addView(list);
                list.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        String app_name = li.get(i);
                        if (db.isInDB(app_name))
                            db.delete(app_name);
                        else
                            db.addApp(app_name);

                    }
                });

            }


        });
        // db.getApps();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    public void changePin(View V) {
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
                    final AlertDialog.Builder innerdialog = new AlertDialog.Builder(getBaseContext());
                    innerdialog.setTitle(R.string.change_title2);
                    innerdialog.setMessage(R.string.change_dialog2);
                    final EditText input2 = new EditText(getBaseContext());
                    input2.setRawInputType(
                            InputType.TYPE_CLASS_PHONE |
                                    InputType.TYPE_NUMBER_VARIATION_PASSWORD);
                    innerdialog.setView(input2);
                    innerdialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String innertest = input2.getText().toString();
                            if (innertest.length() > 0) {
                                SharedPreferences.Editor myEdit = shared.edit();
                                Log.e("TEST5", innertest);
                                myEdit.putString("pin", innertest);

                                myEdit.commit();
                                Toast.makeText(getApplicationContext(), "Restart App to take Affect", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                    innerdialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    AlertDialog dlg2 = innerdialog.create();
                    dlg2.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
                    dlg2.show();

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
