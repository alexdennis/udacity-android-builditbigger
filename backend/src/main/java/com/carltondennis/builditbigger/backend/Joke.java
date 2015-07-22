package com.carltondennis.builditbigger.backend;

/**
 * The object model for the data we are sending through endpoints
 */
public class Joke {

    private String jokeText;

    public String getText() {
        return jokeText;
    }

    public void setText(String data) {
        jokeText = data;
    }
}