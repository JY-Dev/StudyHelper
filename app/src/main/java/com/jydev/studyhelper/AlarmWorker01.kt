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
import androidx.annotation.RequiresApi
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.util.*
import java.util.concurrent.TimeUnit

class AlarmWorker01(val context: Context, workerParameters: WorkerParameters) :
    Worker(context, workerParameters) {
    @SuppressLint("WrongConstant")
    @RequiresApi(Build.VERSION_CODES.M)
    override fun doWork(): Result {
        val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        val handler = Handler(Looper.getMainLooper())
        audioManager.getDevices(AudioManager.GET_DEVICES_ALL).forEach {
            when (it.type) {
                AudioDeviceInfo.TYPE_WIRED_HEADSET, AudioDeviceInfo.TYPE_WIRED_HEADPHONES, AudioDeviceInfo.TYPE_USB_HEADSET , AudioDeviceInfo.TYPE_BLUETOOTH_A2DP,AudioDeviceInfo.TYPE_BLUETOOTH_SCO -> {
                    handler.post {
                        audioManager.setStreamVolume(
                            AudioManager.STREAM_MUSIC,
                            10,
                            AudioManager.FLAG_PLAY_SOUND
                        )
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            context.startForegroundService(
                                Intent(
                                    context,
                                    MusicService::class.java
                                )
                            )
                        }
                    }
                }
            }

        }
        val cal = Calendar.getInstance()
        val workRequest = OneTimeWorkRequestBuilder<AlarmWorker02>().setInitialDelay(AlarmManager.getTime(cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE),1), TimeUnit.MILLISECONDS).build()
        val workManager = WorkManager.getInstance()
        workManager.enqueue(workRequest)
        return Result.success()
    }

}