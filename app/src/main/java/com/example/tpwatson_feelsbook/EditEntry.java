
/*
 The edit entry class opens when the user desires to edit an emotion entry. It allows the user to change the date as well
 as the comment. The emotion is unchangeable. The class will test if the date is in the correct format. Additionally the user can either
 submit an emotion or stop their wish to edit the entry.

  In edit_entry.xml I learned how to wrap the comment entry so it did not scroll
  horizontally from a comment by Bryan(user:323696) at the url
  https://stackoverflow.com/questions/3276380/android-word-wrap-edittext-text/3286921#3286921

  Idea for learning how to split and parse strings from comment by Cristian (user:244296) at url
  https://stackoverflow.com/questions/3732790/android-split-string

  Idea for getting the string form of the entry passed from the BrowseEmotions activity via getIntent and getStringExtra from
  https://developer.android.com/reference/android/content/Intent#putExtra(java.lang.String,%20android.os.Parcelable)

  Idea for implementing the code within the testDate method from the tutorial at the following url http://www.java2s.com/Tutorial/Java/0120__Development/CheckifaStringisavaliddate.htm

  Knowledge for date format from https://developer.android.com/reference/java/text/SimpleDateFormat
 */

package com.example.tpwatson_feelsbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class EditEntry extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_entry);
        // reference the emotions manager to initialize it in this activity
        EmotionsManager.Initialize(this.getApplicationContext());
        // get the string intent passed from browse emotions activity which will contain the emotion entry in string format
        String sentry = getIntent().getStringExtra("entry");

        // parse the entry string and remove spaces from it
        String[] parsed = sentry.split(" -- ");
        parsed[0]=parsed[0].trim();
        parsed[1]=parsed[1].trim();
        String [] parsed2=parsed[1].split("\n");

        // set the emotion textview, date, and comment edit texts from the parsed string
        TextView emotionV = findViewById(R.id.Eemotion);
        emotionV.setText(parsed[0]);
        EditText editDate = findViewById(R.id.Edate);
        editDate.setText(parsed2[0]);
        // try and catch clauses for the parse exception of the comment
        try {
            EditText editComment = findViewById(R.id.Ecomment);
            editComment.setText(parsed2[1]);
        } catch (RuntimeException r){
            r.printStackTrace();
        }

    }

    // test the date to make sure if the user changes the entries date it follows the proper date format. Returns a boolean object for whether the date is in proper format
    private static boolean testDate(String date){
        // establish the date format and make the format non lenient, include a parse catch and try clause
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss", Locale.CANADA);
        format.setLenient(false);
        try{
            format.parse(date.trim());
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    // combines all the elements of the entry into a single string for the new edited entry
    private String getEntry(){
        EditText editText= findViewById(R.id.Ecomment);
        TextView emotion= findViewById(R.id.Eemotion);
        EditText date=findViewById(R.id.Edate);
        return emotion.getText().toString()+" -- "+date.getText().toString()+"\n"+editText.getText().toString();
    }

    // intent to return to emotion history window on click of return button on screen, used if user no longer wants to edit entry
    public void returnToEntries(View view) {
        EditText editDate = findViewById(R.id.Edate);

        // if the date format is correct the curator will be called so the edited entry can be added to the stored emotions
        if (testDate(editDate.getText().toString())){
            Toast.makeText(this, "Browsing Emotions", Toast.LENGTH_SHORT).show();
            Curator cu = new Curator();
            cu.addEmotion(new Emotion((getEntry())));
            // new intent will bring the user to the browse emotions screen
            Intent intent = new Intent(EditEntry.this, BrowseEmotionsActivity.class);
            startActivity(intent);
        }
        // else if the date format is not correct alert the user
       else{
            Toast.makeText(this, "Improper Date Format", Toast.LENGTH_LONG).show();
        }
    }

    // intent to return to the emotion history window on click of submit edited entry, used if entry is modified
    public void submitNew(View view) {
        EditText editDate = findViewById(R.id.Edate);


        // if the date format is correct the curator will be called so the new edited entry can be added to the stored emotions
        if (testDate(editDate.getText().toString())){
            Curator cu = new Curator();
            Toast.makeText(this, "Entry Successfully Modified", Toast.LENGTH_SHORT).show();
            cu.addEmotion(new Emotion((getEntry())));
            // new intent will bring the user to the browse emotions screen
            Intent intent = new Intent(EditEntry.this, BrowseEmotionsActivity.class);
            startActivity(intent);
        }
        else{
            Toast.makeText(this, "Improper Date Format", Toast.LENGTH_LONG).show();
        }
    }

}
