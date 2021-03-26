package vitalij.robin.alarstudiostest.common.extensions

import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

fun Fragment.setToolbarTitle(@StringRes titleRes: Int) {
    (activity as AppCompatActivity).setToolbarTitle(titleRes)
}

fun Fragment.replaceFragment(@IdRes where: Int, fragment: Fragment) {
    (activity as AppCompatActivity).replaceFragment(where, fragment)
}