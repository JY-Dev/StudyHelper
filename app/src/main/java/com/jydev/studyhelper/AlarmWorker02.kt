package com.jydev.studyhelper

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.media.AudioDeviceInfo
import android.media.AudioManager
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.util.*
import java.util.concurrent.TimeUnit

class AlarmWorker02(val context:Context, workerParameters: WorkerParameters) : Worker(context,workerParameters) {
    @SuppressLint("WrongConstant")
    @RequiresApi(Build.VERSION_CODES.M)
    override fun doWork(): Result {
        val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager

        val handler = Handler(Looper.getMainLooper())
            handler.post {
                Toast.makeText(context,"TEst",Toast.LENGTH_SHORT).show()
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, AudioManager.FLAG_PLAY_SOUND)
                audioManager.setStreamVolume(AudioManager.STREAM_RING,0,AudioManager.FLAG_PLAY_SOUND)
                audioManager.setStreamVolume(AudioManager.STREAM_SYSTEM,0,AudioManager.FLAG_PLAY_SOUND)
            }

        val cal = Calendar.getInstance()
        val workRequest = OneTimeWorkRequestBuilder<AlarmWorker02>().setInitialDelay(AlarmManager.getTime(cal.get(Calendar.HOUR_OF_DAY),cal.get(Calendar.MINUTE),1), TimeUnit.MILLISECONDS).build()
        val workManager = WorkManager.getInstance()
        workManager.enqueue(workRequest)
        return Result.success()
    }

}