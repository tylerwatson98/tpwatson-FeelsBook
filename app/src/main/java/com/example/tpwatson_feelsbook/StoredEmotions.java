

/*
 The stored emotions class allows the user to both set new emotion entries and get these entries in the form of a displayed list
 */


package com.example.tpwatson_feelsbook;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;


// Implements serializable so the class can convert an instance into bytes
class StoredEmotions implements Serializable {

    // Initiate the ArrayLists for the emotion entries and updates. Set the updates arraylist as transient so it will not be serializeable;
    private final ArrayList<Emotion> emotions;
    private transient ArrayList<Update> updates;

    // Construct the new Array lists and assign them to their designation objects
    StoredEmotions() {
        emotions = new ArrayList<>();
        updates = new ArrayList<>();
    }

    /*
     Gets updates after checking if updates are null, if true it establishes a new array list
     with general parameter of "Update". Returns updates
      */
    private ArrayList<Update> getUpdates() {
        if (updates == null) {
            updates = new ArrayList<>();
        }
        return updates;
    }

    // Create the list of emotions with general parameter "Emotion" that the collection will be associated with
    Collection<Emotion> listEmotions() {
        return emotions;
    }

    // Performs updates on the Update array list when called
    private void makeUpdates() {
        // for each update in the Updates call the update method to get the updates
        for (Update updates : getUpdates()) {
            updates.update();
        }
    }

    // Remove emotion from the emotions Array list and updates modifications
    void removeEmotion(Emotion emotion) {
        emotions.remove(emotion);
        makeUpdates();
    }

    // Adds a new emotion to the emotions array list and updates modifications
    void addEmotion(Emotion emotion) {
        emotions.add(emotion);
        makeUpdates();
    }

    // Adds a new update
    void addUpdate(Update u) {
        getUpdates().add(u);
    }
}


