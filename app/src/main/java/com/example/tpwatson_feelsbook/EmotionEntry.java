/*
  The emotion entry class activates after the user clicks on one of the 6 emotions in the main menu screen.
  The date will be automatically listed and uneditable. The comment field can be left empty. If the user attempts
  to return to the menu screen by using the cancel button after modifying the comment field they will be
  warned with an alert dialog that this will delete the entry. Submitting will save and submit the entry then return them
  to the menu screen.

  In emotion_entry.xml I learned how to wrap the comment entry so it did not scroll
  horizontally from a comment by Bryan(user:323696) at the url
  https://stackoverflow.com/questions/3276380/android-word-wrap-edittext-text/3286921#3286921

  Idea for alert dialog creation from Abram Hindle's youtube tutorial "Student Picker for android" Saga
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

    private String initial_entry;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emotion_entry);
        // reference the emotions manager to initialize it in main activity
        EmotionsManager.Initialize(this.getApplicationContext());
        Intent intent= getIntent();
        String emotion=intent.getStringExtra("emotion");
        TextView tv = findViewById(R.id.current_emotion);
        EditText editText= findViewById(R.id.optional_comment);
        TextView edate=findViewById(R.id.datetime);
        // set the textView's character sequence to the value passed into the activity
        tv.setText(emotion);

        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss", Locale.CANADA);
        String strdate=dateFormat.format(date);
        edate.setText(strdate);

        initial_entry=tv.getText().toString()+" -- "+edate.getText().toString()+"\n"+editText.getText().toString();
    }

    // getEntry merges all the elements of an entry on the screen and returns it as a string
    private String getEntry(){
        // get all the variables by their id's and merge these into a single string that will serve as the entry format
        EditText editText= findViewById(R.id.optional_comment);
        TextView emotion= findViewById(R.id.current_emotion);
        TextView date=findViewById(R.id.datetime);
        // return the merged string
        return emotion.getText().toString()+" -- "+date.getText().toString()+"\n"+editText.getText().toString();
    }

    // submit will submit and save the entry then return the user to the home screen
    public void Submit(View view){
        Toast.makeText(this,"Adding Entry", Toast.LENGTH_SHORT).show();
        // Call the curator and establish an object of reference
        Curator cu = new Curator();

        // using the curators addEmotion method add the merged strings to the stored emotions
        cu.addEmotion(new Emotion(getEntry()));
        // Launch an intent to return to the main screen
        Intent intent = new Intent(EmotionEntry.this, MainActivity.class);
        startActivity(intent);
    }

    // cancel the entry and return home
    public void Home(View view){
        // check if the initial entry has been modified
        if (!initial_entry.equals(getEntry())){
            // create an alert dialog to alert user changes have been made and allow them to either cancel entry or return to entry screen
            AlertDialog.Builder ab = new AlertDialog.Builder(EmotionEntry.this);
            ab.setMessage("Warning. Changes have been made to the entry."+"\n"+"Returning to the main screen will not save changes.");
            ab.setCancelable(true);
            // Set a button to return to the MainActivity and don't save changes
            ab.setNeutralButton("Cancel Entry", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // launch an intent to return to the home screen
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
        else{
            Toast.makeText(this,"Returning Home", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(EmotionEntry.this, MainActivity.class);
            startActivity(intent);
        }
    }
}
