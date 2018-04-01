package com.example.exp.sleep;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

public class AlarmActivity extends AppCompatActivity {
    private MediaPlayer mMediaPlayer;

    @Override
    protected void onDestroy() {
        Log.d("AlarmActivity","onDestory");
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("AlarmActivity","onCreate");
        setContentView(R.layout.activity_alarm);

        mMediaPlayer = MediaPlayer.create(this,R.raw.dream);
        mMediaPlayer.start();

        //创建一个闹钟提醒的对话框,点击确定关闭铃声与页面
        new AlertDialog.Builder(this).setTitle("闹钟").setMessage("小猪小猪快起床~")
                .setPositiveButton("关闭闹铃", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mMediaPlayer.stop();
                        AlarmActivity.this.finish();
                    }
                }).show();
    }

}
