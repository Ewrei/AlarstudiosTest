package vitalij.robin.alarstudiostest.ui.map

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import vitalij.robin.alarstudiostest.R
import vitalij.robin.alarstudiostest.common.extensions.addFragment
import vitalij.robin.alarstudiostest.model.network.MainModel


class MapBoxActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setWindow()
        setContentView(R.layout.activity_base)

        if (savedInstanceState == null) {
            intent?.let {
                addFragment(
                    R.id.container,
                    MapBoxFragment.newInstance(it.getSerializableExtra(MAIN_MODEL) as MainModel)
                )
            }
        }
    }

    //TODO Deprecated Replace in next version
    private fun setWindow() {
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        window.statusBarColor = Color.TRANSPARENT
    }

    companion object {
        const val MAIN_MODEL = "main_model"

        fun newInstance(context: Context?, mainModel: MainModel) =
            Intent(context, MapBoxActivity::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).apply {
                    putExtra(MAIN_MODEL, mainModel)
                }
    }
}