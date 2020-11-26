package com.jydev.studyhelper

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class AlarmBR : BroadcastReceiver() {
    private var isFirst = true
    override fun onReceive(p0: Context?, p1: Intent?) {
        if(p1?.action==Intent.ACTION_HEADSET_PLUG){
            if(isFirst) isFirst =false
            else
                AlarmManager.onDestroy()
        }
    }
}