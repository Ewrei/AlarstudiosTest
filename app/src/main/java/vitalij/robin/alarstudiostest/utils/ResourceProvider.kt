package vitalij.robin.alarstudiostest.utils

import android.content.Context

class ResourceProvider(private val context: Context) {

    fun getString(string: Int) = context.getString(string)

}