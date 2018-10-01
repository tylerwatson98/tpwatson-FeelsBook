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
        // reference the emotions manager to initialize it in main activity


        EmotionsManager.Initialize(this.getApplicationContext());
        String sentry = getIntent().getStringExtra("entry");

        String[] parsed = sentry.split(" -- ");
        parsed[0]=parsed[0].trim();
        parsed[1]=parsed[1].trim();
        String [] parsed2=parsed[1].split("\n");


        TextView emotionV = findViewById(R.id.Eemotion);
        emotionV.setText(parsed[0]);
        EditText editDate = findViewById(R.id.Edate);
        editDate.setText(parsed2[0]);
        try {
            EditText editComment = findViewById(R.id.Ecomment);
            editComment.setText(parsed2[1]);
        } catch (RuntimeException r){
            r.printStackTrace();
        }

    }

    private static boolean testDate(String date){
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


    private String getEntry(){
        EditText editText= findViewById(R.id.Ecomment);
        TextView emotion= findViewById(R.id.Eemotion);
        EditText date=findViewById(R.id.Edate);
        return emotion.getText().toString()+" -- "+date.getText().toString()+"\n"+editText.getText().toString();
    }


    // intent to return to emotion history window on click of return button on screen, used if user no longer wants to edit entry
    public void returnToEntries(View view) {
        EditText editDate = findViewById(R.id.Edate);

        if (testDate(editDate.getText().toString())){
            Toast.makeText(this, "Browsing Emotions", Toast.LENGTH_SHORT).show();
            Curator cu = new Curator();
            cu.addEmotion(new Emotion((getEntry())));
            Intent intent = new Intent(EditEntry.this, BrowseEmotionsActivity.class);
            startActivity(intent);
        }
       else{
            Toast.makeText(this, "Improper Date Format", Toast.LENGTH_LONG).show();
        }
    }


    // intent to return to the emotion history window on click of submit edited entry, used if entry is modified
    public void submitNew(View view) {
        EditText editDate = findViewById(R.id.Edate);

        if (testDate(editDate.getText().toString())){
            Curator cu = new Curator();
            Toast.makeText(this, "Entry Successfully Modified", Toast.LENGTH_SHORT).show();
            cu.addEmotion(new Emotion((getEntry())));
            Intent intent = new Intent(EditEntry.this, BrowseEmotionsActivity.class);
            startActivity(intent);
        }
        else{
            Toast.makeText(this, "Improper Date Format", Toast.LENGTH_LONG).show();
        }
    }

}
