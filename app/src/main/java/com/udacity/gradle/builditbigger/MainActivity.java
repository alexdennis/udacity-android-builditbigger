package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.carltondennis.jokedisplay.JokeDisplay;


public class MainActivity extends ActionBarActivity implements TellJokeAsyncTask.Callback{

    private TellJokeAsyncTask asyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        Intent i = new Intent(this, JokeDisplay.class);
        i.putExtra(JokeDisplay.JOKE_TEXT, joke);
        startActivity(i);
    }
}
