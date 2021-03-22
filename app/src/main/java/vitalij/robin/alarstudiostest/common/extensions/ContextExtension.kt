package vitalij.robin.alarstudiostest.common.extensions

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder


fun Context.showDialog(message: String?) {
    MaterialAlertDialogBuilder(this).setMessage(message)
        .setPositiveButton(android.R.string.ok) { dialog, _ -> dialog.dismiss() }.create()
        .show()
}

fun Context?.closeKeyboard(view: View?) {
    val imm: InputMethodManager =
        this?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view?.windowToken, 0)
}