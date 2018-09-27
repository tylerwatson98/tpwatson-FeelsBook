
/*
Emotion class containing the emotion constructor, getters, and setters
 */

package com.example.tpwatson_feelsbook;

import java.io.Serializable;

// implements serializable so the class can convert an instance into bytes
class Emotion implements Serializable{

    /*
    Say something is protected because we want things that inherit it to have access to it but we dont want anything accessing
     it outside of the emotion class
     */

    /*
    Private prevents other classes from instantiating objects of the current class
     */

    // Establish a private final String variable "emotion"
    private String emotion;

    // Emotion constructor
    Emotion(String emotion) {
        //this.comment=comment;
        this.emotion = emotion;
    }

    // get emotion returns the emotion
    private String getEmotion() {
        return this.emotion;
    }

    // Method call the getEmotion method and returns its result in a string format
    public String toString() {
        return getEmotion();
    }

    @Override
    public boolean equals(Object o) {
        return o != null && o.getClass() == this.getClass() && this.equals((Emotion)o);
    }

    public boolean equals(Emotion e) {
        return e != null && getEmotion().equals(e.getEmotion());
    }
    public int hashcode(){
        return (getEmotion()).hashCode();
    }

}
