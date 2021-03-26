package vitalij.robin.alarstudiostest.common.bindings

import android.content.Context
import android.util.TypedValue
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import vitalij.robin.alarstudiostest.R

object ImageViewBinging {

    @JvmStatic
    @BindingAdapter("imageUrl")
    fun ImageView.loadImage(imageUrl: String?) {
        Glide.with(context)
            .load(imageUrl)
            .apply(
                RequestOptions()
                    .placeholder(getCircularProgressDrawable(context))
                    .error(getErrorDrawable())
            )
            .into(this)
    }

    private fun getCircularProgressDrawable(context: Context): CircularProgressDrawable {
        val circularProgressDrawable = CircularProgressDrawable(context)
        circularProgressDrawable.strokeWidth = getFloat(R.dimen.circular_stroke_width, context)
        circularProgressDrawable.centerRadius = getFloat(R.dimen.circular_center_radius, context)
        circularProgressDrawable.start()
        return circularProgressDrawable
    }

    private fun getFloat(res: Int, context: Context): Float {
        val outValue = TypedValue()
        context.resources.getValue(res, outValue, true)
        return outValue.float
    }

    private fun getErrorDrawable() = R.drawable.img_placeholder_error

}