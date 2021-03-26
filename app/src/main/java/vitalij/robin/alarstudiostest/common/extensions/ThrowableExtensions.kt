package vitalij.robin.alarstudiostest.common.extensions

import retrofit2.HttpException
import vitalij.robin.alarstudiostest.R
import vitalij.robin.alarstudiostest.utils.ResourceProvider
import java.net.UnknownHostException
import vitalij.robin.alarstudiostest.model.ErrorModel as ErrorModel

fun Throwable?.errorMessage(resourceProvider: ResourceProvider): String {
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

fun Throwable?.getError(): ErrorModel {
    return when (this) {
        is UnknownHostException -> {
            ErrorModel(
                R.string.network_description_error,
                R.drawable.ic_wifi,
                null
            )
        }
        is HttpException -> {
            ErrorModel(
                R.string.server_error,
                R.drawable.ic_unknowwn_error,
                null
            )
        }
        is IllegalArgumentException -> {
            ErrorModel(
                R.string.unknown_error,
                R.drawable.ic_unknowwn_error,
                null
            )
        }
        else -> {
            ErrorModel(
                R.string.unknown_error,
                R.drawable.ic_unknowwn_error,
                null
            )
        }
    }
}