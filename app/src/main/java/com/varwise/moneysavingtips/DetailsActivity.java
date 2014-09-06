package com.varwise.moneysavingtips;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;


public class DetailsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        if (savedInstanceState == null) {
            PlaceholderFragment f = new PlaceholderFragment();

            Intent intent = getIntent();
            String message = intent.getStringExtra(MainScreenActivity.EXTRA_TIP_TEXT);
            f.setDetailsText(message);
            String id = intent.getStringExtra(MainScreenActivity.EXTRA_TIP_ID);
            String name = intent.getStringExtra(MainScreenActivity.EXTRA_TIP_NAME);


            setTitle("Tip " + id + ": " + name.substring(0, Math.min(25, name.length())) + "...");

            getFragmentManager().beginTransaction().add(R.id.container, f).commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }

    public static class PlaceholderFragment extends Fragment {
        private String detailsText = "";

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_details, container, false);

            AdView adView = (AdView) rootView.findViewById(R.id.adViewDetails);
            AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();

            TextView tv = (TextView) rootView.findViewById(R.id.detailsText);
            tv.setText(detailsText);

            adView.loadAd(adRequest);
            return rootView;
        }


        public void setDetailsText(String detailsText) {
            this.detailsText = detailsText;
        }
    }
}
