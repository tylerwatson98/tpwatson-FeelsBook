
/*
Emotion class containing the emotion constructor, getters, and setters
 */

package com.example.tpwatson_feelsbook;

import android.support.annotation.NonNull;

import java.io.Serializable;


// implements serializable so the class can convert an instance into bytes
class Emotion implements Serializable{
    // Establish a private final String variable "emotion" to prevent other classes from instantiating it
    private final String emotion;

    // Emotion constructor
    Emotion(String emotion) {
        this.emotion = emotion;
    }

    // get emotion returns the emotion
    String getEmotion() {
        return this.emotion;
    }

    // Method call the getEmotion method and returns its result in a string format
    @NonNull
    public String toString() {
        return getEmotion();
    }

    /*
    @Override
    public boolean equals(Object o) {
        return o != null && o.getClass() == this.getClass() && this.equals((Emotion)o);
    }

    private boolean equals(Emotion e) {
        return e != null && getEmotion().equals(e.getEmotion());
    }*/

}
