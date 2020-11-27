package com.jydev.studyhelper.kotlin

import android.annotation.SuppressLint
import android.content.Context
import android.media.AudioManager
import android.os.Build
import android.os.Handler
import android.os.Looper
import androidx.annotation.RequiresApi
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.jydev.studyhelper.kotlin.AlarmManager.tag02
import java.util.*
import java.util.concurrent.TimeUnit

class AlarmWorker02(val context:Context, workerParameters: WorkerParameters) : Worker(context,workerParameters) {
    @SuppressLint("WrongConstant")
    @RequiresApi(Build.VERSION_CODES.M)
    override fun doWork(): Result {
        val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager

        val handler = Handler(Looper.getMainLooper())
            handler.post {
                audioManager.adjustStreamVolume(AudioManager.STREAM_NOTIFICATION, AudioManager.ADJUST_MUTE, 0)
                audioManager.adjustStreamVolume(AudioManager.STREAM_ALARM,AudioManager.ADJUST_MUTE,0)
                audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,AudioManager.ADJUST_MUTE,0)
                audioManager.adjustStreamVolume(AudioManager.STREAM_RING,AudioManager.ADJUST_MUTE,0)
                audioManager.adjustStreamVolume(AudioManager.STREAM_SYSTEM,AudioManager.ADJUST_MUTE,0)
            }

        val cal = Calendar.getInstance()
        val workRequest = OneTimeWorkRequestBuilder<AlarmWorker02>().setInitialDelay(
            AlarmManager.getTime(cal.get(Calendar.HOUR_OF_DAY),cal.get(Calendar.MINUTE),1), TimeUnit.MILLISECONDS).addTag(tag02).build()
        val workManager = WorkManager.getInstance()
        workManager.enqueue(workRequest)
        return Result.success()
    }

}