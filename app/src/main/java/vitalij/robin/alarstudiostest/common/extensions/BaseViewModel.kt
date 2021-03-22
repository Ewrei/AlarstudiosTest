package vitalij.robin.alarstudiostest.common.extensions

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import vitalij.robin.alarstudiostest.interfaces.ErrorController
import vitalij.robin.alarstudiostest.interfaces.ProgressBarActivityController
import vitalij.robin.alarstudiostest.interfaces.ProgressBarController
import vitalij.robin.alarstudiostest.ui.common.BaseFragment
import vitalij.robin.alarstudiostest.ui.common.BaseViewModel

fun BaseViewModel.observeToProgressBar(
    lifecycleOwner: LifecycleOwner,
    activity: AppCompatActivity
) {
    activityProgressBarVisibility.observe(lifecycleOwner, { show ->
        (activity as? ProgressBarActivityController)?.showOrHideProgressBar(
            show,
            textActivityVisibility.get() ?: ""
        )
    })
}

fun BaseViewModel.observeToProgressBar(fragment: Fragment) {
    progressBarVisibility.observe(fragment, { show ->
        (fragment as? ProgressBarController)?.showOrHideProgressBar(show)
    })
}

fun BaseViewModel.observeToError(fragment: Fragment) {
    errorVisibility.observe(fragment, {
        if (it) {
            (fragment as? ErrorController)?.setError(errorModel)
        } else if (it == false) {
            (fragment as? ErrorController)?.hideError()
        }
    })
}