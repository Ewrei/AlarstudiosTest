package vitalij.robin.alarstudiostest.ui.login

import androidx.databinding.ObservableField
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import vitalij.robin.alarstudiostest.R
import vitalij.robin.alarstudiostest.common.extensions.errorMessage
import vitalij.robin.alarstudiostest.model.enums.ServerResponseStatus
import vitalij.robin.alarstudiostest.repository.LoginRepository
import vitalij.robin.alarstudiostest.ui.common.BaseViewModel
import vitalij.robin.alarstudiostest.utils.ResourceProvider

class LoginViewModel(
    private val loginRepository: LoginRepository,
    private val resourceProvider: ResourceProvider
) : BaseViewModel() {

    lateinit var openDialog: (message: String) -> Unit
    lateinit var openMainScreen: (code: String?) -> Unit

    val login = ObservableField("")
    val password = ObservableField("")

    fun login() {
        loginRepository.login(login.get()!!, password.get()!!)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .let(::setupActivityProgressShow)
            .subscribe({ loginStatusModel ->
                if (loginStatusModel.status == ServerResponseStatus.ERROR.id) {
                    openDialog(resourceProvider.getString(R.string.incorrect_username_or_password_entered))
                } else if (loginStatusModel.status == ServerResponseStatus.OK.id) {
                    openMainScreen(loginStatusModel.code)
                }
            }, {
                openDialog(
                    it.errorMessage(resourceProvider)
                )
            })
            .let(disposables::add)
    }
}