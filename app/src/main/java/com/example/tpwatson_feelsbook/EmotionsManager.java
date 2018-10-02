
/*
Emotions manager mainly focuses on making the emotion data of the FeelsBook app persistent so that it remains stored in the app
even upon its closing. The app utilizes shared preferences to do so. The class both saves and loads the emotions from the stored shared
preferences file created using the to and from string methods byte conversions and serialization.

Idea for implementing shared preferences and byte encoding and decoding from Abram Hindle's youtube tutorial "Student Picker for android" Saga and
also https://developer.android.com/reference/android/content/SharedPreferences
 */


package com.example.tpwatson_feelsbook;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

class EmotionsManager {
    // create the general file to store the emotion data
    private static final String Emo="Emotions";
    // create the key file to store the emotion data
    private static final String emo="emotions";
    // initialize a context to access
    private final Context context;


    @SuppressLint("StaticFieldLeak")
    // create singleton of the Emotions manager
    static private EmotionsManager emotionsManager=null;


    // Constructor setting the context of EmotionsManager
    private EmotionsManager(Context context){
        this.context=context;
    }


    // initialize the emotions manager
    static void Initialize(Context context) {
        // use if to check if emotions manager has been initialized and if not also check if the context is also uninitialized
        if (emotionsManager == null) {
            if(context==null) {
                // throw a new runtime exception
                throw new RuntimeException("Missing context");
            }
        }
        emotionsManager = new EmotionsManager(context);
    }


    // method to get the emotions manager
    static EmotionsManager getManager(){
        // use if to check if emotions manager has been initialized, throw a runtime exception if not
        if(emotionsManager==null){
                throw new RuntimeException("Emotions Manager not initialized");
        }
        return emotionsManager;
    }


    // loads an emotion from shared preferences and returns type StoredEmotions
    StoredEmotions loadEmotion() throws IOException {
        // get the shared preferences settings for the Emo file set to private mode via 0
        SharedPreferences settings = context.getSharedPreferences(Emo,0);
        // get settings
        String storedEmotions=settings.getString(emo,"");
        // if the stored emotions are empty create a new storeEmotions item
        if(storedEmotions.equals("")){
            return new StoredEmotions();
        }
        else {
            // return the emotion from storedEmotions
            return fromString(storedEmotions);
        }
    }


    // Converts the emotions to string object via output stream and byte conversion to save the emotion in a serialized format of the entry string
    private static String toString(StoredEmotions se) throws IOException{
        // create a new byte array output stream to write to a local file
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        // create the object output stream which will serialize the data
        ObjectOutputStream oo = new ObjectOutputStream(bo);
        // writes the object
        oo.writeObject(se);
        // closes the Object output stream
        oo.close();
        // get the bytes from the ByteArrayOutputStream
        byte bytes[] = bo.toByteArray();
        // return the byte result of the converted string via the Base64 encoding conversion
        return Base64.encodeToString(bytes,Base64.DEFAULT);
    }


    // Decodes the byte converted emotion into a string when loading out the emotions from the stored entry string
    private static StoredEmotions fromString(String storedEmotions) throws IOException{
        // create a new byte input stream with Base64 decoding to decode the serialized bites
        ByteArrayInputStream bi = new ByteArrayInputStream(Base64.decode(storedEmotions,Base64.DEFAULT));
        // create a new object input stream to deserialize the data
        ObjectInputStream oi = new ObjectInputStream(bi);
        // try and catch instance to test if the class StoredEmotions exists, if so the class will be read in the object input stream
        try {
            return (StoredEmotions)oi.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    // saveEmotions will save the emotion entries via the use of shared preferences key and value system
    void saveEmotions(StoredEmotions se) throws IOException{
        // get the shared preferences settings for the Emo file set to private mode via 0
        SharedPreferences settings=context.getSharedPreferences(Emo,0);
        //  get an editor from the preference to allow us to edit stored entries in the storedEmotions
        SharedPreferences.Editor ed =settings.edit();
        // put the stored emotions into the key file after converting them to a string
        ed.putString(emo,toString(se));
        // apply the modifications to the shared preferences editor
        ed.apply();
    }
}
