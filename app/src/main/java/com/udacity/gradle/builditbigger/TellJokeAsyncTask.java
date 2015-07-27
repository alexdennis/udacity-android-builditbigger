package com.udacity.gradle.builditbigger;

import android.os.AsyncTask;
import android.support.v4.util.Pair;
import android.util.Log;

import com.carltondennis.builditbigger.backend.myApi.MyApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;

/**
 * Created by alex on 7/25/15.
 */
public class TellJokeAsyncTask extends AsyncTask<Void, Void, Pair<IOException, String>> {

    private static String TAG = TellJokeAsyncTask.class.getSimpleName();

    private Callback mCallback;
    private static MyApi myApiService = null;

    public TellJokeAsyncTask(Callback c) {
        mCallback = c;
    }

    @Override
    protected Pair<IOException, String> doInBackground(Void... params) {
        if (TellJokeAsyncTask.myApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
//                    .setRootUrl("https://alex-udacity-jokefactory.appspot.com/_ah/api/");
                    // options for running against local devappserver
                    // - 10.0.3.2 is localhost's IP address in Genymotion emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl("http://10.0.3.2:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            // end options for devappserver
            TellJokeAsyncTask.myApiService = builder.build();
        }

        try {
            String joke = TellJokeAsyncTask.myApiService.tellAJoke().execute().getText();
            return new Pair<>(null, joke);
        } catch (IOException e) {
            return new Pair<>(e, null);
        }
    }

    @Override
    protected void onPostExecute(Pair<IOException, String> result) {
        if (result.first != null) {
            Log.d(TAG, "Exception thrown: " + result.first.getMessage());
            mCallback.onJokeFailure(result.first);
        } else if (result.second != null) {
            Log.d(TAG, "Joke retrieved: " + result.second);
            mCallback.onJokeSuccess(result.second);
        }
    }

    public interface Callback {

        void onJokeSuccess(String string);

        void onJokeFailure(IOException e);
    }
}
