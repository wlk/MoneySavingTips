package com.varwise.moneysavingtips;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ShareActionProvider;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;


public class DetailsActivity extends Activity {
    private static final String TAG = "DetailsActivity";
    private int tipId;
    private InterstitialAd interstitial;
    private Tracker t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        t = MainScreenActivity.tracker;
        t.setScreenName(getLocalClassName());
        t.send(new HitBuilders.AppViewBuilder().build());

        if (savedInstanceState == null) {
            DetailsFragment f = new DetailsFragment();

            Intent intent = getIntent();
            String message = intent.getStringExtra(MainScreenActivity.EXTRA_TIP_TEXT);
            f.setDetailsText(message);


            tipId = Integer.parseInt(intent.getStringExtra(MainScreenActivity.EXTRA_TIP_ID));
            String name = intent.getStringExtra(MainScreenActivity.EXTRA_TIP_NAME);
            f.setNameText(name);

            setTitle("Tip " + (tipId + 1) + ": " + name.substring(0, Math.min(25, name.length())) + "...");

            getFragmentManager().beginTransaction().add(R.id.container, f).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.details_screen, menu);

        MenuItem item = menu.findItem(R.id.action_share);
        ShareActionProvider mShareActionProvider = (ShareActionProvider) item.getActionProvider();
        String toShare = "http://bit.ly/1upOr3Q " + MainScreenActivity.adapter.getTipText(tipId);
        String shareBody = toShare.substring(0, Math.min(135, toShare.length()));
        if(toShare.length() > 135){
            shareBody = shareBody + "...";
        }

        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Money Saving Tip");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);

        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(sharingIntent);
        }

        return super.onCreateOptionsMenu(menu);
    }

    public void onClickPrevious(View v) {
        t.send(new HitBuilders.EventBuilder().setCategory("DetailsActivity").setAction("click").setLabel("onClickPrevious").build());
        replaceFragmentWithNewTip(tipId - 1);
        maybeShowInterstitial();

    }

    public void onClickNext(View v) {
        t.send(new HitBuilders.EventBuilder().setCategory("DetailsActivity").setAction("click").setLabel("onClickNext").build());
        replaceFragmentWithNewTip(tipId + 1);
        maybeShowInterstitial();
    }

    private void maybeShowInterstitial(){
        AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).addTestDevice("0457F45F2F3B38D51216287AD98A2C3D").addTestDevice("3AC2DCEE575018317C028D0C93F19AD0").addTestDevice("2D7D6AE8606296EB97A2A9B3681B90F6").build();

        if(MainScreenActivity.totalAdsThisRun > 10 && MainScreenActivity.totalAdsThisRun % 5 == 0) {
            interstitial = new InterstitialAd(this);
            interstitial.setAdUnitId("ca-app-pub-5829945009169600/3224943160");
            interstitial.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {
                    interstitial.show();
                }
            });

            interstitial.loadAd(adRequest);
        }
    }

    public void replaceFragmentWithNewTip(int newTipId) {
        ++MainScreenActivity.totalAdsThisRun;

        if (newTipId < 0) {
            newTipId = MainScreenActivity.adapter.getCount() - 1;
        }
        if (newTipId > MainScreenActivity.adapter.getCount() - 1) {
            newTipId = 0;
        }

        DetailsFragment f = new DetailsFragment();

        f.setDetailsText(MainScreenActivity.adapter.getTipText(newTipId));
        String name = MainScreenActivity.adapter.getTipName(newTipId);
        f.setNameText(name);
        setTitle("Tip " + (newTipId + 1) + ": " + name.substring(0, Math.min(25, name.length())) + "...");

        getFragmentManager().beginTransaction().replace(R.id.container, f).commit();
        tipId = newTipId;
    }
}
