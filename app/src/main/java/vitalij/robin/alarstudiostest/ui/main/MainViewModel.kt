package vitalij.robin.alarstudiostest.ui.main

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import vitalij.robin.alarstudiostest.common.extensions.getError
import vitalij.robin.alarstudiostest.model.enums.Status
import vitalij.robin.alarstudiostest.model.network.MainModel
import vitalij.robin.alarstudiostest.repository.MainRepository
import vitalij.robin.alarstudiostest.ui.common.BaseViewModel
import vitalij.robin.alarstudiostest.utils.ResourceProvider

const val PAGE_SIZE = 10
const val PREFETCH_DISTANCE_SIZE = 5

class MainViewModel(
    private val mainRepository: MainRepository,
    private val resourceProvider: ResourceProvider
) : BaseViewModel() {

    lateinit var liveData: LiveData<PagedList<MainModel>>

    lateinit var openDialog: (title: String) -> Unit

    val isProgressBarVisibility = ObservableBoolean(false)

    private lateinit var myOrdersDataSource: MainDataSource

    fun loadData(code: String) {
        val config = PagedList.Config.Builder()
            .setInitialLoadSizeHint(PAGE_SIZE)
            .setPageSize(PAGE_SIZE)
            .setPrefetchDistance(PREFETCH_DISTANCE_SIZE)
            .setEnablePlaceholders(false)
            .build()

        myOrdersDataSource = MainDataSource(
            mainRepository = mainRepository,
            resourceProvider = resourceProvider,
            setStatus = { status, errorThrowable ->
                when (status) {
                    Status.LOADING -> {
                        progressBarVisibility.postValue(true)
                        errorVisibility.postValue(false)
                    }
                    Status.ERROR -> {
                        progressBarVisibility.postValue(false)
                        errorThrowable?.let {
                            errorModel = it
                        }
                        errorVisibility.postValue(true)
                    }
                    Status.SUCCESS -> {
                        progressBarVisibility.postValue(false)
                    }
                }
            },
            openDialog = openDialog,
            setLoading = {
                isProgressBarVisibility.set(it)
            }, code = code
        )

        liveData = initializedPagedListBuilder(config).build()
    }

    fun refresh() {
        myOrdersDataSource.loadInitial()
    }

    private fun initializedPagedListBuilder(config: PagedList.Config): LivePagedListBuilder<Int, MainModel> {
        return LivePagedListBuilder(object : DataSource.Factory<Int, MainModel>() {
            override fun create() = myOrdersDataSource
        }, config)
    }
}