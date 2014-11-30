package com.example.j.applock;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;
import java.util.List;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;


import static android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends Activity {
    public static boolean facebook;
    public static boolean messaging;
    public static boolean gallery;
    public static int lockoutTries;
    public static int lockoutTime;
    List<String> li;
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

        Button b = (Button) findViewById(R.id.messaging);
        if (messaging)
        {
            b.setBackgroundColor(Color.GREEN);
        }
        else
        {
            b.setBackgroundColor(Color.GRAY);
        }
        b = (Button) findViewById(R.id.gallery);
        if (gallery)
        {
            b.setBackgroundColor(Color.GREEN);
        }
        else
        {
            b.setBackgroundColor(Color.GRAY);
        }
        b = (Button) findViewById(R.id.facebook);
        if (facebook)
        {
            b.setBackgroundColor(Color.GREEN);
        }
        else
        {
            b.setBackgroundColor(Color.GRAY);
        }

        Intent intent = new Intent(this, launchDetection.class);
        intent.putExtra("pin", pin);
        startService(intent);
        intent = new Intent(this, launchDetectionG.class);
        intent.putExtra("pin", pin);
        startService(intent);
        intent = new Intent(this, launchDetectionF.class);
        intent.putExtra("pin", pin);
        startService(intent);

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

    public void lockFacebook(View V)
    {
        facebook = !facebook;

        final SharedPreferences shared = this.getSharedPreferences("com.example.j.applock", Context.MODE_PRIVATE);
        SharedPreferences.Editor myEdit = shared.edit();
        myEdit.putBoolean("facebook", facebook);
        myEdit.commit();
        Button b = (Button) findViewById(R.id.facebook);
        if (facebook)
        {
            b.setBackgroundColor(Color.GREEN);
        }
        else
        {
            b.setBackgroundColor(Color.GRAY);
        }
    }
    public void lockMessaging(View V)
    {
        messaging = !messaging;

        final SharedPreferences shared = this.getSharedPreferences("com.example.j.applock", Context.MODE_PRIVATE);
        SharedPreferences.Editor myEdit = shared.edit();
        myEdit.putBoolean("messaging", messaging);
        myEdit.commit();

        Button b = (Button) findViewById(R.id.messaging);
        if (messaging)
        {
            b.setBackgroundColor(Color.GREEN);
        }
        else
        {
            b.setBackgroundColor(Color.GRAY);
        }
    }
    public void lockGallery(View V)
    {
        gallery = !gallery;

        final SharedPreferences shared = this.getSharedPreferences("com.example.j.applock", Context.MODE_PRIVATE);
        SharedPreferences.Editor myEdit = shared.edit();
        myEdit.putBoolean("gallery", gallery);
        myEdit.commit();

        Button b = (Button) findViewById(R.id.gallery);
        if (gallery)
        {
            b.setBackgroundColor(Color.GREEN);
        }
        else
        {
            b.setBackgroundColor(Color.GRAY);
        }
    }

    public void setLock(View V)
    {
        Intent i = getBaseContext().getPackageManager()
                .getLaunchIntentForPackage( getBaseContext().getPackageName() );
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }
}
