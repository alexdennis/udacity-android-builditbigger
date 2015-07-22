package com.carltondennis;

public class JokeMachine {

    private String[] jokes = {
            "What happens to a frog's car when it breaks down?\n" +
                    "It gets toad away.",
            "Q: Why was six scared of seven? \n" +
                    "A: Because seven \"ate\" nine.",
            "Q: What is the difference between snowmen and snowwomen? \n" +
                    "A: Snowballs.",
            "Today a man knocked on my door and asked for a small donation towards the local swimming pool. I gave him a glass of water.",
            "I find it ironic that the colors red, white, and blue stand for freedom until they are flashing behind you.",
            "I think my neighbor is stalking me as she's been googling my name on her computer. I saw it through my telescope last night.",
            "I never wanted to believe that my Dad was stealing from his job as a road worker. But when I got home, all the signs were there."
    };

    public String tellAJoke() {
        int random = (int) (Math.random() * 100) % jokes.length;
        return jokes[random];
    }
}
