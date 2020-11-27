package com.jydev.studyhelper.java;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmBRJava extends BroadcastReceiver {
    boolean isFirst = true;
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(Intent.ACTION_HEADSET_PLUG)){
            if(isFirst) isFirst = false;
            else new AlarmManagerJava(context).getInstance().onDestroy();
        }
    }
}
