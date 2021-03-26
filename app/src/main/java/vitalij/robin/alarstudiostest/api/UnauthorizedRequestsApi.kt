package vitalij.robin.alarstudiostest.api

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import vitalij.robin.alarstudiostest.model.network.LoginStatusModel
import vitalij.robin.alarstudiostest.model.network.MainResponse

private const val USERNAME_QUERY = "username"
private const val PASSWORD_QUERY = "password"
private const val TEST_PATCH = "test"
private const val AUTH_CGI_PATCH = "auth.cgi"
private const val DATA_CGI_PATCH = "data.cgi"
private const val CODE_QUERY = "code"
private const val PAGE_QUERY = "p"

interface UnauthorizedRequestsApi {

    @GET("/$TEST_PATCH/$AUTH_CGI_PATCH")
    fun login(
        @Query(USERNAME_QUERY) userName: String,
        @Query(PASSWORD_QUERY) password: String
    ): Single<LoginStatusModel>


    @GET("/$TEST_PATCH/$DATA_CGI_PATCH")
    fun getData(
        @Query(CODE_QUERY) code: String,
        @Query(PAGE_QUERY) p: Int
    ): Single<MainResponse>
}