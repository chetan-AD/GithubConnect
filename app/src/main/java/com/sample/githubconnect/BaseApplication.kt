package com.sample.githubconnect

import android.app.Application
import android.content.Context
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BaseApplication : Application() {
    init {
        context = this
    }
    companion object {
        lateinit var context : Context
    }
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@BaseApplication)
            modules(gitHubModuleInfo)
        }
    }
}