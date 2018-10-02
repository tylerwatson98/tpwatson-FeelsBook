
/*
Emotion class contains the emotion constructor, getters, setters, and a method to convert the emotion to a string.
This class will implement serializable so it can convert the emotion string instances to bytes for storage purposes.
The emotion class provides the initial creation for the emotion entry objects that will added to the stored emotions and
saved using methods within the curator class. The Emotions manager class will handle saving these emotions in shared preferences.

Idea for making the emotion serializable from Abram Hindle's youtube tutorial "Student Picker for android" Saga
 */

package com.example.tpwatson_feelsbook;

import android.support.annotation.NonNull;

import java.io.Serializable;

class Emotion implements Serializable{
    // Establish a private final String variable "emotion" to prevent other classes from instantiating it
    private final String emotion;


    // Emotion constructor to construct the emotion
    Emotion(String emotion) {
        this.emotion = emotion;
    }


    // get emotion returns the private final String emotion
    String getEmotion() {
        return this.emotion;
    }


    // Method call the getEmotion method and returns its result in a string format
    @NonNull
    public String toString() {
        return getEmotion();
    }
}
