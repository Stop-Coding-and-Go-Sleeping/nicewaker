package com.example.exp.sleep;

import android.Manifest;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TimePicker;

public class HomePageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PackageManager pm = getPackageManager();
        boolean permission = (PackageManager.PERMISSION_GRANTED == pm.checkPermission("android.permission.RECORD_AUDIO", "packageName"));
        if(permission != true){
            ActivityCompat.requestPermissions(HomePageActivity.this,new String[]{Manifest.permission.RECORD_AUDIO},1);
//            new AudioRecordDemo().getNoiseLevel();
        }

        findViewById(R.id.btn_sleep).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomePageActivity.this.startActivity(new Intent(HomePageActivity.this, AudioTester.class));
            }
        });

    }

    public void BtnChooseTimeClick(View view) {

        new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String time = String.format("%d:%d",hourOfDay,minute);
                System.out.println(time);
            }
        },0,0,true).show();

    }
}
