/*
    The browse emotions class allows the user to see their entered emotions in the form of a list view. It is sorted
    in ascending order (oldest at the top). Clicking on an emotion will bring up an alert dialog that allows the user
    to either edit an emotion or delete it from the list of entries. Clicking out of the dialog will close it. The user can
    return to the main screen by clicking the button at the bottom of the window.

    Idea for using and implementing comparator from https://developer.android.com/reference/java/util/Comparator

    Idea for implementing the alert dialog, arrayAdapter, and listview connections to the arraylist for entry positions
    from Abram Hindle's youtube tutorial "Student Picker for android" Saga

    Idea for passing the string form of the entry via putExtra from
    https://developer.android.com/reference/android/content/Intent#putExtra(java.lang.String,%20android.os.Parcelable)
 */


package com.example.tpwatson_feelsbook;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class BrowseEmotionsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // initiate the browse emotions activity, set the appropriate layout view, and initialize application context
        super.onCreate(savedInstanceState);
        setContentView(R.layout.browse_emotions);
        EmotionsManager.Initialize(this.getApplicationContext());
        // Get the list view variable via its ID and assign it to an object
        ListView listView = findViewById(R.id.emotion_list);
        // Get the stored list of users emotions entered via calls to curator methods

        final Collection<Emotion> emotions = Curator.getStoredEmotions().listEmotions();
        // Create new array list initialized by the emotions collection object. Final indicates el variable is shared and wont be re-assigned
        final ArrayList<Emotion> el = new ArrayList<>(emotions);
        // call the sort class to sort the ArrayList of emotions
        Collections.sort(el,new Sort());

        /* create array adapter providing access to stored array of emotions so they can be displayed on the specified android layout
         for the listView from the list "el". Final indicates ea variable is shared and wont be re-assigned
          */
        final ArrayAdapter<Emotion> ea = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, el);
        // set the adaptor to the listView to list the stored emotions
        listView.setAdapter(ea);


       /* Call the curator to get the list of stored emotions into the browse emotions layout to display current
       entered emotions to track updates
        */
        Curator.getStoredEmotions().addUpdate(new Update() {
            @Override
            // Upon changes to the stored emotions update the display according the list changes and mark those changes
            public void update() {
                // clear the list display
                el.clear();
                // get the stored emotions collection
                Collection<Emotion> emotions=  Curator.getStoredEmotions().listEmotions();
                // add the stored emotions back to the list display
                el.addAll(emotions);
                // mark the updates to the stored emotions to show changed data
                ea.notifyDataSetChanged();
            }
        });



        // add listener to detect button click on items in listview
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            // method to initiate after listener detects click
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // create an alert dialog via the alert dialog builder to help build dialog to specifics
                AlertDialog.Builder ab = new AlertDialog.Builder(BrowseEmotionsActivity.this);
                // set dialog message to edit entry to appear at grabbed position
                ab.setMessage("Edit Entry: " + el.get(position).toString() + "\n" );
                // set the dialog to be cancelable outside of box
                ab.setCancelable(true);
                final int fPosition = position;
                // set a negative button for deleting emotion entries on click via the listener
                ab.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // get the position of the emotion selected from in the listView
                        Emotion emotion = el.get(fPosition);
                        // remove that emotion from the listView of stored emotions
                        Curator.getStoredEmotions().removeEmotion(emotion);
                        //Curator.getStoredEmotions().removeComment(comment);
                    }
                });

                // set a neutral button in the dialog which will open up the edit entry activity to modify the emotion
                ab.setNeutralButton("Edit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // get the emotion at position of click
                        Emotion emotion = el.get(fPosition);
                        // create a new intent to edit the emotion entry
                        String sentry=el.get(fPosition).toString();
                        Intent intent = new Intent(BrowseEmotionsActivity.this, EditEntry.class);
                        intent.putExtra("entry",sentry);
                        // testing new
                        Curator.getStoredEmotions().removeEmotion(emotion);
                        startActivity(intent);
                        }
                    });

                // required in order for dialog object to appear on screen
                ab.show();
            }
        });
    }

    // Return the user to the menu screen
    public void ReturnHome(View view) {
        // Create an intent to return to the home page upon click of the return home button (onClick in xml)
        Intent intent = new Intent(BrowseEmotionsActivity.this, MainActivity.class);
        startActivity(intent);
    }
}