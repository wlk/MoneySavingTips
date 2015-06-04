package com.varwise.moneysavingtips;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.varwise.moneysavingtips.util.TipAdapter;

import java.util.Random;

import hotchemi.android.rate.AppRate;


public class MainScreenActivity extends Activity {
    public static final String EXTRA_TIP_NAME = "TIP_NAME";
    public static String TAG = "MainScreenActivity";
    public static String EXTRA_TIP_TEXT = "TIP_TEXT";
    public static String EXTRA_TIP_ID = "TIP_ID";
    public static TipAdapter adapter;
    ListViewFragment fragment;
    public static int totalAdsThisRun = 0;
    public static GoogleAnalytics analytics;
    public static Tracker tracker;
    private static final boolean appRateEnabled = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new TipAdapter(getApplicationContext(), R.layout.list_item, getTipsFromXMLResource());

        setContentView(R.layout.activity_main_screen);
        setupGoogleAnalytics();

        maybeShowAppRate();

        if (savedInstanceState == null) {
            fragment = new ListViewFragment();
            fragment.setAdapter(adapter);
            getFragmentManager().beginTransaction().add(R.id.container, fragment).commit();
        }
    }

    private void setupGoogleAnalytics(){
        analytics = GoogleAnalytics.getInstance(this);
        analytics.setLocalDispatchPeriod(1800);
        tracker = analytics.newTracker(getResources().getString(R.string.googleAnalytics));
        tracker.enableExceptionReporting(true);
        tracker.enableAdvertisingIdCollection(true);
        tracker.enableAutoActivityTracking(true);
    }

    private void maybeShowAppRate(){
        if(appRateEnabled){
            AppRate.with(this)
                    .setInstallDays(1)
                    .setLaunchTimes(2)
                    .setRemindInterval(1)
                    .setShowNeutralButton(true)
                    .setDebug(false)
                    .monitor();

            AppRate.showRateDialogIfMeetsConditions(this);
        }
    }

    private Tip[] getTipsFromXMLResource() {
        String[] inputTips = getResources().getStringArray(R.array.tips);
        Tip[] tips = new Tip[inputTips.length];

        for (int i = 0; i < inputTips.length; ++i) {
            if (inputTips[i].equals("") || inputTips[i] == null || inputTips[i].split("\\t").length != 2) {
                tips[i] = new Tip("", "", i);
            } else {
                tips[i] = new Tip(inputTips[i].split("\\t")[0], inputTips[i].split("\\t")[1], i);
            }
        }
        return tips;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            AboutDialog about = new AboutDialog(this);
            about.setTitle("About Money Saving Tips");
            about.show();
            return true;
        } else if (id == R.id.action_about) {
            AboutDialog about = new AboutDialog(this);
            about.setTitle("Settings");
            about.show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onClickRandom(View v) {
        tracker.send(new HitBuilders.EventBuilder().setCategory("MainScreenActivity").setAction("click").setLabel("onClickRandom").build());
        int max = adapter.getCount();
        Random r = new Random();
        startSpecificDetailActivity(r.nextInt(max));
    }

    public void startSpecificDetailActivity(int position) {
        Intent intent = fragment.getIntentForTip(position);
        startActivity(intent);
    }
}
