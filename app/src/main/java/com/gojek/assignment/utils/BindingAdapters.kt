package com.gojek.assignment.utils

import android.view.View
import androidx.databinding.BindingAdapter
import com.gojek.assignment.api.helper.ErrorHandler
import com.gojek.assignment.api.helper.Result

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
}