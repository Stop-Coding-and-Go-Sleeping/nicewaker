package com.example.exp.sleep;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Locale;

/**
 * The HomePageActivity shows the homepgae views and provides the entrance of setting time and monitoring.
 */

public class HomePageActivity extends AppCompatActivity implements View.OnClickListener {
    public static  final  String CLASS_NAME = "HomePageActivity";
    private AlarmManager mAlarmManager;
    private PendingIntent mPendingIntent;
    private Calendar mCalendar;

    /**
     * Init The intents and check the permission
     */
    public  void  Init(){
        Intent intent = new Intent(HomePageActivity.this,AlarmActivity.class);
        mPendingIntent = PendingIntent.getActivity(HomePageActivity.this,0,intent,0);
        mAlarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        mCalendar = Calendar.getInstance();

        PackageManager pm = getPackageManager();
        boolean permission = (PackageManager.PERMISSION_GRANTED == pm.checkPermission("android.permission.RECORD_AUDIO", "packageName"));
        if(permission != true){
            ActivityCompat.requestPermissions(HomePageActivity.this,new String[]{Manifest.permission.RECORD_AUDIO},1);
//            new AudioRecordDemo().getNoiseLevel();

        }
        findViewById(R.id.btn_sleep).setOnClickListener(this);
        findViewById(R.id.btn_choose_earlist_time2).setOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Init();
    }


    /**
     * implement the onCLick funtion to respond two types of click : choose time and begin to sleep
     * @param v
     */

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            // begin to sleep
            case R.id.btn_sleep:
                HomePageActivity.this.startActivity(new Intent(HomePageActivity.this, AudioTestAcitivity.class));
                mAlarmManager.set(AlarmManager.RTC_WAKEUP, mCalendar.getTimeInMillis(), mPendingIntent);
                break;
            case R.id.btn_choose_earlist_time2:
                final Calendar currentTime = Calendar.getInstance();
                new TimePickerDialog(HomePageActivity.this, 0,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view,
                                                  int hourOfDay, int minute) {
                                //set the current time
                                mCalendar = Calendar.getInstance(Locale.CHINA);
                                mCalendar.setTimeInMillis(System.currentTimeMillis());

                                // Set the Current time
                                mCalendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                                mCalendar.set(Calendar.MINUTE,minute);

                                // make to toast to infrom user that alarm have been set.
                                Toast.makeText(HomePageActivity.this, mCalendar.get(Calendar.HOUR_OF_DAY) + ":" +
                                                mCalendar.get(Calendar.MINUTE),
                                        Toast.LENGTH_SHORT).show();
                            }
                        }, currentTime.get(Calendar.HOUR_OF_DAY), currentTime
                        .get(Calendar.MINUTE), false).show();
        }
    }
}
