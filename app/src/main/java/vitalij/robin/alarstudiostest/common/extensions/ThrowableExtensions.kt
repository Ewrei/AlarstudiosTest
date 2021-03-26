package vitalij.robin.alarstudiostest.common.extensions

import retrofit2.HttpException
import vitalij.robin.alarstudiostest.R
import vitalij.robin.alarstudiostest.utils.ResourceProvider
import java.net.UnknownHostException
import vitalij.robin.alarstudiostest.model.ErrorModel as ErrorModel1

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

fun Throwable?.getError(resourceProvider: ResourceProvider): ErrorModel1 {
    return when (this) {
        is UnknownHostException -> {
            ErrorModel1(
                R.string.network_description_error,
                R.drawable.ic_wifi,
                null,
                true
            )
        }
        is HttpException -> {
            ErrorModel1(
                R.string.server_error,
                R.drawable.ic_wifi,
                null,
                true
            )
        }
        is IllegalArgumentException -> {
            // resourceProvider.getString(R.string.unknown_error)
            ErrorModel1(
                R.string.server_error,
                R.drawable.ic_wifi,
                null,
                true
            )
        }
        else -> {
            // resourceProvider.getString(R.string.unknown_error)
            ErrorModel1(
                R.string.server_error,
                R.drawable.ic_wifi,
                null,
                true
            )
        }
    }
}