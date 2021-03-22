package vitalij.robin.alarstudiostest.api

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import vitalij.robin.alarstudiostest.model.LoginStatusModel

private const val USERNAME_QUERY = "username"
private const val PASSWORD_QUERY = "password"
private const val TEST_PATCH = "test"
private const val AUTH_CGI_PATCH = "auth.cgi"

interface UnauthorizedRequestsApi {

    @GET("/$TEST_PATCH/$AUTH_CGI_PATCH")
    fun login(
        @Query(USERNAME_QUERY) userName: String,
        @Query(PASSWORD_QUERY) password: String
    ): Single<LoginStatusModel>

}