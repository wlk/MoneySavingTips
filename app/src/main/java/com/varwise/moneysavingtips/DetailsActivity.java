package com.varwise.moneysavingtips;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ShareActionProvider;


public class DetailsActivity extends Activity {
    private static final String TAG = "DetailsActivity";
    private int tipId;
    private ShareActionProvider mShareActionProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

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
        mShareActionProvider = (ShareActionProvider) item.getActionProvider();
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
        replaceFragmentWithNewTip(tipId - 1);
    }

    public void onClickNext(View v) {
        replaceFragmentWithNewTip(tipId + 1);
    }

    public void replaceFragmentWithNewTip(int newTipId) {
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
