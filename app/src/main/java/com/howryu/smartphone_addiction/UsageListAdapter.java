package com.howryu.smartphone_addiction;

/*
* Copyright (C) 2014 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Provide views to RecyclerView with the directory entries.
 */
public class UsageListAdapter extends RecyclerView.Adapter<UsageListAdapter.ViewHolder> {

    private List<CustomUsageStats> mCustomUsageStatsList = new ArrayList<>();
    private DateFormat mDateFormat = new SimpleDateFormat();

    /**
     * Provide a reference to the type of views that you are using (custom ViewHolder)
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView mAppName;
        private final TextView mTotalTimeUsed;
        private final ImageView mAppIcon;

        public ViewHolder(View v) {
            super(v);
            mAppName = (TextView) v.findViewById(R.id.textview_app_name);
            mTotalTimeUsed = (TextView) v.findViewById(R.id.textview_total_time_used);
            mAppIcon = (ImageView) v.findViewById(R.id.app_icon);
        }

        public TextView getTotalTimeUsed() {
            return mTotalTimeUsed;
        }

        public TextView getAppName() {
            return mAppName;
        }

        public ImageView getAppIcon() {
            return mAppIcon;
        }
    }

    public UsageListAdapter() {
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.usage_row, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.getAppName().setText(
                mCustomUsageStatsList.get(position).appName);
        String totalTimeInForeground = convertMillsToNormal(mCustomUsageStatsList.get(position).usageStats.getTotalTimeInForeground());
        viewHolder.getTotalTimeUsed().setText(totalTimeInForeground);
        viewHolder.getAppIcon().setImageDrawable(mCustomUsageStatsList.get(position).appIcon);
    }

    @Override
    public int getItemCount() {
        return mCustomUsageStatsList.size();
    }

    public void setCustomUsageStatsList(List<CustomUsageStats> customUsageStats) {
        mCustomUsageStatsList = customUsageStats;
    }

    private String convertMillsToNormal(long totalTimeInForeground){
        int minutes;
        int seconds;
        int hours;

        minutes = (int) (( totalTimeInForeground / (1000*60)) % 60);
        seconds = (int) (totalTimeInForeground / 1000) % 60 ;
        hours   = (int) ((totalTimeInForeground / (1000*60*60)) % 24);

        String result;
        result = Integer.toString(hours) + "h:" + Integer.toString(minutes) + "m:"
                + Integer.toString(seconds) + "s";

        return result;
    }

}
