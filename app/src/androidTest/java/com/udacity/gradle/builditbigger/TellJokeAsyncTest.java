package com.udacity.gradle.builditbigger;

import android.test.AndroidTestCase;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by alex on 7/25/15.
 */
public class TellJokeAsyncTest extends AndroidTestCase implements TellJokeAsyncTask.Callback {

    private String joke;

    // create  a signal to let us know when our task is done.
    final CountDownLatch signal = new CountDownLatch(1);

    public void testVerifyJokeResponse() {

        assertNull("We should have no joke yet", joke);
        TellJokeAsyncTask task = new TellJokeAsyncTask(this);
        task.execute();

        try {
            // Waiting for response from async task for 30 secs
            signal.await(30, TimeUnit.SECONDS);
            assertNotNull("We should have a joke now", joke);
        } catch (InterruptedException e) {
            // Something happened and we could not complete the test.
            fail(e.getMessage());
        }
    }

    @Override
    public void onJokeSuccess(String string) {
        // Yay! we got a joke
        joke = string;

        // Release the latch
        signal.countDown();
    }

    @Override
    public void onJokeFailure(IOException e) {
        fail(e.getMessage());
    }
}
