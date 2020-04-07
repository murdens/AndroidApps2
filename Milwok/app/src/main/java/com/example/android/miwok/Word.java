package com.example.android.miwok;

public class Word {
    private String cDefaultTranslation;

    private String cMiwokTranslation;

    private int cImageResourceID = NO_IMAGE;

    private static final int NO_IMAGE = 2;

    private int cAudioSourceID;

    public Word(String defaultTranslation, String miwokTranslation, int audioSourceID){
        cDefaultTranslation = defaultTranslation;
        cMiwokTranslation = miwokTranslation;
        cAudioSourceID = audioSourceID;
    }

    public Word(String defaultTranslation, String miwokTranslation,int imageResourceID, int audioSourceID){
        cDefaultTranslation = defaultTranslation;
        cMiwokTranslation = miwokTranslation;
        cImageResourceID = imageResourceID;
        cAudioSourceID = audioSourceID;
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


    public int getAudiosourceId() {
        return cAudioSourceID;
    }
}