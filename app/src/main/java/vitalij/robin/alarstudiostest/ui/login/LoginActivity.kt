package vitalij.robin.alarstudiostest.ui.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_base.*
import vitalij.robin.alarstudiostest.R
import vitalij.robin.alarstudiostest.common.extensions.addFragment
import vitalij.robin.alarstudiostest.common.extensions.setVisibility
import vitalij.robin.alarstudiostest.interfaces.ProgressBarActivityController

class LoginActivity : AppCompatActivity(), ProgressBarActivityController {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        addFragment(R.id.container, LoginFragment.newInstance())
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStackImmediate()
        } else {
            finish()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun showOrHideProgressBar(show: Boolean, title: String) {
        loadingContainer.setVisibility(show)
    }
}