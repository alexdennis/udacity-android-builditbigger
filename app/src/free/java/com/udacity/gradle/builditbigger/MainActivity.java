package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.carltondennis.jokedisplay.JokeDisplay;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;


public class MainActivity extends ActionBarActivity implements TellJokeAsyncTask.Callback{

    InterstitialAd mInterstitialAd;
    private TellJokeAsyncTask asyncTask;
    private ProgressBar spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_banner_add_unit_id));

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                fetchJoke();
                requestNewInterstitial();
            }
        });

        requestNewInterstitial();
        setContentView(R.layout.activity_main);

        spinner = (ProgressBar) findViewById(R.id.spinner);
        spinner.setVisibility(View.GONE);
    }

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();

        mInterstitialAd.loadAd(adRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void tellJoke(View view){

        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            fetchJoke();
        }
    }

    private void fetchJoke() {
        spinner.setVisibility(View.VISIBLE);

        asyncTask = new TellJokeAsyncTask(this);
        asyncTask.execute();
    }

    @Override
    public void onPause() {
        super.onPause();

        if (asyncTask != null) {
            asyncTask.cancel(true);
        }
    }

    @Override
    public void onJokeResponse(String joke) {
        spinner.setVisibility(View.GONE);
        
        Intent i = new Intent(this, JokeDisplay.class);
        i.putExtra(JokeDisplay.JOKE_TEXT, joke);
        startActivity(i);
    }
}
