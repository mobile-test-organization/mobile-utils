package br.com.spotsales.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager

object Reachability {
    var isReachable = false

    private var isReceiving = false
    private var receiver: BroadcastReceiver? = null

    fun registerReachability(context: Context): Boolean {
        val connectivityManager = context
            .getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager

        if (isReceiving) {
            context.unregisterReceiver(receiver)
            isReceiving = false
        }

        val info = connectivityManager?.activeNetworkInfo
        isReachable = info != null && info.isConnected

        receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                val manager = context
                    .getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
                val netInfo = manager?.activeNetworkInfo

                isReachable = netInfo != null && netInfo.isConnected
            }
        }

        context.registerReceiver(receiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
        isReceiving = true

        return isReachable
    }
}
