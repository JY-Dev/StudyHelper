package com.jydev.studyhelper

import android.app.Application

class SampleApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: SampleApplication private set
    }
}