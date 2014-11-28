package com.example.j.applock;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by A.J on 11/26/2014.
 */
public class settings extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        final String lockoutTriesArray[] = { "3", "5", "10", "20"};
        final String lockoutTimeArray[] = { "1 minute", "5 minutes", "30 minutes", "60 minutes"};
        final SharedPreferences shared = this.getSharedPreferences("com.example.j.applock", Context.MODE_PRIVATE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        final ListView listView = (ListView) findViewById(R.id.setting_list);
        String[] values = new String[] { "Number of Tries Before Lockout", "Lockout Time"};
        final ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < values.length; i++){
            list.add(values[i]);
        }
        final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);
        //final SharedPreferences shared = this.getSharedPreferences("com.example.j.applock", Context.MODE_PRIVATE);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String item = (String) parent.getItemAtPosition(position);
                if (item.equals(getString(R.string.lockout_tries))){
                    class NumTriesDialog extends DialogFragment{
                        //SharedPreferences shared = getActivity().getSharedPreferences("com.example.j.applock", Context.MODE_PRIVATE);
                        int lockoutTries = 5;
                                //shared.getInt(getString(R.string.lockout_tries), 5);
                        @Override
                        public Dialog onCreateDialog(Bundle savedInstanceState){
                            int choice;
                            switch (lockoutTries){
                                case 3:
                                    choice = 0;
                                    break;
                                case 10:
                                    choice = 2;
                                    break;
                                case 20:
                                    choice = 3;
                                    break;
                                default:
                                    choice = 1;
                                    break;
                            }
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setTitle(R.string.lockout_tries);
                            builder.setSingleChoiceItems(lockoutTriesArray,-1,
                                    new DialogInterface.OnClickListener(){

                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            lockoutTries = Integer.parseInt(lockoutTriesArray[i]);
                                        }
                                    });
                            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener(){

                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    SharedPreferences.Editor editor = shared.edit();
                                    editor.putInt(getString(R.string.lockout_tries), lockoutTries);
                                    editor.commit();
                                    Intent intent = getBaseContext().getPackageManager()
                                            .getLaunchIntentForPackage( getBaseContext().getPackageName() );
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                }
                            });
                            return builder.create();
                        }

                    }
                    DialogFragment dlg = new NumTriesDialog();
                    dlg.show(getFragmentManager(), "num_tries_dialog");
                } else if (item.equals(getString(R.string.lockout_time))){
                    class TimeDialog extends DialogFragment{
                        //SharedPreferences shared = getActivity().getSharedPreferences("com.example.j.applock", Context.MODE_PRIVATE);
                        int lockoutTime = 300000;
                                //shared.getInt(getString(R.string.lockout_time), 300000);
                        @Override
                        public Dialog onCreateDialog(Bundle savedInstanceState){
                            int choice;
                            switch (lockoutTime){
                                case 60000:
                                    choice = 0;
                                    break;
                                case 1800000:
                                    choice = 2;
                                    break;
                                case 3600000:
                                    choice = 3;
                                    break;
                                default:
                                    choice = 1;
                                    break;
                            }
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setTitle(R.string.lockout_time);
                            builder.setSingleChoiceItems(lockoutTimeArray,-1,
                                    new DialogInterface.OnClickListener(){

                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            switch(i){
                                                case 0:
                                                    lockoutTime = 60000;
                                                    break;
                                                case 1:
                                                    lockoutTime = 300000;
                                                    break;
                                                case 2:
                                                    lockoutTime = 1800000;
                                                    break;
                                                case 3:
                                                    lockoutTime = 3600000;
                                                    break;
                                            }
                                        }
                                    });
                            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener(){

                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    SharedPreferences.Editor editor = shared.edit();
                                    editor.putInt(getString(R.string.lockout_time), lockoutTime);
                                    editor.commit();
                                    Intent intent = getBaseContext().getPackageManager()
                                            .getLaunchIntentForPackage( getBaseContext().getPackageName() );
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                }
                            });
                            return builder.create();
                        }
                    }
                    DialogFragment dlg = new TimeDialog();
                    dlg.show(getFragmentManager(), "num_tries_dialog");
                }
            }
        });

    }
}
