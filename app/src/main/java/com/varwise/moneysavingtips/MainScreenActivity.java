package com.varwise.moneysavingtips;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.varwise.moneysavingtips.util.TipAdapter;

import java.util.Arrays;


public class MainScreenActivity extends Activity {
    public static String TAG = "MainScreenActivity";
    public Tip[] tips;
    public String[] titles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String[] inputTips = getResources().getStringArray(R.array.tips);
        tips = new Tip[inputTips.length];
        TipAdapter adapter;

        for (int i = 0; i < inputTips.length; ++i) {
            if (inputTips[i].equals("") || inputTips[i] == null || inputTips[i].split("\\t").length != 2) {
                tips[i] = new Tip("", "");
            } else {
                tips[i] = new Tip(i + 1 + ". " + inputTips[i].split("\\t")[0], inputTips[i].split("\\t")[1]);
            }
        }

        adapter = new TipAdapter(getApplicationContext(), R.layout.list_item, tips);

        Log.d(TAG, Arrays.toString(titles));

        setContentView(R.layout.activity_main_screen);
        if (savedInstanceState == null) {

            PlaceholderFragment fragment = new PlaceholderFragment();
            fragment.setTitles(titles);
            fragment.setAdapter(adapter);
            getFragmentManager().beginTransaction().add(R.id.container, fragment).commit();
        }
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
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
