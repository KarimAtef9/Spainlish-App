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

public class ColorsActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    //bdl ma a7otha zy el 3adeya f onCreate 3shan mesh kol ma a3ozha acreate wa7da gidida !
    //keda asb7 global variable bst5dmo howa howa kol ma7tago bdl ma acreate f kol marra
    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            releaseMediaPlayer();
//            Toast.makeText(ColorsActivity.this, "DONE", Toast.LENGTH_SHORT).show();
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
        setContentView(R.layout.activity_colors);

        final ArrayList<Word> words = generateWords();

        for (int i = 0; i < words.size(); i++)
            Log.v("ColorsActivity", "Word at index "+i+": " + words.get(i).getEnglishWord());

        WordAdapter adapter = new WordAdapter(this, words, R.color.category_colors);

        ListView listView = (ListView) findViewById(R.id.colorsList);
        listView.setAdapter(adapter);

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                releaseMediaPlayer();

                // Request audio focus for playback
                int result = audioManager.requestAudioFocus(audioFocusChangeListener,
                        // Use the music stream.
                        AudioManager.STREAM_MUSIC,
                        // Request permanent focus.
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    // Start playback.
                    mediaPlayer = MediaPlayer.create(ColorsActivity.this, words.get(position).getAudioID());
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
        words.add(new Word("White", "Blanco", R.drawable.color_white, R.raw.color_white));
        words.add(new Word("Black", "Negro", R.drawable.color_black, R.raw.color_black));
        words.add(new Word("Orange", "Anaranjado", R.drawable.color_orange, R.raw.color_orange));
        words.add(new Word("Yellow", "Amarillo", R.drawable.color_mustard_yellow, R.raw.color_yellow));
        words.add(new Word("Blue", "Azul", R.drawable.color_blue, R.raw.color_blue));
        words.add(new Word("Red", "Rojo", R.drawable.color_red, R.raw.color_red));
        words.add(new Word("Green", "Verde", R.drawable.color_green, R.raw.color_green));
        words.add(new Word("Brown", "Marrón, Café", R.drawable.color_brown, R.raw.color_brown));
        words.add(new Word("Pink", "Rosado", R.drawable.color_pink, R.raw.color_pink));
        words.add(new Word("Purple", "Morado", R.drawable.color_purple, R.raw.color_purple));

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
