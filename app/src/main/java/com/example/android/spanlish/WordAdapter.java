package com.example.android.spanlish;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class WordAdapter extends ArrayAdapter<Word> {
    private ArrayList<Word> words;
    private int colorResourceId;

    public WordAdapter (Activity context, ArrayList<Word> words, int colorID) {
        super(context, 0, words);
        this.words = words;
        colorResourceId = colorID;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list, parent, false);
        }

        Word currentWord = words.get(position);

        TextView englishWord = (TextView) listItemView.findViewById(R.id.englishWord);
        englishWord.setText(currentWord.getEnglishWord());

        TextView spanishWord = (TextView) listItemView.findViewById(R.id.spanishWord);
        spanishWord.setText(currentWord.getSpanishWord());

        ImageView image = (ImageView) listItemView.findViewById(R.id.imageId);
        if (currentWord.isImageProvided()) {
            image.setImageResource(currentWord.getImageID());
            image.setVisibility(View.VISIBLE);
        } else {
            image.setVisibility(View.GONE);
        }

        View textContainer = listItemView.findViewById(R.id.textContainer);

        int color = ContextCompat.getColor(getContext(), colorResourceId);
        textContainer.setBackgroundColor(color);

        return listItemView;
    }
}
