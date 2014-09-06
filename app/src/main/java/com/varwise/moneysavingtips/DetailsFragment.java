package com.varwise.moneysavingtips;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class DetailsFragment extends Fragment {
    private String detailsText = "";

    public DetailsFragment() {    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_details, container, false);

        AdView adView = (AdView) rootView.findViewById(R.id.adViewDetails);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).addTestDevice("2D7D6AE8606296EB97A2A9B3681B90F6").build();

        TextView tv = (TextView) rootView.findViewById(R.id.detailsText);
        Button previousButton = (Button) rootView.findViewById(R.id.buttonPrevious);
        Button nextButton = (Button) rootView.findViewById(R.id.buttonNext);

        tv.setText(detailsText);

        adView.loadAd(adRequest);
        return rootView;
    }

    public void setDetailsText(String detailsText) {
        this.detailsText = detailsText;
    }

}
