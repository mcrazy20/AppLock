package com.example.j.applock;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Looper;
import android.util.Log;

import java.util.List;

class CheckRunningActivity extends Thread {
    ActivityManager am = null;
    Context context = null;

    public CheckRunningActivity(Context con) {
        context = con;
        am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
    }

    public void run() {
       /*Looper.prepare();
        boolean check = true;
        while (check) {
            // Return a list of the tasks that are currently running,
            // with the most recent being first and older ones after in order.
            // Taken 1 inside getRunningTasks method means want to take only
            // top activity from stack and forgot the olders.
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);

            String currentRunningActivityName = taskInfo.get(0).topActivity.getClassName();
            Log.e("TEST6", currentRunningActivityName);

            if (currentRunningActivityName.equals("PACKAGE_NAME.ACTIVITY_NAME")) {
                // show your activity here on top of PACKAGE_NAME.ACTIVITY_NAME
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Looper.loop();*/
    }
}