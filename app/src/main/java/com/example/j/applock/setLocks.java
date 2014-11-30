package com.example.j.applock;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by A.J on 11/30/2014.
 */
public class setLocks extends Activity {
    public static boolean facebook;
    public static boolean messaging;
    public static boolean gallery;
    public static String pin;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_locks);

        SharedPreferences shared = this.getSharedPreferences("com.example.j.applock", Context.MODE_PRIVATE);
        facebook = shared.getBoolean("facebook", false);
        messaging = shared.getBoolean("messaging", false);
        gallery = shared.getBoolean("gallery", false);
        pin = shared.getString("pin", "1234");

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
