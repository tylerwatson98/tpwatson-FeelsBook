package com.example.tpwatson_feelsbook;

import java.io.IOException;


// Class curator helps to perform tasks to reduce code segments and responsibilities within other classes
class Curator {

    // create a new singleton for StoredEmotions
    private static StoredEmotions storedEmotions = null;

    // gets the stored Emotions and returns type StoredEmotions class
    static StoredEmotions getStoredEmotions() {
        // if there is no instance of a storedEmotions transfer to a try and catch system
        if (storedEmotions == null) {
            try {
                // establish connection to the storedEmotions and add the new emotion
                storedEmotions = EmotionsManager.getManager().loadEmotion();
                // perform the updates to storedEmotions
                storedEmotions.addUpdate(new Update() {
                    @Override
                    // save emotion to the list of stored emotions with saveEmotion() method call
                    public void update() {
                        saveEmotion();
                    }
                });
                // catch any io exceptions and print them to stack trace to visualize exception
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // return the storedEmotions
        return storedEmotions;
    }


    // save the emotion to shared preferences by calling the saveEmotions method in EmotionsManager
    private static void saveEmotion() {
        try {
            //
            EmotionsManager.getManager().saveEmotions(getStoredEmotions());
            // catch any io exceptions and print them to stack trace to visualize exception
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // add a new emotion
    void addEmotion(Emotion emotion){
        // Get the stored emotions and add the emotion passed in the parameter
        getStoredEmotions().addEmotion(emotion);
    }

}
