package com.example.tpwatson_feelsbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.util.Collection;

public class EditEntry extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_entry);
        // reference the emotions manager to initialize it in main activity


        EmotionsManager.Initialize(this.getApplicationContext());
        Collection<Emotion> emotions = Curator.getStoredEmotions().listEmotions();

        /*String sentry = getIntent().getStringExtra("entry");

        String[] parsed = sentry.split("--");
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
        */
    }


    // intent to return to emotion history window on click of return button on screen, used if user no longer wants to edit entry
    public void returnToEntries(View view) {
        Toast.makeText(this, "Browsing Emotions", Toast.LENGTH_SHORT).show();
        /*
        Curator cu = new Curator();
        TextView textView= findViewById(R.id.Eemotion);
        EditText date = findViewById(R.id.Edate);
        EditText comment = findViewById(R.id.Ecomment);
        String merge1=textView.getText().toString();
        String merge2=date.getText().toString();
        String merge3=comment.getText().toString();
        String mergeall=merge1+" -- "+merge2+"\n"+merge3;
        //cu.addEmotion(new Emotion((mergeall)));*/
        Intent intent = new Intent(EditEntry.this, BrowseEmotionsActivity.class);
        startActivity(intent);
    }

    // intent to return to the emotion history window on click of submit edited entry, used if entry is modified
    public void submitNew(View view) {
        Toast.makeText(this, "Entry Successfully Modified", Toast.LENGTH_SHORT).show();
        /*
        Curator cu = new Curator();
        TextView textView= findViewById(R.id.Eemotion);
        EditText date = findViewById(R.id.Edate);
        EditText comment = findViewById(R.id.Ecomment);
        String merge1=textView.getText().toString();
        String merge2=date.getText().toString();
        String merge3=comment.getText().toString();
        String mergeall=merge1+" -- "+merge2+"\n"+merge3;

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.CANADA);
        try {
            Date date1= dateFormat.parse(merge2);
            //cu.addEmotion(new Emotion((mergeall)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //cu.addEmotion(new Emotion((mergeall)));
        */
        Intent intent = new Intent(EditEntry.this, BrowseEmotionsActivity.class);
        startActivity(intent);
    }
}
