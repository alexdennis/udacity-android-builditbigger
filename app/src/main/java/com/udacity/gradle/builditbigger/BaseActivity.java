package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.carltondennis.jokedisplay.JokeDisplay;

import java.io.IOException;


abstract public class BaseActivity extends ActionBarActivity implements TellJokeAsyncTask.Callback{

    private TellJokeAsyncTask asyncTask;
    private ProgressBar spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = (ProgressBar) findViewById(R.id.spinner);
        spinner.setVisibility(View.GONE);
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
        spinner.setVisibility(View.VISIBLE);

        asyncTask = new TellJokeAsyncTask(this);
        asyncTask.execute();
    }

    protected void fetchJoke() {
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
    public void onJokeSuccess(String joke) {
        spinner.setVisibility(View.GONE);

        Intent i = new Intent(this, JokeDisplay.class);
        i.putExtra(JokeDisplay.JOKE_TEXT, joke);
        startActivity(i);
    }

    private Toast toast;

    @Override
    public void onJokeFailure(IOException e) {
        spinner.setVisibility(View.GONE);

        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT);
        toast.show();
    }
}
