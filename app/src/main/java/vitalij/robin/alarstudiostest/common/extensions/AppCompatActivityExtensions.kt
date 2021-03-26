package vitalij.robin.alarstudiostest.common.extensions

import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

fun AppCompatActivity.addFragment(@IdRes where: Int, fragment: Fragment) {
    supportFragmentManager
        .beginTransaction()
        .add(where, fragment, fragment.javaClass.simpleName)
        .commit()
}

fun AppCompatActivity.replaceFragment(@IdRes where: Int, fragment: Fragment) {
    supportFragmentManager
        .beginTransaction()
        .replace(where, fragment, fragment.javaClass.simpleName)
        .commit()
}

fun AppCompatActivity.setToolbarTitle(@StringRes titleRes: Int) {
    supportActionBar?.setTitle(titleRes)
}