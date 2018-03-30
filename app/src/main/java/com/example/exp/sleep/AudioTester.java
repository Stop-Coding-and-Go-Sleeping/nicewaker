package com.example.exp.sleep;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public class AudioTester extends AppCompatActivity {
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



