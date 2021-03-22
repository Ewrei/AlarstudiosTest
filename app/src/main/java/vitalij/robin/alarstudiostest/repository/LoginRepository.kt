package vitalij.robin.alarstudiostest.repository

import vitalij.robin.alarstudiostest.api.UnauthorizedRequestsApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginRepository @Inject constructor(private val unauthorizedRequestsApi: UnauthorizedRequestsApi) {

    fun login(userName: String, password: String) = unauthorizedRequestsApi.login(userName, password)

}