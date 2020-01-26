package com.koinsample.api.helper

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.koinsample.utils.isNetworkAvailable


interface ErrorHandler {
    fun onError(result: Result<*>)
}

class AlertErrorHandler(
    private val context: Context?,
    private val isCancelable: Boolean = true,
    private var isNetworkAvailable: ((isNetworkAvailable: Boolean) -> Unit)
) : ErrorHandler {

    override fun onError(result: Result<*>) {
        context?.let { ctx ->
            if (ctx.isNetworkAvailable()) {
                isNetworkAvailable.invoke(true)
                val builder = AlertDialog.Builder(ctx)
                builder.setCancelable(isCancelable)
                builder.setMessage(result.errorMessage)
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
