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

/*
Emotions manager mainly focuses on making the emotion data of the feels app persistent so that it remains stored in the app
even upon its closing. The app utilizes shared preferences to do so.
 */

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

    // Constructor setting the content of EmotionsManager
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

    // method to get the emotions manager at any time
    static EmotionsManager getManager(){
        // use if to check if emotions manager has been initialized, throw a runtime exception if not
        if(emotionsManager==null){
                throw new RuntimeException("Emotions Manager not initialized");
        }
        return emotionsManager;
    }

    // load an emotion from shared preferences
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

    // method converts the student list to a string object via output stream and byte conversion
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


    // convert a string into the storedEmotions
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

    // save stored emotions
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
