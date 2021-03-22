package vitalij.robin.alarstudiostest

import android.app.Application
import vitalij.robin.alarstudiostest.di.component.AppComponent
import vitalij.robin.alarstudiostest.di.component.DaggerAppComponent
import vitalij.robin.alarstudiostest.di.module.AlarstudiosAppModule

class AlarStudiosApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        appComponent = getComponent()
    }

    private fun getComponent(): AppComponent {
        return DaggerAppComponent
            .builder()
            .alarstudiosAppModule(AlarstudiosAppModule(applicationContext))
            .build()
    }

    companion object {

        lateinit var appComponent: AppComponent

    }
}