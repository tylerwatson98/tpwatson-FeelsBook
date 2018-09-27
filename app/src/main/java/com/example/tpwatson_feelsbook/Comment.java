
/*
Emotion class containing the emotion constructor, getters, and setters
 */

package com.example.tpwatson_feelsbook;

import java.io.Serializable;

// implements serializable so the class can convert an instance into bytes
class Comment implements Serializable {

    /*
    Say something is protected because we want things that inherit it to have access to it but we dont want anything accessing
     it outside of the emotion class
     */

    /*
    Private prevents other classes from instantiating objects of the current class
     */

    // Establish a private final String variable "emotion"
    private String comment;
    //private String comment;

    // Emotion constructor
    Comment(String comment){
        //this.comment=comment;
        this.comment = comment;
    }

    // get emotion returns the emotion
    private String getComment() {
        return this.comment;
    }

    // Method call the getEmotion method and returns its result in a string format
    public String toString() {
        return getComment();
    }
}
