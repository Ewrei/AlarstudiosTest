package vitalij.robin.alarstudiostest.common.extensions

import android.view.View

fun View.setVisibility(isVisible: Boolean?) {
    visibility = if (isVisible == true) View.VISIBLE else View.GONE
}