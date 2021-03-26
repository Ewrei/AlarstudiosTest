package vitalij.robin.alarstudiostest.repository

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import vitalij.robin.alarstudiostest.api.UnauthorizedRequestsApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainRepository @Inject constructor(
    private val authorizedRequestsApi: UnauthorizedRequestsApi
) {

    fun getMain(code: String, page: Int) = authorizedRequestsApi.getData(code, page)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread()).flatMap {
            it.data.forEach {
                it.imageUrl = "http://api.worldoftanks.ru/static/2.66.0/wot/encyclopedia/achievement/big/crucialShotMedal.png"
            }
            return@flatMap Single.just(it.data)
        }
}