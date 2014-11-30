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
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by A.J on 11/26/2014.
 */
public class changePin extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_pin);

        final SharedPreferences shared = this.getSharedPreferences("com.example.j.applock", Context.MODE_PRIVATE);
        final String pin_facebook = shared.getString("pin_facebook","1234");
        final String pin_messaging = shared.getString("pin_messaging","1234");
        final String pin_gallery = shared.getString("pin_gallery","1234");
        final ListView listView = (ListView) findViewById(R.id.app_change_pin_list);
        String[] values = new String[] { "AppLock Navigating","Facebook", "Messaging", "Gallery"};
        final ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < values.length; i++){
            list.add(values[i]);
        }
        final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String item = (String) parent.getItemAtPosition(position);
                //final AlertDialog.Builder dialog = new AlertDialog.Builder(getBaseContext());
                if (item.equals("AppLock Navigating")) {
                    //final Intent intent = new Intent(this, changePin.class);
                    //startActivity(intent);

                    final AlertDialog.Builder dialog = new AlertDialog.Builder(getBaseContext());
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
                                //After entering correct password, allows them to enter a new one. Saves to sharedpreferences
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
                                            Intent intent = getBaseContext().getPackageManager()
                                                    .getLaunchIntentForPackage( getBaseContext().getPackageName() );
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            startActivity(intent);
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
                            }else {
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
                else if (item.equals("Facebook")) {
                    final AlertDialog.Builder dialog = new AlertDialog.Builder(getBaseContext());

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
                            if (test.equals(pin_facebook)) {
                                //After entering correct password, allows them to enter a new one. Saves to sharedpreferences
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
                                            myEdit.putString("pin_facebook", innertest);

                                            myEdit.commit();
                                            Intent intent = getBaseContext().getPackageManager()
                                                    .getLaunchIntentForPackage( getBaseContext().getPackageName() );
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            startActivity(intent);
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
                else if (item.equals("Messaging")) {
                    final AlertDialog.Builder dialog = new AlertDialog.Builder(getBaseContext());

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
                            if (test.equals(pin_messaging)) {
                                //After entering correct password, allows them to enter a new one. Saves to sharedpreferences
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
                                            myEdit.putString("pin_messaging", innertest);

                                            myEdit.commit();
                                            Intent intent = getBaseContext().getPackageManager()
                                                    .getLaunchIntentForPackage( getBaseContext().getPackageName() );
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            startActivity(intent);
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
                            }else {
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
                else if (item.equals("Gallery")) {
                    //final Intent intent = new Intent(this, changePin.class);
                    //startActivity(intent);

                    final AlertDialog.Builder dialog = new AlertDialog.Builder(getBaseContext());
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
                                //After entering correct password, allows them to enter a new one. Saves to sharedpreferences
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
                                            myEdit.putString("pin_gallery", innertest);

                                            myEdit.commit();
                                            Intent intent = getBaseContext().getPackageManager()
                                                    .getLaunchIntentForPackage( getBaseContext().getPackageName() );
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            startActivity(intent);
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
                            }else {
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

        });

    }
}
