package com.example.tpwatson_feelsbook;

import android.content.Intent;
import android.os.Bundle;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emotion_entry);
        // reference the emotions manager to initialize it in main activity
        EmotionsManager.Initialize(this.getApplicationContext());
        Intent intent= getIntent();
        String emotion=intent.getStringExtra("emotion");
        TextView tv = findViewById(R.id.current_emotion);
        // set the textView's character sequence to the value passed into the activity
        tv.setText(emotion);

        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.CANADA);
        String strdate=dateFormat.format(date);
        TextView edate=findViewById(R.id.datetime);
        edate.setText(strdate);
    }

    public void Submit(View view) {
        Toast.makeText(this,"Adding Entry", Toast.LENGTH_SHORT).show();
        // Call the curator and establish an object of reference
        Curator cu = new Curator();
        EditText editText= findViewById(R.id.optional_comment);
        // assign the textview object the element via its id "current_emotion"
        TextView emotion= findViewById(R.id.current_emotion);
        TextView date=findViewById(R.id.datetime);
        String merge1=emotion.getText().toString();
        String merge2=date.getText().toString();
        String merge3=editText.getText().toString();
        String mergeall=merge1+" - "+merge2+"\n"+merge3;


        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.CANADA);
        Date date1 = new Date();

        // using the curators addEmotion method add the text associated with the textView object in a string format to the stored emotions
        cu.addEmotion(new Emotion(mergeall));

        //cu.addComment(new Comment(merge3));
    }

    public void Home(View view){
        Toast.makeText(this,"Returning Home", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(EmotionEntry.this, MainActivity.class);
        startActivity(intent);
    }
}
