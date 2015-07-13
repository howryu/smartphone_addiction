package com.howryu.smartphone_addiction;

import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
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
    private UsageListAdapter mUsageListAdapter;
    RecyclerView mRecyclerView;
    private List<UsageStats> recentDayStatsList;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.home_fragment, container, false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview_app_usage);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mUsageListAdapter = new UsageListAdapter();

        mRecyclerView.setAdapter(mUsageListAdapter);

        return rootView;
    }

    @Override
    public void onViewCreated(View rootView, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(rootView, savedInstanceState);

        myStatsManager = (UsageStatsManager) getActivity()
                .getSystemService("usagestats"); //Context.USAGE_STATS_SERVICE

        myQuarier = new UsageQuerier(myStatsManager);

        recentDayStatsList = myQuarier.getUsageStatsInOneDay();

        updateAppsList(recentDayStatsList);

        Log.v("TEST", "List Size is " + Integer.toString(recentDayStatsList.size()));
        for (UsageStats i : recentDayStatsList ){
            Log.v("STATSLIST", i.getPackageName() + i.getTotalTimeInForeground() + " \n");
        }

    }

    void updateAppsList(List<UsageStats> usageStatsList) {
        List<CustomUsageStats> customUsageStatsList = new ArrayList<>();
        for (int i = 0; i < usageStatsList.size(); i++) {
            CustomUsageStats customUsageStats = new CustomUsageStats();
            customUsageStats.usageStats = usageStatsList.get(i);
            try {
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
        mUsageListAdapter.setCustomUsageStatsList(customUsageStatsList);
        mUsageListAdapter.notifyDataSetChanged();
        mRecyclerView.scrollToPosition(0);
    }
}
