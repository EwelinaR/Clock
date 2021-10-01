package com.example.clock

import android.app.Application
import com.example.clock.di.AppComponent
import com.example.clock.di.DaggerAppComponent

class App : Application() {
    val appComponent: AppComponent = DaggerAppComponent.create()
}
