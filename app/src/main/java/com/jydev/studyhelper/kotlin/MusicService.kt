package com.jydev.studyhelper.kotlin

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.jydev.studyhelper.R

class MusicService : Service() {
    lateinit var mediaPlayer: MediaPlayer
    var alarmBR: AlarmBR? = null
    lateinit var view : View
    val filter = IntentFilter(Intent.ACTION_HEADSET_PLUG)
    override fun onBind(p0: Intent?): IBinder? = null
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        alarmBR = AlarmBR()
        registerReceiver(alarmBR,filter)
        AlarmManager.setAlarmService(this)
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(alarmBR)
    }



    override fun onCreate() {
        super.onCreate()
        val layoutInflater = LayoutInflater.from(this)
        val view = layoutInflater.inflate(R.layout.alarm_dialog,null,false)
        view.findViewById<Button>(R.id.test).setOnClickListener {
            AlarmManager.onDestroy()
        }
        AlarmManager.addView(view)

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                "alarmworker",
                "test",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(notificationChannel)
        }
        val notification =
            NotificationCompat.Builder(this, "alarmworker")
                .setContentTitle("StudyHelper앱이 실행중입니다.")
                .setSmallIcon(R.mipmap.ic_launcher).build()
        startForeground(1, notification)
    }
}