package vitalij.robin.alarstudiostest.ui.main

import androidx.paging.PageKeyedDataSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import vitalij.robin.alarstudiostest.model.enums.Status
import vitalij.robin.alarstudiostest.model.network.MainModel
import vitalij.robin.alarstudiostest.repository.MainRepository

class MainDataSource constructor(
    private val mainRepository: MainRepository,
    private val setStatus: (status: Status, throwable: Throwable?) -> Unit,
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
            .subscribe({ list ->
                var position = 1
                list.forEach {
                    it.position = position
                    position++
                }

                if (list.isNotEmpty()) {
                    setStatus(Status.SUCCESS, null)
                } else {
                    setStatus(Status.EMPTY, null)
                }

                callback.onResult(
                    list,
                    null,
                    2
                )
            }, {
                setStatus(Status.ERROR, it)
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
            .subscribe({ list ->
                var position = 1
                list.forEach {
                    it.position = position
                    position++
                }

                if (list.isNotEmpty()) {
                    setStatus(Status.SUCCESS, null)
                } else {
                    setStatus(Status.EMPTY, null)
                }

                callback.onResult(
                    list,
                    null,
                    2
                )

            }, {
                setStatus(Status.ERROR, it)
            })
            .let(disposables::add)
    }


    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, MainModel>) {
        if (params.key != 0) {
            setLoading(true)
        }
        mainRepository.getMain(code, params.key)
            .subscribe({ list ->
                var position = PAGE_SIZE * (params.key - 1)
                list.forEach {
                    position++
                    it.position = position
                }

                setLoading(false)
                callback.onResult(list, if (list.size == PAGE_SIZE) params.key + 1 else null)
            }, {
                setLoading(false)
            })
            .let(disposables::add)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, MainModel>) {
        mainRepository.getMain(code, params.key)
            .subscribe({ list ->
                var position = PAGE_SIZE * params.key
                list.forEach {
                    position--
                    it.position = position
                }

                callback.onResult(list, params.key - 1)

            }, {
                //TODO Add error handling
            })
            .let(disposables::add)
    }

    override fun invalidate() {
        super.invalidate()
        disposables.dispose()
    }
}