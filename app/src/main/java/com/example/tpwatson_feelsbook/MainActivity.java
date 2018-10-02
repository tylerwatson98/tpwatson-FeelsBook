/*
tpwatson-feelsbook: Store emotion entries created by the user


    The MainActivity will contain all the emotion buttons and the browse activity button. On the click of an emotion
    button the user is taken to the EmotionEntry activity where they can submit the emotion with an optional comment. The
    browse emotions button at the bottom of the screen will bring the user to the screen containing all the stored emotions.
    This screen also displays all the emotion counts

  Idea for learning how to split and parse strings from comment by Cristian (user:244296) at url
  https://stackoverflow.com/questions/3732790/android-split-string

  Idea for passing the string form of the entry via putExtra from
  https://developer.android.com/reference/android/content/Intent#putExtra(java.lang.String,%20android.os.Parcelable)
  */

package com.example.tpwatson_feelsbook;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collection;

// extends
public class MainActivity extends AppCompatActivity {


    // declaration of count variables and initiation of their default 0 values
    private int joyCount,sadCount,angCount,lovCount,feaCount,surCount =0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // set screen to layout specified in activity_main
        setContentView(R.layout.activity_main);
        // reference the emotions manager to initialize it in main activity
        EmotionsManager.Initialize(this.getApplicationContext());
        // get the emotion counts
        Counts();
    }


    // get the counts for each emotion and display them
    @SuppressLint("SetTextI18n")
    private void Counts(){
        // get the collection of stored emotions via the Curator's list getter methods
        Collection<Emotion> emotions=  Curator.getStoredEmotions().listEmotions();
        // get the entire list of emotions in the form of 1 string and split that string at the commas to separate each entry
        String test=String.valueOf(emotions);
        String[] count=test.split(",");
        // for each of the parsed strings check if the string contains the emotion and if so increment its count
        for (String aCount : count) {
            if (aCount.contains("Joy")) {
                joyCount++;
            }
            if (aCount.contains("Sadness")){
                sadCount++;
            }
            if (aCount.contains("Fear")){
                feaCount++;
            }
            if (aCount.contains("Love")){
                lovCount++;
            }
            if (aCount.contains("Surprise")){
                surCount++;
            }
            if (aCount.contains("Anger")){
                angCount++;
            }
        }

        // set all the textviews for the clicker accounts using each counts ID and count integer
        TextView textjoy = findViewById(R.id.joycount);
        textjoy.setText(Integer.toString(joyCount));
        TextView textsad = findViewById(R.id.sadnesscount);
        textsad.setText(Integer.toString(sadCount));
        TextView textfear = findViewById(R.id.fearcount);
        textfear.setText(Integer.toString(feaCount));
        TextView textlove = findViewById(R.id.lovecount);
        textlove.setText(Integer.toString(lovCount));
        TextView textsurprise = findViewById(R.id.surprisecount);
        textsurprise.setText(Integer.toString(surCount));
        TextView textanger = findViewById(R.id.angercount);
        textanger.setText(Integer.toString(angCount));

    }


    // Method containing the new intent which will bring user to the browse emotions activity and layout screen
    public void BrowseEmotions(View view) {
        // Display a brief message on screen upon the browse emotions button being clicked
        Toast.makeText(this, "Browse Emotions", Toast.LENGTH_SHORT).show();
        // Create an intent object containing the bridge to between the two activities
        Intent intent = new Intent(MainActivity.this, BrowseEmotionsActivity.class);
        // Launch the browse emotions activity
        startActivity(intent);
    }


    // newEntry will bring the user to the edit entry activity on any button click, passing the emotion designated with each different emotion button
    public void newEntry (View view) {
        // Switch case scenario which differentiates based on view IDs as to which button was clicked to properly initiate click sequence
        Toast.makeText(this, "New Entry", Toast.LENGTH_SHORT).show();
        switch (view.getId()) {
            // in the case of the joy button being clicked

            case R.id.joybutton:
                // the clicking of this button creates a new intent which takes the user to the emotion entry activity
                Intent intent = new Intent(MainActivity.this, EmotionEntry.class);
                // pass the joy value designated to the button to the emotion entry activity via its key "emotion"
                intent.putExtra("emotion","Joy");
                // start the activity
                startActivity(intent);
                // break from the case so the sequence does not continue onto other cases
                break;

            // each case will perform almost identical methods but will differ in their count IDs & values and the exported message
            case R.id.sadnessbutton:
                Intent intent1 = new Intent(MainActivity.this, EmotionEntry.class);
                intent1.putExtra("emotion","Sadness");
                startActivity(intent1);
                break;

            case R.id.lovebutton:
                Intent intent2 = new Intent(MainActivity.this, EmotionEntry.class);
                intent2.putExtra("emotion","Love");
                startActivity(intent2);
                break;

            case R.id.angerbutton:
                Intent intent3 = new Intent(MainActivity.this, EmotionEntry.class);
                intent3.putExtra("emotion","Anger");
                startActivity(intent3);
                break;

            case R.id.surprisebutton:
                Intent intent4 = new Intent(MainActivity.this, EmotionEntry.class);
                intent4.putExtra("emotion","Surprise");
                startActivity(intent4);
                break;

            case R.id.fearbutton:
                Intent intent5 = new Intent(MainActivity.this, EmotionEntry.class);
                intent5.putExtra("emotion","Fear");
                startActivity(intent5);
                break;
        }
    }
}
