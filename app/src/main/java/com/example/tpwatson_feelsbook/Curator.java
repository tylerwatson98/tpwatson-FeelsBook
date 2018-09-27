package com.example.tpwatson_feelsbook;

import java.io.IOException;


// Class curator helps to perform tasks to reduce code segments and responsibilities within other classes
class Curator {

    // create a singleton for StoredEmotions
    private static StoredEmotions storedEmotions = null;

    // if the storedEmotions are null then try making a new
    static public StoredEmotions getStoredEmotions() {
        // if there is no instance of a storedEmotions transfer to a try and catch system
        if (storedEmotions == null) {
            try {
                // create storedEmotions on instance of it not first existing
                storedEmotions = EmotionsManager.getManager().loadEmotion();
                storedEmotions = EmotionsManager.getManager().loadComment();
                // on updates update the storedEmotions so they automatically save
                storedEmotions.addUpdate(new Update() {
                    @Override
                    public void update() {
                        saveE();
                        saveC();
                    }
                });
                // catch any io exceptions and print them to stack trace to visualize exception
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // return the storeEmotions
        return storedEmotions;
    }

    private static void saveE() {
        try {
            EmotionsManager.getManager().saveE(getStoredEmotions());
            // catch any io exceptions and print them to stack trace to visualize exception
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static void saveC() {
        try {
            EmotionsManager.getManager().saveC(getStoredEmotions());
            // catch any io exceptions and print them to stack trace to visualize exception
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addEmotion (Emotion emotion){
        // Get the stored emotions and add the emotion passed in the parameter
        getStoredEmotions().addEmotion(emotion);
    }

    public void addComment (Comment comment){
        // Get the stored comments and add the emotion passed in the parameter
        getStoredEmotions().addComment(comment);
    }


}
