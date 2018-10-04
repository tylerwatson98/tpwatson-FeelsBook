
/*
 The curator class helps to perform tasks associated with adding and saving the emotions. It reduces the code in separate classes as an attempt to increase
 cohesion. The curator utilizes methods found in the StoredEmotions class via the creation of an instance of the StoredEmotions in this class. Via getStoredEmotions
 emotions can be loaded and updated as well as be saved to the StoredEmotions

 Idea for Curator implementations from *Abram Hindle, CMPUT 301: Saga of Student Picker, https://www.youtube.com/playlist?list=PL240uJOh_Vb4PtMZ0f7N8ACYkCLv0673O, 2014/09/14, viewed 2018/09/15*
 */

package com.example.tpwatson_feelsbook;

import java.io.IOException;


// Class curator helps to perform tasks to reduce code segments and responsibilities within other classes
class Curator {

    // create a new singleton for StoredEmotions
    private static StoredEmotions storedEmotions = null;

    // gets the stored Emotions and returns type StoredEmotions class. Performs adding updates and emotions to the stored emotions and finishes by saving
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
