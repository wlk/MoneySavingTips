package com.varwise.moneysavingtips;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class DetailsActivity extends Activity {
    private int tipId;
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

            setTitle("Tip " + (tipId+1) + ": " + name.substring(0, Math.min(25, name.length())) + "...");

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

    public void onClickPrevious(View v){
        replaceFragmentWithNewTip(tipId - 1);
    }

    public void onClickNext(View v){
        replaceFragmentWithNewTip(tipId + 1);
    }

    public void replaceFragmentWithNewTip(int newTipId) {
        if(newTipId < 0){
            newTipId = 0;
        }

        DetailsFragment f = new DetailsFragment();

        f.setDetailsText(MainScreenActivity.adapter.getTipText(newTipId));
        String name = MainScreenActivity.adapter.getTipName(newTipId);
        setTitle("Tip " + (newTipId+1) + ": " + name.substring(0, Math.min(25, name.length())) + "...");

        getFragmentManager().beginTransaction().replace(R.id.container, f).commit();
        tipId = newTipId;
    }
}
