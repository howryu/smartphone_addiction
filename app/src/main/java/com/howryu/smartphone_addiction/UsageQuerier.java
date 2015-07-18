package com.howryu.smartphone_addiction;

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

    public List<UsageStats> getUsageStatsInLastOneDay(){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);

        List<UsageStats> stats = myStatsManger.queryUsageStats(UsageStatsManager.INTERVAL_DAILY,
                cal.getTimeInMillis(), System.currentTimeMillis());
        //Log.v("Quarier", "BeginTime " + Long.toString(beginTime) + "\n");
        //Log.v("Quarier", "EndTime " + Long.toString(currentTime) + "\n");
        Log.v("Quarier", "ListSize " + Integer.toString(stats.size()));
        return stats;
    }

    public List<UsageStats> getUsageStatsInLastOneWeek(){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -7);

        List<UsageStats> stats = myStatsManger.queryUsageStats(UsageStatsManager.INTERVAL_DAILY,
                cal.getTimeInMillis(), System.currentTimeMillis());
        return stats;
    }

    public List<UsageStats> getUsageStatsInLastOneMonth(){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -30);

        List<UsageStats> stats = myStatsManger.queryUsageStats(UsageStatsManager.INTERVAL_DAILY,
                cal.getTimeInMillis(), System.currentTimeMillis());
        return stats;
    }
}
