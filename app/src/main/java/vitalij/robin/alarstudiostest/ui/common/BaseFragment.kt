package vitalij.robin.alarstudiostest.ui.common

import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.progress_view.*
import kotlinx.android.synthetic.main.view_error.*
import vitalij.robin.alarstudiostest.common.extensions.setVisibility
import vitalij.robin.alarstudiostest.interfaces.ErrorController
import vitalij.robin.alarstudiostest.interfaces.ProgressBarController
import vitalij.robin.alarstudiostest.model.ErrorModel

abstract class BaseFragment : Fragment(), ProgressBarController, ErrorController {

    override fun showOrHideProgressBar(show: Boolean) {
        loading_container.setVisibility(show)
    }

    override fun setError(
        errorModel: ErrorModel
    ) {
        context?.let { context ->
            errorView.setVisibility(true)
            errorText.text = context.getString(
                errorModel.textResourceId
            )

            errorImage.setImageDrawable(
                ContextCompat.getDrawable(
                    context,
                    errorModel.imageResourceId
                )
            )
            errorModel.descriptionResourceId?.let {
                errorDescription.setVisibility(true)
                errorDescription.setText(errorModel.descriptionResourceId)
            }

            errorResolveButton.setVisibility(errorModel.isButtonVisible)
        }
    }

    fun setErrorResolveButtonClick(onClick: () -> Unit) {
        errorResolveButton.setOnClickListener {
            onClick()
            hideError()
        }
    }

    override fun hideError() {
        errorView?.setVisibility(false)
    }
}