package com.example.android.spanlish;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class FamilyActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    //bdl ma a7otha zy el 3adeya f onCreate 3shan mesh kol ma a3ozha acreate wa7da gidida !
    //keda asb7 global variable bst5dmo howa howa kol ma7tago bdl ma acreate f kol marra
    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            releaseMediaPlayer();
        }
    };

    private AudioManager audioManager;
    private AudioManager.OnAudioFocusChangeListener audioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {

        @Override
        public void onAudioFocusChange(int focusChange) {
            switch (focusChange) {
                case AudioManager.AUDIOFOCUS_GAIN:
                    mediaPlayer.start();
                    break;
                case AudioManager.AUDIOFOCUS_LOSS:
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    break;
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                    mediaPlayer.pause();
                    mediaPlayer.seekTo(0);
                    break;
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                    mediaPlayer.pause();
                    mediaPlayer.seekTo(0);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_words);

        final ArrayList<Word> words = generateWords();

        for (int i = 0; i < words.size(); i++)
            Log.v("FamilyActivity", "Word at index "+i+": " + words.get(i).getEnglishWord());

        WordAdapter adapter = new WordAdapter(this, words, R.color.category_family);

        ListView listView = (ListView) findViewById(R.id.wordsList);
        listView.setAdapter(adapter);

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               releaseMediaPlayer();

               // Request audio focus for playback
               int result = audioManager.requestAudioFocus(audioFocusChangeListener,
                       // Use the music stream.
                       AudioManager.STREAM_MUSIC,
                       // Request permanent focus.
                       AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
               if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                   // Start playback.
                   mediaPlayer = MediaPlayer.create(FamilyActivity.this, words.get(position).getAudioID());
                   mediaPlayer.start();
                   //set up a listener on the media player
                   mediaPlayer.setOnCompletionListener(mCompletionListener);
               }
           }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    private ArrayList<Word> generateWords() {
        ArrayList<Word> words = new ArrayList<Word>();
        words.add(new Word("Father", "Padre", R.drawable.family_father, R.raw.family_father));
        words.add(new Word("Mother", "Madre", R.drawable.family_mother, R.raw.family_mother));
        words.add(new Word("Brother", "Hermano", R.drawable.family_brother, R.raw.family_brother));
        words.add(new Word("Sister", "Hermana", R.drawable.family_sister, R.raw.family_sister));
        words.add(new Word("Grandfather", "Abuelo", R.drawable.family_grandfather, R.raw.family_grandfather));
        words.add(new Word("Grandmother", "Abuela",R.drawable.family_grandmother, R.raw.family_grandmother));
        words.add(new Word("Uncle", "Tío", R.drawable.family_uncle, R.raw.family_uncle));
        words.add(new Word("Aunt", "Tía",R.drawable.family_aunt, R.raw.family_aunt));
        words.add(new Word("Husband", "Esposo", R.drawable.family_father, R.raw.family_husband));
        words.add(new Word("Wife", "Esposa",R.drawable.family_mother, R.raw.family_wife));
        words.add(new Word("Son", "Hijo", R.drawable.family_son, R.raw.family_son));
        words.add(new Word("Daughter", "Hija",R.drawable.family_daughter, R.raw.family_daughter));
        words.add(new Word("Nephew", "Sobrino",R.drawable.family_nephew, R.raw.family_nephew));
        words.add(new Word("Niece", "Sobrina",R.drawable.family_niece, R.raw.family_niece));
        words.add(new Word("Cousin", "Primo, Prima",R.drawable.family_cousin, R.raw.family_cosin));

        return words;
    }

    private void releaseMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
            //abandon audio focus when playback complete
            audioManager.abandonAudioFocus(audioFocusChangeListener);
        }
    }
}
