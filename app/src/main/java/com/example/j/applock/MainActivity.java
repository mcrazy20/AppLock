package com.example.j.applock;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends Activity {
    public static boolean facebook;
    public static boolean messaging;
    public static boolean gallery;
    public static int lockoutTries;
    public static int lockoutTime;
    String pin;
    String pin_facebook;
    String pin_messaging;
    String pin_gallery;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //This gets the PIN number and starts the service that will check for the running app.
        SharedPreferences shared = this.getSharedPreferences("com.example.j.applock", Context.MODE_PRIVATE);

        pin = shared.getString("pin", "1234");
        pin_facebook = shared.getString("pin_facebook","1234");
        pin_messaging = shared.getString("pin_messaging","1234");
        pin_gallery = shared.getString("pin_gallery","1234");
        facebook = shared.getBoolean("facebook", false);
        messaging = shared.getBoolean("messaging", false);
        gallery = shared.getBoolean("gallery", false);
        lockoutTries = shared.getInt(getString(R.string.lockout_tries), 5);
        lockoutTime = shared.getInt(getString(R.string.lockout_time), 300000);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intent = new Intent(this, settings.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    //Code for changing the pin code
    public void changePin(View V) {
        final Intent intent = new Intent(this, changePin.class);
        final AlertDialog.Builder dialog = new AlertDialog.Builder(getBaseContext());
        final SharedPreferences shared = this.getSharedPreferences("com.example.j.applock", Context.MODE_PRIVATE);
        final String pin = shared.getString("pin", "1234");
        //Creating an alert dialog
        dialog.setTitle(R.string.change_title1);
        dialog.setMessage(R.string.change_dialog1);

        //Allows them to use phone keyboard
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
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Incorrect Pin", Toast.LENGTH_LONG).show();
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

    public void setLock(View V)
    {
        final Intent intent = new Intent(this, setLocks.class);
        final AlertDialog.Builder dialog = new AlertDialog.Builder(getBaseContext());
        final SharedPreferences shared = this.getSharedPreferences("com.example.j.applock", Context.MODE_PRIVATE);
        final String pin = shared.getString("pin", "1234");
        //Creating an alert dialog
        dialog.setTitle(R.string.change_title1);
        dialog.setMessage(R.string.change_dialog1);

        //Allows them to use phone keyboard
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
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Incorrect Pin", Toast.LENGTH_LONG).show();
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
