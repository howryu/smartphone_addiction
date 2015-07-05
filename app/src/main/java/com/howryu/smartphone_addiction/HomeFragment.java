package com.howryu.smartphone_addiction;

import android.annotation.TargetApi;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;

import java.util.List;

/**
 * Created by howryu on 6/30/15.
 */
public class HomeFragment extends Fragment {

    private UsageStatsManager myStatsManager;
    private UsageQuerier myQuarier;
    private List<UsageStats> recentDayStatsList;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_fragment, container, false);
    }

    @Override
    @TargetApi(21)
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int position = FragmentPagerItem.getPosition(getArguments());
        TextView title = (TextView) view.findViewById(R.id.item_title);
        title.setText(String.valueOf(position));

        myStatsManager = (UsageStatsManager)getActivity().getSystemService(Context.USAGE_STATS_SERVICE);
        myQuarier = new UsageQuerier(myStatsManager);
        recentDayStatsList = myQuarier.getUsageStatsInOneDay();

        Log.v("TEST", "List Size is " + Integer.toString(recentDayStatsList.size()));
        for (UsageStats i : recentDayStatsList ){
            Log.v("STATSLIST", i.getPackageName() + i.getTotalTimeInForeground() + " \n");
        }

    }
}
