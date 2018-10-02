/*
    The sort class will compare the dates of two emotion entries at a time in order to sort them in ascending order based off of the returned values.
    Its single message is created with the ability to catch exceptions which occur during parsing

    Idea for which values to return from the compare method from *RHSeeger (user:26816), https://stackoverflow.com/questions/6478515/return-type-from-a-comparator, 2011/06/25, viewed 2018/09/28*

    Idea for using and implementing comparator from *https://developer.android.com/reference/java/util/Comparator, 2018/07/06, viewed 2018/09/28*

    Idea for learning how to split and parse strings from comment by *Cristian (user:244296), https://stackoverflow.com/questions/3732790/android-split-string, 2010/09/17, viewed 2018/09/28*
 */


package com.example.tpwatson_feelsbook;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

class Sort implements Comparator<Emotion> {


    // compare takes two emotion entries and compares their date in order to sort the entries
    public int compare(Emotion e1, Emotion e2) {
        // set the format
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss", Locale.CANADA);

        // get the two emotions whose dates will be compared
        String test = e1.getEmotion();
        String test2 = e2.getEmotion();
        // split the emotion entries into a string array between the emotion, and the date + comment
        String[] parsed1 = test.split(" -- ");
        String[] parsed2 = test2.split(" -- ");
        // trim the parsed date and comment string of their spaces
        parsed1[1]=parsed1[1].trim();
        parsed2[1]=parsed2[1].trim();
        // split the date and comment into a second string array
        String [] parsed1_2=parsed1[1].split("\n");
        String [] parsed2_2=parsed2[1].split("\n");

        // try to parse the date portions from the second string array, in the listed date format, into their own Date objects
        try {
            Date date1=format.parse(parsed1_2[0]);
            Date date2=format.parse(parsed2_2[0]);

            // if the first emotions date is later than the seconds, return 1
            if (date1.after(date2)){
                return 1;
            }
            // else if the first emotions date is earlier than the seconds, return -1
            else if ((date1.before(date2))){
                return -1;
            }
            // else the dates would be equal so return 0
            else{
                return 0;}


        // catch any parse exception errors so program won't crash if it arises
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

}
