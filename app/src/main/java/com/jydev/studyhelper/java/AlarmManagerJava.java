package com.jydev.studyhelper.java;

import android.content.Context;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import com.jydev.studyhelper.R;
import com.jydev.studyhelper.kotlin.SampleApplication;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class AlarmManagerJava {
    private static AlarmManagerJava alarmManagerJava = null;
    private MusicServiceJava mService = null;
    private MediaPlayer mediaPlayer = null;
    private View mView = null;
    private Timer timer = null;
    private Context mContext = null;
    String tag01 = "work01";
    String tag02 = "work02";
    WindowManager windowManager;
    public AlarmManagerJava(Context context){
        mContext = context;
        windowManager = (WindowManager) mContext.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
    }

    public long getTime(int hour,int min, int interval){
        Calendar cal = Calendar.getInstance();
        Calendar nCal = Calendar.getInstance();
        nCal.set(Calendar.HOUR_OF_DAY,hour);
        nCal.set(Calendar.MINUTE,min);
        nCal.set(Calendar.SECOND,0);
        nCal.add(Calendar.DATE,interval);
        return nCal.getTimeInMillis() - cal.getTimeInMillis();
    }

    public void setAlarmService(MusicServiceJava sv){
        mService = sv;
        playMusic();
    }

    public AlarmManagerJava getInstance(){
        if(alarmManagerJava==null)
            alarmManagerJava = new AlarmManagerJava(mContext);
        return alarmManagerJava;
    }

    private void playMusic(){
        mediaPlayer = (MediaPlayer) MediaPlayer.create(mContext, R.raw.alarm);
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                mediaPlayer.start();
            }
        };
        timer = new Timer();
        timer.schedule(timerTask,0,5000);
    }

    public void addView(View view){
        mView = view;
        windowManager.addView(view,buildLayoutParams(0,0));
    }

    public void onDestroy(){
        if(mService == null) return;
        timer.cancel();
        windowManager.removeView(mView);
        mediaPlayer.stop();
        mService.stopSelf();
    }

    private WindowManager.LayoutParams buildLayoutParams(int x, int y){
        int type = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) ? WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY : WindowManager.LayoutParams.TYPE_PHONE;
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT,type,WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, PixelFormat.TRANSPARENT);
        layoutParams.gravity = Gravity.CENTER;
        layoutParams.x = x;
        layoutParams.y = y;
        return layoutParams;
    }

}
