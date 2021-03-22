package vitalij.robin.alarstudiostest.ui.common

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import vitalij.robin.alarstudiostest.model.ErrorModel

open class BaseViewModel : ViewModel() {

    val disposables = CompositeDisposable()

    val progressBarVisibility = MutableLiveData<Boolean>()
    val activityProgressBarVisibility = MutableLiveData<Boolean>()

    val errorVisibility = MutableLiveData<Boolean>()

    val textActivityVisibility = ObservableField<String>()

    val showSwipeRefreshLayout = MutableLiveData<Boolean>()

    lateinit var errorModel: ErrorModel

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }

    protected val error: (t: Throwable) -> Unit = { error ->
        handleError(error)
    }

    protected fun <T> setupProgressActivityShow(observable: Observable<T>): Observable<T> {
        return observable.doOnSubscribe { progressBarVisibility.value = true }
            .doOnNext { progressBarVisibility.value = false }
            .doOnComplete { progressBarVisibility.value = false }
            .doOnError { progressBarVisibility.value = false }
            .doOnDispose { progressBarVisibility.value = false }
    }

    protected fun <T> setupProgressActivityShow(observable: Single<T>): Single<T> {
        return observable.doOnSubscribe { activityProgressBarVisibility.value = true }
            .doOnSuccess { activityProgressBarVisibility.value = false }
            .doOnError { activityProgressBarVisibility.value = false }
            .doOnDispose { activityProgressBarVisibility.value = false }
    }

    protected fun <T> setupProgressShow(observable: Observable<T>): Observable<T> {
        return observable.doOnSubscribe { showProgressBar() }
            .doOnNext { hideProgressBar() }
            .doOnComplete { hideProgressBar() }
            .doOnError { hideProgressBar() }
            .doOnDispose { hideProgressBar() }
    }

    protected fun <T> setupProgressShow(single: Single<T>): Single<T> {
        return single.doOnSubscribe { showProgressBar() }
            .doOnSuccess { hideProgressBar() }
            .doOnError { hideProgressBar() }
            .doOnDispose { hideProgressBar() }
    }

    protected fun <T> setupSwipeRefresh(single: Single<T>): Single<T> {
        return single.doOnSubscribe { showSwipeRefresh() }
            .doOnSuccess {
                hideSwipeRefresh()
                errorVisibility.value = false
            }
            .doOnError {
                hideSwipeRefresh()
                errorVisibility.value = false
            }
            .doOnDispose {
                hideSwipeRefresh()
                errorVisibility.value = false
            }
    }

    protected fun <T> setupActivityProgressShow(single: Single<T>): Single<T> {
        return single.doOnSubscribe { activityProgressBarVisibility.value = true }
            .doOnSuccess { activityProgressBarVisibility.value = false }
            .doOnError { activityProgressBarVisibility.value = false }
            .doOnDispose { activityProgressBarVisibility.value = false }
    }

    private fun hideProgressBar() {
        progressBarVisibility.value = false
    }

    private fun showProgressBar() {
        progressBarVisibility.value = true
        errorVisibility.value = false
    }

    private fun handleError(error: Throwable) {
        hideProgressBar()
        //errorModel = error.getErrorModel()
        errorVisibility.value = true
    }

    private fun hideSwipeRefresh() {
        showSwipeRefreshLayout.value = false
    }

    private fun showSwipeRefresh() {
        showSwipeRefreshLayout.value = true
    }
}