package com.jydev.studyhelper.java;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.jydev.studyhelper.R;
import com.jydev.studyhelper.kotlin.AlarmBR;

public class MusicServiceJava extends Service {
    AlarmBRJava alarmBRJava = null;
    View view;
    IntentFilter filter = new IntentFilter(Intent.ACTION_HEADSET_PLUG);

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(alarmBRJava);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate() {
        super.onCreate();
        AlarmManagerJava alarmManagerJava = new AlarmManagerJava(this).getInstance();
        alarmBRJava = new AlarmBRJava();
        registerReceiver(alarmBRJava,filter);
        alarmManagerJava.setAlarmService(this);

        LayoutInflater layoutInflater = LayoutInflater.from(this);
        view = layoutInflater.inflate(R.layout.alarm_dialog,null,false);
        view.findViewById(R.id.test).setOnClickListener(view1 -> alarmManagerJava.onDestroy());
        alarmManagerJava.addView(view);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel("alarmworker","test",NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        Notification notification = new Notification.Builder(this,"alarmworker").setContentTitle("studyHelper앱이 실행중입니다.").setSmallIcon(R.mipmap.ic_launcher).build();
        startForeground(1,notification);
    }
}
