package com.jydev.studyhelper.java;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.AudioDeviceInfo;
import android.media.AudioManager;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.jydev.studyhelper.kotlin.AlarmWorker01;
import com.jydev.studyhelper.kotlin.AlarmWorker02;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;


public class AlarmWorker01Java extends Worker {
    private Context mContext;
    public AlarmWorker01Java(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        mContext = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("WrongConstant")
    @NonNull
    @Override
    public Result doWork() {
        AudioManager audioManager = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
        Handler handler = new Handler(Looper.getMainLooper());
        AudioDeviceInfo[] audioDeviceInfos = audioManager.getDevices(AudioManager.GET_DEVICES_ALL);
        for(AudioDeviceInfo data : audioDeviceInfos){
            int type = data.getType();
            if(type==3||type==4||type==22|type==8||type==7){
                handler.post(() -> {
                   audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,10,AudioManager.FLAG_PLAY_SOUND);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        mContext.startForegroundService(new Intent(mContext,MusicServiceJava.class));
                    }
                });
            }
        }
        Calendar cal = Calendar.getInstance();
        AlarmManagerJava alarmManagerJava= new AlarmManagerJava(mContext).getInstance();
        long duration = alarmManagerJava.getTime(cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE),1);
        OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(AlarmWorker01.class).setInitialDelay(duration, TimeUnit.MILLISECONDS).addTag(alarmManagerJava.tag01).build();
        WorkManager workManager = WorkManager.getInstance(mContext);
        workManager.enqueue(workRequest);
        return Result.success();
    }
}
