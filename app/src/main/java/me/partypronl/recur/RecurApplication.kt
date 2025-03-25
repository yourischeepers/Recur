package me.partypronl.recur

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.component.KoinComponent
import org.koin.core.context.startKoin

class RecurApplication : Application(), KoinComponent {

    override fun onCreate() {
        super.onCreate()
        initializeKoin()
    }

    private fun initializeKoin() {
        startKoin {
            androidContext(this@RecurApplication)
            modules(KoinApplicationModule().module)
        }
    }
}
