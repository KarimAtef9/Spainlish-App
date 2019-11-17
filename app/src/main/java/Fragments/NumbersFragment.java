package Fragments;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;


import androidx.fragment.app.Fragment;

import com.example.android.spanlish.R;
import com.example.android.spanlish.Word;
import com.example.android.spanlish.WordAdapter;

import java.util.ArrayList;


public class NumbersFragment extends Fragment {
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
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                    mediaPlayer.pause();
                    mediaPlayer.seekTo(0);
                    break;
            }
        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView;
        rootView = inflater.inflate(R.layout.activity_words, container, false);

        final ArrayList<Word> words = generateWords();

        for (int i = 0; i < words.size(); i++)
            Log.v("NumbersActivity", "Word at index "+i+": " + words.get(i).getEnglishWord());

        final WordAdapter adapter = new WordAdapter(getActivity(), words, R.color.category_numbers);

        final ListView listView = rootView.findViewById(R.id.wordsList);

        listView.setAdapter(adapter);

        audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

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
                    mediaPlayer = MediaPlayer.create(getActivity(), words.get(position).getAudioID());
                    mediaPlayer.start();
                    //set up a listener on the media player
                    mediaPlayer.setOnCompletionListener(mCompletionListener);
                }

            }
        });

        return rootView;
    }

    @Override
    public void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    private ArrayList<Word> generateWords() {
        ArrayList<Word> words = new ArrayList<>();
        words.add(new Word("one", "Uno", R.drawable.numbers_one, R.raw.number_one));
        words.add(new Word("two", "dos", R.drawable.numbers_two, R.raw.number_two));
        words.add(new Word("three", "Tres", R.drawable.numbers_three, R.raw.number_three));
        words.add(new Word("four", "cuatro", R.drawable.numbers_four, R.raw.number_four));
        words.add(new Word("five", "cinco", R.drawable.numbers_five, R.raw.number_five));
        words.add(new Word("six", "seis", R.drawable.numbers_six, R.raw.number_six));
        words.add(new Word("seven", "Siete", R.drawable.numbers_seven, R.raw.number_seven));
        words.add(new Word("eight", "ocho", R.drawable.numbers_eight, R.raw.number_eight));
        words.add(new Word("nine", "nueve", R.drawable.numbers_nine, R.raw.number_nine));
        words.add(new Word("ten", "diez", R.drawable.numbers_ten, R.raw.number_ten));
        words.add(new Word("eleven", "once", R.drawable.numbers_eleven, R.raw.number_eleven));
        words.add(new Word("twelve", "doce", R.drawable.numbers_twelve, R.raw.number_twelve));
        words.add(new Word("thirteen", "trece", R.drawable.numbers_thirteen, R.raw.number_thirteen));
        words.add(new Word("fourteen", "catorce", R.drawable.numbers_fourteen, R.raw.number_forteen));
        words.add(new Word("fifteen", "quince", R.drawable.numbers_fifteen, R.raw.number_fifteen));
        words.add(new Word("sixteen", "dieciséis", R.drawable.numbers_sixteen, R.raw.number_sixteen));
        words.add(new Word("seventeen", "de diecisiete", R.drawable.numbers_seventeen, R.raw.number_seventeen));
        words.add(new Word("eighteen", "Dieciocho", R.drawable.numbers_eighteen, R.raw.number_eighteen));
        words.add(new Word("nineteen", "diecinueve", R.drawable.numbers_nineteen, R.raw.number_nineteen));
        words.add(new Word("twenty", "veinte", R.drawable.numbers_twenty, R.raw.number_twenty));
        words.add(new Word("thirty", "treinta", R.drawable.numbers_thirty, R.raw.number_thirty));
        words.add(new Word("forty", "cuarenta", R.drawable.numbers_forty, R.raw.number_forty));
        words.add(new Word("fifty", "cincuenta", R.drawable.numbers_fifty, R.raw.number_fifty));
        words.add(new Word("sixty", "sesenta", R.drawable.numbers_sixty, R.raw.number_sixty));
        words.add(new Word("seventy", "setenta", R.drawable.numbers_seventy, R.raw.number_seventy));
        words.add(new Word("eighty", "ochenta", R.drawable.numbers_eighty, R.raw.number_eighty));
        words.add(new Word("ninety", "noventa", R.drawable.numbers_ninety, R.raw.number_ninety));
        words.add(new Word("hundred", "cien", R.drawable.numbers_hundred, R.raw.number_hundred));

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
