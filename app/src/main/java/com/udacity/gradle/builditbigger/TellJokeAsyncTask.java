package com.udacity.gradle.builditbigger;

import android.os.AsyncTask;

import com.carltondennis.builditbigger.backend.myApi.MyApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.IOException;

/**
 * Created by alex on 7/25/15.
 */
public class TellJokeAsyncTask extends AsyncTask<Void, Void, String> {

    private Callback mCallback;
    private static MyApi myApiService = null;

    public TellJokeAsyncTask(Callback c) {
        mCallback = c;
    }

    @Override
    protected String doInBackground(Void... params) {
        if (TellJokeAsyncTask.myApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    .setRootUrl("https://alex-udacity-jokefactory.appspot.com/_ah/api/");
            TellJokeAsyncTask.myApiService = builder.build();
        }

        try {
            return TellJokeAsyncTask.myApiService.tellAJoke().execute().getText();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String joke) {
        mCallback.onJokeResponse(joke);
    }

    public interface Callback {

        void onJokeResponse(String string);
    }
}
