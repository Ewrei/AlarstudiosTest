package vitalij.robin.alarstudiostest.ui.login

import vitalij.robin.alarstudiostest.repository.LoginRepository
import vitalij.robin.alarstudiostest.ui.common.BaseViewModelFactory
import vitalij.robin.alarstudiostest.utils.ResourceProvider
import javax.inject.Inject

class LoginViewModelFactory @Inject constructor(
    private val loginRepository: LoginRepository,
    private val resourceProvider: ResourceProvider
) : BaseViewModelFactory<LoginViewModel>(LoginViewModel::class.java) {

    private var viewModel: LoginViewModel? = null

    override fun createViewModel(): LoginViewModel {
        return viewModel ?: run {
            val model =
                LoginViewModel(
                    loginRepository,
                    resourceProvider
                )
            viewModel = model
            return model
        }
    }
}