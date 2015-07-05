package com.howryu.smartphone_addiction;

import android.annotation.TargetApi;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.util.Log;

import java.util.Calendar;
import java.util.List;

/**
 * Created by howryu on 7/1/15.
 */
public class UsageQuerier {

    private UsageStatsManager myStatsManger;

    UsageQuerier(UsageStatsManager statsManager){
        myStatsManger = statsManager;
    }

    @TargetApi(21)
    public List<UsageStats> getUsageStatsInOneDay(){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -1);

        List<UsageStats> stats = myStatsManger.queryUsageStats(UsageStatsManager.INTERVAL_YEARLY,
                cal.getTimeInMillis(), System.currentTimeMillis());
        //Log.v("Quarier", "BeginTime " + Long.toString(beginTime) + "\n");
        //Log.v("Quarier", "EndTime " + Long.toString(currentTime) + "\n");
        Log.v("Quarier", "ListSize " + Integer.toString(stats.size()));
        return stats;
    }

    private long calculateBeginTime(int intervalType){
        if (intervalType == UsageStatsManager.INTERVAL_DAILY){
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -1);
            return cal.getTimeInMillis();
        }
        else{
            return 0;
        }
    }


}
