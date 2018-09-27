
/*
Emotion class containing the emotion constructor, getters, and setters
 */

package com.example.tpwatson_feelsbook;

import java.io.Serializable;

// implements serializable so the class can convert an instance into bytes
class Date implements Serializable {

    /*
    Say something is protected because we want things that inherit it to have access to it but we dont want anything accessing
     it outside of the emotion class
     */

    /*
    Private prevents other classes from instantiating objects of the current class
     */

    // Establish a private final String variable "emotion"
    private Date date;
    //private String comment;

    // Emotion constructor
    Date(Date date){
        //this.comment=comment;
        this.date = date;
    }

    // get emotion returns the emotion
    private Date getDate() {
        return this.date;
    }

    // Method call the getEmotion method and returns its result in a string format
    public String toString() {
        return String.valueOf(getDate());
    }

    public int compareTo(Date date) {
        return getDate().compareTo(date.getDate());
    }
}
