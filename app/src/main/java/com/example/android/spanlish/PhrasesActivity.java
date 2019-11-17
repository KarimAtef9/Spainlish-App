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

public class PhrasesActivity extends AppCompatActivity {
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
            Log.v("PhrasesActivity", "Word at index "+i+": " + words.get(i).getEnglishWord());

        WordAdapter adapter = new WordAdapter(this, words, R.color.category_phrases);

        ListView listView = (ListView) findViewById(R.id.wordsList);
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
                    mediaPlayer = MediaPlayer.create(PhrasesActivity.this, words.get(position).getAudioID());
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
        words.add(new Word("hello", "hola", R.raw.phrases_hello));
        words.add(new Word("good morning", "buenos días", R.raw.phrases_goodmorning));
        words.add(new Word("good afternoon", "buenas tardes", R.raw.phrases_goodafternoon));
        words.add(new Word("good evening", "buenas noches", R.raw.phrases_goodevening));
        words.add(new Word("goodbye", "adiós", R.raw.phrases_goodbye));
        words.add(new Word("see you soon", "hasta pronto", R.raw.phrases_seeyousoon));
        words.add(new Word("see ya", "nos vemos", R.raw.phrases_seeya));
        words.add(new Word("How are you?", "Cómo estás?", R.raw.phrases_howareyou));
        words.add(new Word("What’s up?", "Qué tal?", R.raw.phrases_whatsup));
        words.add(new Word("What’s happening?", "Qué pasa?", R.raw.phrases_whatshappening));
        words.add(new Word("What are you doing?", "Qué haces?", R.raw.phrases_whatareyoudoing));
        words.add(new Word("Well, thanks. / Very well.", "Bien, gracias. / Muy bien.", R.raw.phrases_verywell));
        words.add(new Word("All good.", "Todo bien.", R.raw.phrases_allgood));
        words.add(new Word("Bad.", "Mal.", R.raw.phrases_bad));
        words.add(new Word("Nothing.", "Nada.", R.raw.phrases_nothing));
        words.add(new Word("I’m sorry.", "Lo siento.", R.raw.phrases_iamsorry));
        words.add(new Word("Excuse me!", "Perdón!", R.raw.phrases_excuseme));
        words.add(new Word("I love you.", "Te amo.", R.raw.phrases_iloveyou));
        words.add(new Word("I need help", "Necesito ayuda.", R.raw.phrases_ineedhelp));
        words.add(new Word("Have fun!", "Diviértete!", R.raw.phrases_havefun));
        words.add(new Word("Good luck!", "Buena suerte!", R.raw.phrases_goodluck));
        words.add(new Word("Take care!", "Cuídate!", R.raw.phrases_takecare));
        words.add(new Word("Congratulations!", "Felicitaciones!", R.raw.phrases_congratulations));
        words.add(new Word("Cheers!", "Salud!", R.raw.phrases_cheers));
        words.add(new Word("Well done!", "Muy bien!", R.raw.phrases_welldone));
        words.add(new Word("Welcome!", "Bienvenidos! / Bienvenidas!", R.raw.phrases_welcome));
        words.add(new Word("Happy Birthday!", "Feliz Cumpleaños!", R.raw.phrases_happybirthday));
        words.add(new Word("Merry Christmas!", "Feliz Navidad!", R.raw.phrases_merrychristmas));
        words.add(new Word("Happy New Year!", "Feliz Año Nuevo!", R.raw.phrases_happynewyear));

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
