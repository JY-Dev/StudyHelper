package com.jydev.studyhelper

import android.Manifest
import android.app.NotificationManager
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import com.jydev.studyhelper.AlarmManager.getTime
import com.jydev.studyhelper.AlarmManager.tag01
import com.jydev.studyhelper.AlarmManager.tag02
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn.setOnClickListener {
            permission {
                val cal = Calendar.getInstance()
                val curHour = cal.get(Calendar.HOUR_OF_DAY)
                val curMin = cal.get(Calendar.MINUTE)
                TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { _, hour, min ->
                    if(hour<=curHour&&min<=curMin) setAlarm(hour,min,1,tag01)
                    else setAlarm(hour,min,0,tag01)
                },curHour,curMin,false).show()
            }
        }
        btn02.setOnClickListener {
            permission {
                val cal = Calendar.getInstance()
                val curHour = cal.get(Calendar.HOUR_OF_DAY)
                val curMin = cal.get(Calendar.MINUTE)
                TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { _, hour, min ->
                    if(hour<=curHour&&min<=curMin) {
                        setAlarm(hour,min,1,tag02)
                    }
                    else setAlarm(hour,min,0,tag02)
                },curHour,curMin,false).show()
            }
        }

        btn03.setOnClickListener {
            WorkManager.getInstance().cancelAllWork()
        }

    }

    private fun setAlarm(hour:Int, minute:Int, interval : Int, tag:String){
            Toast.makeText(this,"알림이 등록되었습니다.",Toast.LENGTH_SHORT).show()
            val duration = getTime(hour,minute,interval)
            val workRequest =  when(tag){
                tag01 -> OneTimeWorkRequestBuilder<AlarmWorker01>().setInitialDelay(duration,TimeUnit.MILLISECONDS).addTag(tag).build()
                else -> OneTimeWorkRequestBuilder<AlarmWorker02>().setInitialDelay(duration,TimeUnit.MILLISECONDS).addTag(tag).build()
            }
            val workManager = WorkManager.getInstance()
            workManager.apply {
                cancelAllWorkByTag(tag)
                enqueue(workRequest)
            }
    }

    private fun permission(func : () -> Unit){
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        TedPermission.with(this)
                .setPermissions(Manifest.permission.SYSTEM_ALERT_WINDOW)
                .setPermissionListener(object : PermissionListener {
                    @RequiresApi(Build.VERSION_CODES.O)
                    override fun onPermissionGranted() {
                        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !notificationManager.isNotificationPolicyAccessGranted){
                            startActivity(Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
                        } else {
                            func()
                        }
                    }
                    override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {

                    }
                }).check()
    }
}