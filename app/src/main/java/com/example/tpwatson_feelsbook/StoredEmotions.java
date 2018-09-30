

/*
 The stored emotions class allows the user to both set new emotion entries and get these entries in the form of a displayed list
 */


package com.example.tpwatson_feelsbook;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;


// implements serializable so the class can convert an instance into bytes
public class StoredEmotions implements Serializable{

    // Arraylist type with general parameter "Emotion" that the array list will hold
    private ArrayList<Emotion> emotions;
    private ArrayList<Comment> comments;
    // Arraylist type with general parameter "Update" that the array list will hold
    // Mark as transient so it will not be serializeable;
    private transient ArrayList<Update> updates;

    // Constructor making new Array lists and assigning them to their designation objects
    StoredEmotions() {
        emotions = new ArrayList<>();
        comments = new ArrayList<>();
        updates = new ArrayList<>();
    }
    /*
     Method gets updates after checking to see if updates are null, if so it establishes a new array list
     with general parameter of "Update". Finally it returns updates
      */
    private ArrayList<Update>getUpdates(){
        if(updates==null){
            updates= new ArrayList<>();
        }
        return updates;
    }

    // establish with general parameter "Emotion" that the collection will be associated with
    public Collection<Emotion> listEmotions(){
        return emotions;
    }

    public ArrayList<Comment> listComments(){
        return comments;
    }

    // Perform updates on the Update array list when called
    private void makeUpdates() {
        // for each update in the Updates call the update method to get the updates
        for (Update updates:getUpdates()) {
            updates.update();
        }
    }

    // Remove emotion from the emotions Array list and update modifications
    public void removeEmotion(Emotion emotion) {
        emotions.remove(emotion);
        makeUpdates();
    }

    public void removeComment(Comment comment){
        comments.remove(comment);
        makeUpdates();
    }

    // Adds a new emotion to the emotions array list update modifications
    public void addEmotion(Emotion emotion){
        emotions.add(emotion);
        makeUpdates();
    }

    public void addComment(Comment comment){
        comments.add(comment);
        makeUpdates();
    }

    // Adds a new update
    public void addUpdate(Update u) {
        getUpdates().add(u);
    }


/*
    void getJoyCount(){
        Collection<Emotion> emotions=  Curator.getStoredEmotions().listEmotions();

        //int x =Collections.frequency(Collections.singleton(emotions.toString()),"[Sadness, Sadness, Joy, Joy, Joy, Joy]");-->gave me a count of 1
        String test=String.valueOf(emotions);
        //test=test.substring(1);
        //test=test.substring(0,test.length()-1);
        String[] count=test.split(",");
        int x=0;
        int y=0;

        for (String aCount : count) {
            //if (aCount.trim().equals("Joy")) {
            if(aCount.contains("Joy")){
                x++;
            }
            //if (aCount.trim().equals("Sadness")){
            if(aCount.contains("Sadness")){
                y++;
            }
        }

        String tag="test";
        Log.d("test values un split", String.valueOf(emotions));
        Log.d("test values split", Arrays.toString(count));
        Log.d("test values", test);
        Log.d("test count joy", String.valueOf(x));
        Log.d("test count sad", String.valueOf(y));
        System.out.print(x);
    }*/

}


