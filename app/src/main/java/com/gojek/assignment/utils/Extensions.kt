package com.gojek.assignment.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.view.View


/**
 * Sets the view's visibility to GONE
 */
fun View.hide() {
    visibility = View.GONE
}

/**
 * Sets the view's visibility to VISIBLE
 */
fun View.show() {
    visibility = View.VISIBLE
}

/**
 * Sets the view's visibility to INVISIBLE
 */
fun View.invisible() {
    visibility = View.INVISIBLE
}

/**
 * Returns false if it is null
 */
fun Boolean?.orFalse(): Boolean = this ?: false


/**
 * Checks for network availability
 * NOTE: Don't forget to add android.permission.ACCESS_NETWORK_STATE permission to manifest
 * @return a boolean representing if network is available or not
 */
fun Context.isNetworkAvailable(): Boolean {
    val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    if (Build.VERSION.SDK_INT < 23) {
        val ni = cm.activeNetworkInfo
        if (ni != null) {
            return ni.isConnected && (ni.type == ConnectivityManager.TYPE_WIFI || ni.type == ConnectivityManager.TYPE_MOBILE)
        }
    } else {
        val n = cm.activeNetwork
        if (n != null) {
            val nc = cm.getNetworkCapabilities(n)
            return nc?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR).orFalse() || nc?.hasTransport(
                NetworkCapabilities.TRANSPORT_WIFI
            ).orFalse()
        }
    }
    return false
}

