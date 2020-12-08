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


public class AlarmWorker02Java extends Worker {
    private Context mContext;
    public AlarmWorker02Java(@NonNull Context context, @NonNull WorkerParameters workerParams) {
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
        handler.post(() -> {
            audioManager.adjustStreamVolume(AudioManager.STREAM_NOTIFICATION, AudioManager.ADJUST_MUTE, 0);
            audioManager.adjustStreamVolume(AudioManager.STREAM_ALARM,AudioManager.ADJUST_MUTE,0);
            audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,AudioManager.ADJUST_MUTE,0);
            audioManager.adjustStreamVolume(AudioManager.STREAM_RING,AudioManager.ADJUST_MUTE,0);
            audioManager.adjustStreamVolume(AudioManager.STREAM_SYSTEM,AudioManager.ADJUST_MUTE,0);
        });


        Calendar cal = Calendar.getInstance();
        AlarmManagerJava alarmManagerJava= new AlarmManagerJava(mContext).getInstance();
        long duration = alarmManagerJava.getTime(cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE),1);
        OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(AlarmWorker02.class).setInitialDelay(duration, TimeUnit.MILLISECONDS).addTag(alarmManagerJava.tag02).build();
        WorkManager workManager = WorkManager.getInstance();
        workManager.enqueue(workRequest);
        return Result.success();
    }
}
