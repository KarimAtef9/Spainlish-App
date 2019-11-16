package com.example.android.spanlish;

public class Word {
    private String mEnglishWord;
    private String mSpanishWord;
    private final int NOIMAGEPROVIDED = -1;
    private int imageID = NOIMAGEPROVIDED;
    private int audioID;

    public Word (String englishWord, String spanishWord, int audioResourceId) {
        mEnglishWord = englishWord;
        mSpanishWord = spanishWord;
        audioID = audioResourceId;
    }

    public Word (String englishWord, String spanishWord, int imageResourceId, int audioResourceId) {
        mEnglishWord = englishWord;
        mSpanishWord = spanishWord;
        imageID = imageResourceId;
        audioID = audioResourceId;
    }

    public String getEnglishWord() {
        return mEnglishWord;
    }

    public String getSpanishWord() {
        return mSpanishWord;
    }

    public int getImageID() {
        return imageID;
    }

    public boolean isImageProvided() {
        return imageID != NOIMAGEPROVIDED;
    }

    public int getAudioID() {
        return audioID;
    }

    @Override
    public String toString() {
        return "Word{" +
                "mEnglishWord='" + mEnglishWord + '\'' +
                ", mSpanishWord='" + mSpanishWord + '\'' +
                ", NOIMAGEPROVIDED=" + NOIMAGEPROVIDED +
                ", imageID=" + imageID +
                ", audioID=" + audioID +
                '}';
    }
}
