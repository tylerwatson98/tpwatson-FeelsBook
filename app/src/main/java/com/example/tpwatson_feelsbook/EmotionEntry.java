/*
  The emotion entry class activates after the user clicks on one of the 6 emotions in the main menu screen.
  The date will be automatically listed and uneditable. The comment field can be left empty. If the user attempts
  to return to the menu screen by using the cancel button after modifying the comment field they will be
  warned with an alert dialog that this will delete the entry. Submitting will save and submit the entry then return them
  to the menu screen.

  In emotion_entry.xml I learned how to wrap the comment entry so it did not scroll
  horizontally from a comment by Bryan(user:323696), https://stackoverflow.com/questions/3276380/android-word-wrap-edittext-text/3286921#3286921, 2010/07/20, viewed 2018/09/21*

  Idea for alert dialog creation from *Abram Hindle, CMPUT 301: Saga of Student Picker, https://www.youtube.com/playlist?list=PL240uJOh_Vb4PtMZ0f7N8ACYkCLv0673O, 2014/09/14, viewed 2018/09/15*

  Idea for getting the string form of the entry passed from the MainActivity via getIntent and getStringExtra from
  *https://developer.android.com/reference/android/content/Intent#putExtra(java.lang.String,%20android.os.Parcelable), 2018/07/02, viewed 2018/09/21*

  Knowledge for date format from *https://developer.android.com/reference/java/text/SimpleDateFormat, 2018/06/06, viewed 2018/09/25*
 */


package com.example.tpwatson_feelsbook;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class EmotionEntry extends AppCompatActivity {

    // create an initial entry string for comparison purposes when user tries to cancel entry
    private String initial_entry;
    private Emotion e;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emotion_entry);
        // reference the emotions manager to initialize it in this activity
        EmotionsManager.Initialize(this.getApplicationContext());

        // get emotion passed from the MainActivity
        Intent intent = getIntent();
        String emotion = intent.getStringExtra("emotion");

        // set the Edittext and TextViews to the appropriate layout objects via the findViewId method
        TextView tv = findViewById(R.id.current_emotion);
        EditText editText = findViewById(R.id.optional_comment);
        TextView edate = findViewById(R.id.datetime);

        // set the textView's character sequence to the value passed into the activity
        tv.setText(emotion);

        // create a new datetime object with the specified assignment format and set the date objects text
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss", Locale.CANADA);
        String strdate = dateFormat.format(date);
        edate.setText(strdate);
        // merge the emotion, date, and comment into a single entry which will later be user to compare changes
        initial_entry = tv.getText().toString() + " -- " + edate.getText().toString() + "\n" + editText.getText().toString();

        // add new emotion after 1 click
        e = new Emotion(initial_entry);
        Curator cu = new Curator();
        cu.addEmotion(e);
    }

    // getEntry merges all the elements of an entry on the screen and returns it as a string
    private String getEntry() {
        // get all the variables by their id's and merge these into a single string that will serve as the entry format
        EditText editText = findViewById(R.id.optional_comment);
        TextView emotion = findViewById(R.id.current_emotion);
        TextView date = findViewById(R.id.datetime);
        // return the merged string
        return emotion.getText().toString() + " -- " + date.getText().toString() + "\n" + editText.getText().toString();
    }

    // submit will submit and save the entry then return the user to the home screen
    public void Submit(View view) {
        Toast.makeText(this, "Adding Entry", Toast.LENGTH_SHORT).show();
        // Call the curator and establish an object of reference
        Curator cu = new Curator();
        // remove the emotion we added and add a new one with a possible comment a few lines down
        Curator.getStoredEmotions().removeEmotion(e);


        // using the curators addEmotion method add the merged strings to the stored emotions
        cu.addEmotion(new Emotion(getEntry()));
        // Launch an intent to return to the main screen
        Intent intent = new Intent(EmotionEntry.this, MainActivity.class);
        startActivity(intent);
    }

    // cancel the entry and return home
    public void Home(View view) {
        // check if the initial entry has been modified
        if (!initial_entry.equals(getEntry())) {
            // create an alert dialog to alert user changes have been made and allow them to either cancel entry or return to entry screen
            AlertDialog.Builder ab = new AlertDialog.Builder(EmotionEntry.this);
            ab.setMessage("Warning. Changes have been made to the entry." + "\n" + "Returning to the main screen will not save changes.");
            ab.setCancelable(true);
            // Set a button to return to the MainActivity and don't save changes
            ab.setNeutralButton("Cancel Entry", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // launch an intent to return to the home screen
                    // remove the emotion we added in on create
                    Curator.getStoredEmotions().removeEmotion(e);
                    Intent intent = new Intent(EmotionEntry.this, MainActivity.class);
                    startActivity(intent);
                }
            });

            // set a button which will close the alert dialog
            ab.setNegativeButton("Return to Entry", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            // show the alert dialog on the screen
            ab.show();
        }

        // if the initial entry has not been changed the user will be returned to MainActivity via a new intent
        else {
            Toast.makeText(this, "Returning Home", Toast.LENGTH_SHORT).show();
            // remove the emotion we added in on create
            Curator.getStoredEmotions().removeEmotion(e);
            Intent intent = new Intent(EmotionEntry.this, MainActivity.class);
            startActivity(intent);
        }
    }
}
