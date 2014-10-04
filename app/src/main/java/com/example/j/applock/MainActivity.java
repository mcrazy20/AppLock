package com.example.j.applock;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
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
                // TODO Auto-generated method stub

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


/*
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
    }*/
}
