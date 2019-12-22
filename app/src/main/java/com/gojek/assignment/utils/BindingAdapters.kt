package com.gojek.assignment.utils

import android.graphics.Color
import android.graphics.PorterDuff
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.request.RequestOptions
import com.gojek.assignment.R
import com.gojek.assignment.api.helper.ErrorHandler
import com.gojek.assignment.api.helper.Result
import com.gojek.assignment.utils.glide.GlideApp

object BindingAdapters {
    @JvmStatic
    @BindingAdapter("visibleIf")
    fun setVisibleIf(view: View, shouldBeVisible: Boolean?) {
        if (shouldBeVisible.orFalse()) {
            view.show()
        } else {
            view.hide()
        }
    }

    /**
     * binding adapter for show API error.
     */
    @JvmStatic
    @BindingAdapter(value = ["result", "errorHandler"], requireAll = true)
    fun handleErrors(view: View, result: Result<*>?, errorHandler: ErrorHandler?) {
        result?.let {
            if (result.state == Result.State.ERROR) {
                errorHandler?.onError(result)
            }
        }
    }

    /**
     * Loads the image into ImageView from the URL
     */
    @JvmStatic
    @BindingAdapter("circleImageUrl")
    fun setCircleImage(imageView: ImageView, url: String?) {
        GlideApp.with(imageView)
            .load(url)
            .apply(RequestOptions().circleCrop())
            .placeholder(R.drawable.bg_placeholder).into(imageView)

    }

    /**
     * Applies the custom tint to imageview
     */
    @JvmStatic
    @BindingAdapter("colorFilter")
    fun colorFilter(imageView: ImageView, color: String?) {
        color?.let {
            imageView.setColorFilter(Color.parseColor(it), PorterDuff.Mode.SRC_ATOP)
        }
    }


}