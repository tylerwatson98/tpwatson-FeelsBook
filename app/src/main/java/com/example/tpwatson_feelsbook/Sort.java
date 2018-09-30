package com.example.tpwatson_feelsbook;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

public class Sort implements Comparator<Emotion> {

    public int compare(Emotion e1, Emotion e2) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss", Locale.CANADA);

        String test = e1.getEmotion();
        String test2 = e2.getEmotion();
        String[] parsed1 = test.split(" -- ");
        String[] parsed2 = test2.split(" -- ");
        parsed1[1]=parsed1[1].trim();
        parsed2[1]=parsed2[1].trim();
        String [] parsed1_2=parsed1[1].split("\n");
        String [] parsed2_2=parsed2[1].split("\n");

        try {
            Date date1=format.parse(parsed1_2[0]);
            Date date2=format.parse(parsed2_2[0]);

            if (date1.after(date2)){
                return 1;
            }
            else if ((date1.before(date2))){
                return -1;
            }
            else{
                return 0;}


        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

}
