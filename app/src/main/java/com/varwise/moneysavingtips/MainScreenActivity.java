package com.varwise.moneysavingtips;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.varwise.moneysavingtips.util.TipAdapter;

import java.util.Random;


public class MainScreenActivity extends Activity {
    public static final String EXTRA_TIP_NAME = "TIP_NAME";
    public static String TAG = "MainScreenActivity";
    public static String EXTRA_TIP_TEXT = "TIP_TEXT";
    public static String EXTRA_TIP_ID = "TIP_ID";
    public static TipAdapter adapter;
    ListViewFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new TipAdapter(getApplicationContext(), R.layout.list_item, getTipsFromXMLResource());

        setContentView(R.layout.activity_main_screen);

        if (savedInstanceState == null) {
            fragment = new ListViewFragment();
            fragment.setAdapter(adapter);
            getFragmentManager().beginTransaction().add(R.id.container, fragment).commit();
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
        int max = adapter.getCount();
        Random r = new Random();
        startSpecificDetailActivity(r.nextInt(max));
    }

    public void startSpecificDetailActivity(int position) {
        Intent intent = fragment.getIntentForTip(position);
        startActivity(intent);
    }
}
