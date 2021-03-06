package com.varwise.moneysavingtips;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

public class DetailsFragment extends Fragment {
    private String detailsText = "";
    private String nameText = "";

    public DetailsFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_details, container, false);

        AdView adView = (AdView) rootView.findViewById(R.id.adViewDetails);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).addTestDevice("0457F45F2F3B38D51216287AD98A2C3D").addTestDevice("3AC2DCEE575018317C028D0C93F19AD0").addTestDevice("2D7D6AE8606296EB97A2A9B3681B90F6").build();

        TextView tv = (TextView) rootView.findViewById(R.id.detailsText);

        tv.setText(nameText + "\n\n" + detailsText);

        adView.loadAd(adRequest);

        return rootView;
    }

    public void setDetailsText(String detailsText) {
        this.detailsText = detailsText;
    }

    public void setNameText(String nameText) {
        this.nameText = nameText;
    }
}
