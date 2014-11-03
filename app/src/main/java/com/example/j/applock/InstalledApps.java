package com.example.j.applock;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.util.Log;

        import java.util.ArrayList;
        import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Protiva Rahman on 10/3/2014.
 */
public class InstalledApps {
    ArrayList<String> getInstalledApp(Context c) {
        ArrayList<String> result = new ArrayList<String>();
       
        List<PackageInfo> packs = c.getPackageManager().getInstalledPackages(0);

        List<ResolveInfo> appList;
        for (int i = 0; i < packs.size(); i++) {
            PackageInfo p = packs.get(i);
            String s = p.applicationInfo.loadLabel(c.getPackageManager()).toString();

            //Log.e("test", s);
            //System.out.println(s);
            result.add(s);
        }
        return result;
    }

    public HashMap<String,String> getActivityName(Context c) {
        HashMap<String,String> res=new HashMap<String, String>();
        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        PackageManager pm = c.getPackageManager();
        List<ResolveInfo> appList = pm.queryIntentActivities(mainIntent, 0);
        Collections.sort(appList, new ResolveInfo.DisplayNameComparator(pm));

        for (ResolveInfo temp : appList) {
            String name = temp.loadLabel(pm).toString();
            String act=temp.activityInfo.packageName;
            res.put(name,act);
//Log.v("my logs", "package and activity name and application name= "+ temp.activityInfo.packageName + "    "+ temp.activityInfo.name + " " + name);


        }
        return res;
    }


}