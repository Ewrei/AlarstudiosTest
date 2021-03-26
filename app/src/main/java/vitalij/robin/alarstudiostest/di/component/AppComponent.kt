package vitalij.robin.alarstudiostest.di.component

import dagger.Component
import vitalij.robin.alarstudiostest.di.module.AlarstudiosAppModule
import vitalij.robin.alarstudiostest.di.module.NetworkModule
import vitalij.robin.alarstudiostest.ui.login.LoginFragment
import vitalij.robin.alarstudiostest.ui.main.MainFragment
import vitalij.robin.alarstudiostest.ui.map.MapBoxFragment
import javax.inject.Singleton

@Singleton
@Component(modules = [AlarstudiosAppModule::class, NetworkModule::class])
interface AppComponent {

    fun inject(fragment: LoginFragment)
    fun inject(fragment: MainFragment)
    fun inject(fragment: MapBoxFragment)
}