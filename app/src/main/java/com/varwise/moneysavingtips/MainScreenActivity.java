package com.varwise.moneysavingtips;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.Arrays;


public class MainScreenActivity extends Activity {
    public Tip[] tips;
    public String[] titles;
    public static String TAG = "MainScreenActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String[] inputTips = getResources().getStringArray(R.array.tips);
        tips = new Tip[inputTips.length];
        titles = new String[inputTips.length];


        for(int i = 0; i< inputTips.length; ++i){
            if(inputTips[i].equals("") || inputTips[i] == null || inputTips[i].split("\\t").length != 2){
                tips[i] = new Tip("", "");
                titles[i] = "";
            }
            else{
                tips[i] = new Tip(i+1 + ". " + inputTips[i].split("\\t")[0], inputTips[i].split("\\t")[1]);
                titles[i] = tips[i].getName();
            }
        }

        //Log.d(TAG, Arrays.toString(titles));

        setContentView(R.layout.activity_main_screen);
        if (savedInstanceState == null) {

            PlaceholderFragment fragment = new PlaceholderFragment();
            fragment.setTitles(titles);

            getFragmentManager().beginTransaction()
                    .add(R.id.container, fragment)
                    .commit();
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

    public static class PlaceholderFragment extends Fragment implements AdapterView.OnItemClickListener {
        public String[] titles;

        public PlaceholderFragment(){}

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main_screen, container, false);

            ListView mainList = (ListView) rootView.findViewById(R.id.lvMainScreen);

            mainList.setAdapter(new ArrayAdapter<String>(rootView.getContext(), android.R.layout.simple_list_item_1, titles));

            mainList.setOnItemClickListener(this);

            AdView adView = (AdView)rootView.findViewById(R.id.adViewMainScreen);
            AdRequest adRequest = new AdRequest.Builder()
                    .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                    .build();
            adView.loadAd(adRequest);


            return rootView;
        }

        public void setTitles(String[] titles) {
            this.titles = titles;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        }
    }
}
