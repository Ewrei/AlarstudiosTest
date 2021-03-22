package vitalij.robin.alarstudiostest.di.component

import dagger.Component
import vitalij.robin.alarstudiostest.MainActivity
import vitalij.robin.alarstudiostest.di.module.AlarstudiosAppModule
import vitalij.robin.alarstudiostest.di.module.NetworkModule
import vitalij.robin.alarstudiostest.ui.login.LoginFragment
import javax.inject.Singleton

@Singleton
@Component(modules = [AlarstudiosAppModule::class, NetworkModule::class])
interface AppComponent {

    fun inject(activity: MainActivity)

    fun inject(fragment: LoginFragment)
}