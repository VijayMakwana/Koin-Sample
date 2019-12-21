package com.gojek.assignment.api.helper

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.gojek.assignment.utils.isNetworkAvailable


interface ErrorHandler {
    fun onError(resource: Result<*>)
}

class AlertErrorHandler(
    private val context: Context?,
    private val isCancelable: Boolean = true,
    private var isNetworkAvailable: ((isNetworkAvailable: Boolean) -> Unit)
) : ErrorHandler {

    override fun onError(resource: Result<*>) {
        context?.let { ctx ->
            if (ctx.isNetworkAvailable()) {
                val builder = AlertDialog.Builder(ctx)
                builder.setCancelable(isCancelable)
                builder.setPositiveButton(android.R.string.yes) { dialogInterface, _ ->
                    dialogInterface.dismiss()
                }
                builder.show()
            } else {
                isNetworkAvailable.invoke(false)
            }
        }
    }
}
