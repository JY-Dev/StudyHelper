package com.jydev.studyhelper

import android.content.Context
import android.graphics.PixelFormat
import android.media.MediaPlayer
import android.os.Build
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import java.util.*
import kotlin.concurrent.timer

object AlarmManager {
    var mService: MusicService? = null
    lateinit var mediaPlayer: MediaPlayer
    lateinit var mView :View
    lateinit var timer : Timer
    val tag01 = "work01"
    val tag02 = "work02"
    private val windowManager: WindowManager by lazy {
        SampleApplication.instance.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    }

    fun getTime(hour:Int,min:Int,interval : Int) : Long{
        val cal = Calendar.getInstance()
        val nCal = Calendar.getInstance()
        nCal.set(Calendar.HOUR_OF_DAY,hour)
        nCal.set(Calendar.MINUTE,min)
        if(cal.get(Calendar.DAY_OF_WEEK)==7) interval + 1
        nCal.add(Calendar.DATE,interval)
        return nCal.timeInMillis - cal.timeInMillis
    }

    fun setAlarmService(sv: MusicService){
        mService = sv
        playMusic()
    }

    fun onDestroy() {
        if (mService == null) return
        timer.cancel()
        windowManager.removeView(mView)
        mediaPlayer.stop()
        mService?.stopSelf()
    }

    private fun playMusic(){
        mediaPlayer = MediaPlayer.create(mService, R.raw.alarm) as MediaPlayer
        timer = timer(period = 5000){
            mediaPlayer.start()
        }
    }

    fun addView(view: View){
        mView = view
        windowManager.addView(view, buildLayoutParams(0,0))
    }


    private fun buildLayoutParams(
        x: Int,
        y: Int
    ): WindowManager.LayoutParams {
        val type = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else {
            WindowManager.LayoutParams.TYPE_PHONE
        }
        return WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            type,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSPARENT
        ).apply {
            gravity = Gravity.CENTER
            this.x = x
            this.y = y
        }
    }
}