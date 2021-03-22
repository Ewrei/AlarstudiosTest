package vitalij.robin.alarstudiostest.common.extensions

import retrofit2.HttpException
import vitalij.robin.alarstudiostest.R
import vitalij.robin.alarstudiostest.utils.ResourceProvider
import java.net.UnknownHostException

fun Throwable?.errorLoginMessage(resourceProvider: ResourceProvider): String {
    return when (this) {
        is UnknownHostException -> {
            resourceProvider.getString(R.string.network_description_error)
        }
        is HttpException -> {
            resourceProvider.getString(R.string.server_error)
        }
        is IllegalArgumentException -> {
            resourceProvider.getString(R.string.unknown_error)
        }
        else -> {
            resourceProvider.getString(R.string.unknown_error)
        }
    }
}