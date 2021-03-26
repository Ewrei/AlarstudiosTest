package vitalij.robin.alarstudiostest

import android.app.Application
import com.mapbox.mapboxsdk.Mapbox
import vitalij.robin.alarstudiostest.di.component.AppComponent
import vitalij.robin.alarstudiostest.di.component.DaggerAppComponent
import vitalij.robin.alarstudiostest.di.module.AlarstudiosAppModule

class AlarStudiosApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        appComponent = getComponent()
        initMapBox()
    }

    private fun getComponent(): AppComponent {
        return DaggerAppComponent
            .builder()
            .alarstudiosAppModule(AlarstudiosAppModule(applicationContext))
            .build()
    }

    private fun initMapBox() {
        Mapbox.getInstance(
            applicationContext,
            BuildConfig.MAPBOX_KEY
        )
    }

    companion object {

        lateinit var appComponent: AppComponent

    }
}