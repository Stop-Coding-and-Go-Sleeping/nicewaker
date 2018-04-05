package com.example.exp.sleep;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Aims to show the data of the user's sleep
 * Has a testView which shows the graph of user' sleep dynamically.
 */

public class AudioTestActivity extends AppCompatActivity {
    AudioView testView;

    @Override
    protected void onResume() {
        super.onResume();
        testView = new AudioView(this);
        setContentView(testView);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onPause() {
        super.onPause();
        testView.stop();
    }
}



