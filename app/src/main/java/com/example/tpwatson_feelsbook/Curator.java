package com.example.tpwatson_feelsbook;

import java.io.IOException;


// Class curator helps to perform tasks to reduce code segments and responsibilities within other classes
class Curator {

    // create a singleton for StoredEmotions
    private static StoredEmotions storedEmotions = null;

    // if the storedEmotions are null then make a new set
    static StoredEmotions getStoredEmotions() {
        // if there is no instance of a storedEmotions transfer to a try and catch system
        if (storedEmotions == null) {
            try {
                // establish connection to the storedEmotions variable and add new emotions then perform updates
                storedEmotions = EmotionsManager.getManager().loadEmotion();
                storedEmotions.addUpdate(new Update() {
                    @Override
                    // save emotion to the list of stored emotions
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

    private static void saveEmotion() {
        try {
            EmotionsManager.getManager().saveEmotions(getStoredEmotions());
            // catch any io exceptions and print them to stack trace to visualize exception
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void addEmotion(Emotion emotion){
        // Get the stored emotions and add the emotion passed in the parameter
        getStoredEmotions().addEmotion(emotion);
    }

}
