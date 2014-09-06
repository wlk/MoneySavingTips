package com.varwise.moneysavingtips;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

/**
* Created by w on 06.09.14.
*/
public class PlaceholderFragment extends Fragment implements AdapterView.OnItemClickListener {
    public String[] titles;
    private ListAdapter adapter;

    public PlaceholderFragment() {
    }

    public void setAdapter(ListAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main_screen, container, false);

        ListView mainList = (ListView) rootView.findViewById(R.id.lvMainScreen);
        mainList.setAdapter(adapter);

        mainList.setOnItemClickListener(this);

        AdView adView = (AdView) rootView.findViewById(R.id.adViewMainScreen);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
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
