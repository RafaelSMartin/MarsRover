package com.rafaels.marsrover

import android.app.Application
import com.rafaels.marsrover.di.dataModule
import com.rafaels.marsrover.di.domainModule
import com.rafaels.marsrover.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MarsRoverApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MarsRoverApplication)
            modules(
                viewModelModule,
                domainModule,
                dataModule
            )
        }
    }
}