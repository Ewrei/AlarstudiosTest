package vitalij.robin.alarstudiostest.ui.map

import vitalij.robin.alarstudiostest.model.network.MainModel
import vitalij.robin.alarstudiostest.ui.common.BaseViewModel
import javax.inject.Inject

class MapBoxViewModel @Inject constructor(
) : BaseViewModel() {

    var mainModel: MainModel? = null

}