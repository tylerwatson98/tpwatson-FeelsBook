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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class EmotionEntry extends AppCompatActivity {

    public String initial_entry;


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
        // set the textView's character sequence to the value passed into the activity
        tv.setText(emotion);

        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss", Locale.CANADA);
        String strdate=dateFormat.format(date);
        TextView edate=findViewById(R.id.datetime);
        edate.setText(strdate);

        initial_entry=tv.getText().toString()+" -- "+edate.getText().toString()+"\n"+editText.getText().toString();
    }

    public String getEntry(){
        EditText editText= findViewById(R.id.optional_comment);
        // assign the textview object the element via its id "current_emotion"
        TextView emotion= findViewById(R.id.current_emotion);
        TextView date=findViewById(R.id.datetime);
        return emotion.getText().toString()+" -- "+date.getText().toString()+"\n"+editText.getText().toString();
    }

    public void Submit(View view) throws ParseException {
        Toast.makeText(this,"Adding Entry", Toast.LENGTH_SHORT).show();
        // Call the curator and establish an object of reference
        Curator cu = new Curator();

        Date date1 = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.CANADA);
        String strdate=dateFormat.format(date1);
        Date date2=dateFormat.parse(strdate);

        // using the curators addEmotion method add the text associated with the textView object in a string format to the stored emotions
        cu.addEmotion(new Emotion(getEntry()));
        //cu.addComment(new Comment(merge3));

        Toast.makeText(this,"Submitting", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(EmotionEntry.this, MainActivity.class);
        startActivity(intent);
    }

    public void Home(View view){
        if (!initial_entry.equals(getEntry())){
            AlertDialog.Builder ab = new AlertDialog.Builder(EmotionEntry.this);
            ab.setMessage("Warning. Changes have been made to the entry."+"\n"+"Returning to the main screen will not save changes.");
            ab.setCancelable(true);
            // Set a button to return to the home and don't save changes
            ab.setNeutralButton("Cancel Entry", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(EmotionEntry.this, MainActivity.class);
                    startActivity(intent);
                }
            });

            // set a button which will submit the entry in case user tries to exit activity without saving their work
            ab.setNegativeButton("Return to Entry", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            ab.show();
        }
        else{
            Toast.makeText(this,"Returning Home", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(EmotionEntry.this, MainActivity.class);
            startActivity(intent);
        }
    }
}
