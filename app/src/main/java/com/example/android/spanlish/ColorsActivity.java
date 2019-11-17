package com.example.android.spanlish;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import Fragments.ColorsFragment;

public class ColorsActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new ColorsFragment())
                .commit();
    }

}
