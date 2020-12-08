package com.jydev.studyhelper.java;

import android.Manifest;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.jydev.studyhelper.R;
import com.jydev.studyhelper.kotlin.MainActivity;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainActivityJava extends AppCompatActivity {
    AlarmManagerJava alarmManagerJava;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_java);
        Button btn1 = findViewById(R.id.btn);
        Button btn2 = findViewById(R.id.btn02);
        Button btn3 = findViewById(R.id.btn03);
        alarmManagerJava = new AlarmManagerJava(this).getInstance();
        btn1.setOnClickListener(view -> {
            permission(() -> {
                Calendar cal = Calendar.getInstance();
                int curHour = cal.get(Calendar.HOUR_OF_DAY);
                int curMin = cal.get(Calendar.MINUTE);
                new TimePickerDialog(this, (timePicker, hour, min) -> {
                    if(hour<=curHour&&min<=curMin) setAlarm(hour,min,1,alarmManagerJava.tag01);
                    else setAlarm(hour,min,0,alarmManagerJava.tag01);
                },curHour,curMin,false).show();
            });
        });

        btn2.setOnClickListener(view -> {
            permission(() -> {
                Calendar cal = Calendar.getInstance();
                int curHour = cal.get(Calendar.HOUR_OF_DAY);
                int curMin = cal.get(Calendar.MINUTE);
                new TimePickerDialog(this, (timePicker, hour, min) -> {
                    if(hour<=curHour&&min<=curMin) setAlarm(hour,min,1,alarmManagerJava.tag02);
                    else setAlarm(hour,min,0,alarmManagerJava.tag02);
                },curHour,curMin,false).show();
            });
        });

        btn3.setOnClickListener(view -> {
            WorkManager.getInstance().cancelAllWork();
        });

    }

    private void setAlarm(int hour, int minute, int interval,String tag){
        Toast.makeText(this,"알람이 등록되었습니다.",Toast.LENGTH_SHORT).show();
        long duration = alarmManagerJava.getTime(hour,minute,interval);
        OneTimeWorkRequest workRequest = (tag.equals(alarmManagerJava.tag01)) ? new OneTimeWorkRequest.Builder(AlarmWorker01Java.class).setInitialDelay(duration,TimeUnit.MILLISECONDS).addTag(tag).build() : new OneTimeWorkRequest.Builder(AlarmWorker02Java.class).setInitialDelay(duration,TimeUnit.MILLISECONDS).addTag(tag).build();
        WorkManager workManager = WorkManager.getInstance();
        workManager.cancelAllWorkByTag(tag);
        workManager.enqueue(workRequest);
    }

    private void permission(PermissionListner permissionListner){
        TedPermission.with(this)
                .setPermissions(Manifest.permission.SYSTEM_ALERT_WINDOW)
                .setPermissionListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        permissionListner.permissionGranted();
                    }

                    @Override
                    public void onPermissionDenied(List<String> deniedPermissions) {

                    }
                }).check();
    }
}