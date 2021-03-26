package vitalij.robin.alarstudiostest.ui.main

import vitalij.robin.alarstudiostest.repository.MainRepository
import vitalij.robin.alarstudiostest.ui.common.BaseViewModelFactory
import vitalij.robin.alarstudiostest.utils.ResourceProvider
import javax.inject.Inject

class MainViewModelFactory @Inject constructor(
    private val mainRepository: MainRepository,
    private val resourceProvider: ResourceProvider
) : BaseViewModelFactory<MainViewModel>(
    MainViewModel::class.java
) {

    private var viewModel: MainViewModel? = null

    override fun createViewModel(): MainViewModel {
        return viewModel ?: run {
            val model = MainViewModel(
                mainRepository,
                resourceProvider
            )
            viewModel = model
            return model
        }
    }
}