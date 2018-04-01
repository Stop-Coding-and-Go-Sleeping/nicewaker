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
import java.util.TimeZone;

/**
 * The HomePageActivity shows the homepgae views and provides the function of monitoring the sleep.
 */
public class HomePageActivity extends AppCompatActivity implements View.OnClickListener {
    public static  final  String CLASS_NAME = "HomePageActivity";
    private AlarmManager mAlarmManager;
    private PendingIntent mPendingIntent;
    private Calendar mCalendar;


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
     * a funtion to  choose the Alarm Time
     * @param view
     */
    public void BtnChooseTimeClick(View view) {

//        new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
//            @Override
//            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//                String time = String.format("%d:%d",hourOfDay,minute);
//                System.out.println(time);
//            }
//        },0,0,true).show();
          Log.d(CLASS_NAME,getClass().getName());
//          new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
//              @Override
//              public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//                  Calendar c = Calendar.getInstance();
//                  c.setTimeInMillis(System.currentTimeMillis());
//                  c.set(Calendar.HOUR_OF_DAY,hourOfDay);
//                  c.set(Calendar.MINUTE,minute);
//                  mAlarmManager.set(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(),mPendingIntent);
//                  Log.d(CLASS_NAME,System.currentTimeMillis() + "" );
//                  Toast.makeText(HomePageActivity.this,"Alarm set at " +  + c.get(Calendar.HOUR_OF_DAY) + " 日 " +
//                          c.get(Calendar.MINUTE),Toast.LENGTH_SHORT).show();
//              }
//          },CurrentClendar.get(Calendar.HOUR_OF_DAY),CurrentClendar.get(Calendar.MINUTE),false).show();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_sleep:
                HomePageActivity.this.startActivity(new Intent(HomePageActivity.this, AudioTester.class));
                mAlarmManager.set(AlarmManager.RTC_WAKEUP, mCalendar.getTimeInMillis(), mPendingIntent);
                break;
            case R.id.btn_choose_earlist_time2:
                final Calendar currentTime = Calendar.getInstance();
                new TimePickerDialog(HomePageActivity.this, 0,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view,
                                                  int hourOfDay, int minute) {
                                //设置当前时间
                                mCalendar = Calendar.getInstance(Locale.CHINA);
//                        Log.d(CLASS_NAME, String.valueOf(c.get(Calendar.DAY_OF_MONTH)));
                                mCalendar.setTimeInMillis(System.currentTimeMillis());
//                        c.add(Calendar.MINUTE, minute);
//                        c.add(Calendar.HOUR, hourOfDay);
//                        Log.d("Mainactivity",c.get(Calendar.HOUR_OF_DAY) + " and hour is  " + c.get(Calendar.HOUR));
                                // 根据用户选择的时间来设置Calendar对象
                                mCalendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                                mCalendar.set(Calendar.MINUTE,minute);

                                // 提示闹钟设置完毕:
                                Toast.makeText(HomePageActivity.this, mCalendar.get(Calendar.HOUR_OF_DAY) + ":" +
                                                mCalendar.get(Calendar.MINUTE),
                                        Toast.LENGTH_SHORT).show();
                            }
                        }, currentTime.get(Calendar.HOUR_OF_DAY), currentTime
                        .get(Calendar.MINUTE), false).show();
        }


    }
}
