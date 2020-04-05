package com.example.android.miwok;

public class Word {
    private String cDefaultTranslation;

    private String cMiwokTranslation;

    private int cImageResourceID = NO_IMAGE;

    private static final int NO_IMAGE = 2;

    public Word(String defaultTranslation, String miwokTranslation){
        cDefaultTranslation = defaultTranslation;
        cMiwokTranslation = miwokTranslation;
    }

    public Word(String defaultTranslation, String miwokTranslation,int imageResourceID){
        cDefaultTranslation = defaultTranslation;
        cMiwokTranslation = miwokTranslation;
        cImageResourceID = imageResourceID;
    }

    public String getDefaultTranslation(){
        return cDefaultTranslation;
    }

    public String getMiwokTranslation() {
        return cMiwokTranslation;
    }
    public int getImageResourceId() {
        return cImageResourceID;
    }

    /**
     * Returns whether or not there is an image for this word.
     */
    public boolean hasImage() {
        return cImageResourceID != NO_IMAGE;
    }
}