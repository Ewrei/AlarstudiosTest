package vitalij.robin.alarstudiostest.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import vitalij.robin.alarstudiostest.utils.ResourceProvider
import javax.inject.Singleton


@Module
@Suppress("unused")
class AlarstudiosAppModule(private val context: Context) {

    @Provides
    @Singleton
    fun context(): Context = context

    @Provides
    @Singleton
    fun resourceProvider(context: Context) = ResourceProvider(context)

}