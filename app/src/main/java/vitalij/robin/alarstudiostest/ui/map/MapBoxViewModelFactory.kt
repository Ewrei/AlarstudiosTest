package vitalij.robin.alarstudiostest.ui.map

import vitalij.robin.alarstudiostest.ui.common.BaseViewModelFactory
import javax.inject.Inject

class MapBoxViewModelFactory @Inject constructor() :
    BaseViewModelFactory<MapBoxViewModel>(MapBoxViewModel::class.java) {

    private var viewModel: MapBoxViewModel? = null

    override fun createViewModel(): MapBoxViewModel {
        return viewModel ?: run {
            val model = MapBoxViewModel()
            viewModel = model
            return model
        }
    }
}