package vitalij.robin.alarstudiostest.ui.main

import androidx.paging.PageKeyedDataSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import vitalij.robin.alarstudiostest.R
import vitalij.robin.alarstudiostest.common.extensions.errorMessage
import vitalij.robin.alarstudiostest.common.extensions.getError
import vitalij.robin.alarstudiostest.model.ErrorModel
import vitalij.robin.alarstudiostest.model.enums.ServerResponseStatus
import vitalij.robin.alarstudiostest.model.enums.Status
import vitalij.robin.alarstudiostest.model.network.MainModel
import vitalij.robin.alarstudiostest.repository.MainRepository
import vitalij.robin.alarstudiostest.utils.ResourceProvider

class MainDataSource constructor(
    private val mainRepository: MainRepository,
    private val resourceProvider: ResourceProvider,
    private val setStatus: (status: Status, errorModel: ErrorModel?) -> Unit,
    private val openDialog: (title: String) -> Unit,
    private val setLoading: (isLoading: Boolean) -> Unit,
    private val code: String
) : PageKeyedDataSource<Int, MainModel>() {

    private val disposables = CompositeDisposable()

    lateinit var params: LoadInitialParams<Int>
    lateinit var callback: LoadInitialCallback<Int, MainModel>

    fun loadInitial() {
        setStatus(Status.LOADING, null)

        mainRepository.getMain(code, 1)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ mainResponse ->
                if (mainResponse.status == ServerResponseStatus.OK.id) {
                    var position = 1
                    mainResponse.data?.forEach {
                        it.position = position
                        position++
                    }

                    setStatus(Status.SUCCESS, null)
                    callback.onResult(
                        mainResponse.data ?: arrayListOf(),
                        null,
                        2
                    )
                } else {
                    setStatus(
                        Status.ERROR, ErrorModel(
                            R.string.unknown_error,
                            R.drawable.ic_unknowwn_error,
                            mainResponse.status
                        )
                    )
                }
            }, {
                setStatus(Status.ERROR, it.getError())
            })
            .let(disposables::add)
    }

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, MainModel>
    ) {
        this.params = params
        this.callback = callback
        setStatus(Status.LOADING, null)

        mainRepository.getMain(code, 1)
            .subscribe({ mainResponse ->
                if (mainResponse.status == ServerResponseStatus.OK.id) {
                    var position = 1
                    mainResponse.data?.forEach {
                        it.position = position
                        position++
                    }

                    setStatus(Status.SUCCESS, null)

                    callback.onResult(
                        mainResponse.data ?: arrayListOf(),
                        null,
                        2
                    )
                } else {
                    setStatus(
                        Status.ERROR, ErrorModel(
                            R.string.unknown_error,
                            R.drawable.ic_unknowwn_error,
                            mainResponse.status
                        )
                    )
                }

            }, {
                setStatus(Status.ERROR, it.getError())
            })
            .let(disposables::add)
    }


    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, MainModel>) {
        if (params.key != 0) {
            setLoading(true)
        }
        mainRepository.getMain(code, params.key)
            .subscribe({ mainResponse ->
                var position = PAGE_SIZE * (params.key - 1)
                mainResponse.data?.forEach {
                    position++
                    it.position = position
                }

                setLoading(false)
                callback.onResult(
                    mainResponse.data ?: arrayListOf(),
                    if (mainResponse.data?.size == PAGE_SIZE) params.key + 1 else null
                )
            }, {
                openDialog(it.errorMessage(resourceProvider))
                setLoading(false)
            })
            .let(disposables::add)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, MainModel>) {
        mainRepository.getMain(code, params.key)
            .subscribe({ mainResponse ->
                var position = PAGE_SIZE * params.key
                mainResponse.data?.forEach {
                    position--
                    it.position = position
                }

                callback.onResult(mainResponse.data ?: arrayListOf(), params.key - 1)

            }, {
                openDialog(it.errorMessage(resourceProvider))
                setLoading(false)
            })
            .let(disposables::add)
    }

    override fun invalidate() {
        super.invalidate()
        disposables.dispose()
    }
}