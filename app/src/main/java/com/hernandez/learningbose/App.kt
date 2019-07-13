package com.hernandez.learningbose

import android.app.Application
import com.bose.wearable.BoseWearable
import com.bose.wearable.Config

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        BoseWearable.configure(this, Config.Builder().build())
    }
}