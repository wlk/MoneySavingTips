package com.varwise.moneysavingtips;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.varwise.moneysavingtips.util.TipAdapter;


public class MainScreenActivity extends Activity {
    public static String TAG = "MainScreenActivity";
    public static String EXTRA_TIP_TEXT = "TIP_TEXT";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TipAdapter adapter = new TipAdapter(getApplicationContext(), R.layout.list_item, getTipsFromXMLResource());

        setContentView(R.layout.activity_main_screen);

        if (savedInstanceState == null) {
            PlaceholderFragment fragment = new PlaceholderFragment();
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
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
