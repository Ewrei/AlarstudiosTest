package vitalij.robin.alarstudiostest.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import vitalij.robin.alarstudiostest.R
import vitalij.robin.alarstudiostest.common.extensions.addFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            intent?.let {
                addFragment(
                    R.id.content_main,
                    MainFragment.newInstance(it.getStringExtra(CODE) ?: "")
                )
            }
        }
    }

    companion object {
        const val CODE = "code"

        fun newInstance(context: Context?, code: String?) =
            Intent(context, MainActivity::class.java).apply {
                putExtra(CODE, code)
            }
    }
}