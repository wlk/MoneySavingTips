package com.varwise.moneysavingtips;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.varwise.moneysavingtips.util.TipAdapter;


public class MainScreenActivity extends Activity {
    public static final String EXTRA_TIP_NAME = "TIP_NAME";
    public static String TAG = "MainScreenActivity";
    public static String EXTRA_TIP_TEXT = "TIP_TEXT";
    public static String EXTRA_TIP_ID = "TIP_ID";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TipAdapter adapter = new TipAdapter(getApplicationContext(), R.layout.list_item, getTipsFromXMLResource());

        setContentView(R.layout.activity_main_screen);

        if (savedInstanceState == null) {
            ListViewFragment fragment = new ListViewFragment();
            fragment.setAdapter(adapter);
            getFragmentManager().beginTransaction().add(R.id.container, fragment).commit();
        }
    }

    private Tip[] getTipsFromXMLResource() {
        String[] inputTips = getResources().getStringArray(R.array.tips);
        Tip[] tips = new Tip[inputTips.length];

        for (int i = 0; i < inputTips.length; ++i) {
            if (inputTips[i].equals("") || inputTips[i] == null || inputTips[i].split("\\t").length != 2) {
                tips[i] = new Tip("", "", i + 1);
            } else {
                tips[i] = new Tip(inputTips[i].split("\\t")[0], inputTips[i].split("\\t")[1], i + 1);
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
        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }

}
