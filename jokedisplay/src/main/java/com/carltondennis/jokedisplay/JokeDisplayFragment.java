package com.carltondennis.jokedisplay;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A placeholder fragment containing a simple view.
 */
public class JokeDisplayFragment extends Fragment {

    public JokeDisplayFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_joke_display, container, false);

        Intent i = getActivity().getIntent();
        if (i != null && i.hasExtra(JokeDisplay.JOKE_TEXT)) {
            TextView tv = (TextView) rootView.findViewById(R.id.joke_text);
            tv.setText(i.getStringExtra(JokeDisplay.JOKE_TEXT));
        } else {
            // Nothing to display so kill Activity?
            getActivity().finish();
        }

        return rootView;
    }
}
