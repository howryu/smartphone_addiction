package com.howryu.smartphone_addiction;

import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by howryu on 6/30/15.
 */
public class HomeFragment extends Fragment {

    private UsageStatsManager myStatsManager;
    private UsageQuerier myQuarier;
    RecyclerView.LayoutManager mLayoutManager;

    private UsageListAdapter mOneDayUsageListAdapter;
    private UsageListAdapter mOneWeekUsageListAdapter;
    private UsageListAdapter mOneMonthUsageListAdapter;
    RecyclerView mOneDayRecyclerView;
    RecyclerView mOneWeekRecyclerView;
    RecyclerView mOneMonthRecyclerView;





    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.home_fragment, container, false);

        mOneDayRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview_app_usage_one_day);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mOneDayRecyclerView.setLayoutManager(mLayoutManager);

        mOneDayUsageListAdapter = new UsageListAdapter();

        mOneDayRecyclerView.setAdapter(mOneDayUsageListAdapter);

        return rootView;
    }

    @Override
    public void onViewCreated(View rootView, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(rootView, savedInstanceState);

        myStatsManager = (UsageStatsManager) getActivity()
                .getSystemService("usagestats"); //Context.USAGE_STATS_SERVICE
        myQuarier = new UsageQuerier(myStatsManager);

        List<UsageStats> oneDayUsageStatsList;
        oneDayUsageStatsList = myQuarier.getUsageStatsInLastOneDay();

        updateAppsList(oneDayUsageStatsList, mOneDayUsageListAdapter, mOneDayRecyclerView);

//            Log.v("TEST", "List Size is " + Integer.toString(recentDayStatsList.size()));
//            for (UsageStats i : recentDayStatsList ){
//                Log.v("STATSLIST", i.getPackageName() + i.getTotalTimeInForeground() + " \n");
//            }

    }

    void updateAppsList(List<UsageStats> usageStatsList, UsageListAdapter adapter, RecyclerView mView) {
        List<CustomUsageStats> customUsageStatsList = new ArrayList<>();
        for (int i = 0; i < usageStatsList.size(); i++) {
            CustomUsageStats customUsageStats = new CustomUsageStats();

            customUsageStats.usageStats = usageStatsList.get(i);
            PackageManager pm = getActivity().getPackageManager();

            try {
                ApplicationInfo ai = pm.getApplicationInfo(customUsageStats.usageStats.getPackageName(), 0);
                customUsageStats.appName = (String) pm.getApplicationLabel(ai);
                Drawable appIcon = getActivity().getPackageManager()
                        .getApplicationIcon(customUsageStats.usageStats.getPackageName());
                customUsageStats.appIcon = appIcon;
            } catch (PackageManager.NameNotFoundException e) {
                //Log.w(TAG, String.format("App Icon is not found for %s",
                //        customUsageStats.usageStats.getPackageName()));
                customUsageStats.appIcon = getActivity()
                        .getDrawable(R.drawable.ic_launcher);
            }
            customUsageStatsList.add(customUsageStats);
        }
        adapter.setCustomUsageStatsList(customUsageStatsList);
        adapter.notifyDataSetChanged();
        mView.scrollToPosition(0);
    }
}
