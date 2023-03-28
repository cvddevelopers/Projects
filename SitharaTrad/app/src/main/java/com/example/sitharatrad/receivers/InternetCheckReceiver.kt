package com.example.sitharatrad.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.sitharatrad.Model.NetworkUtil
import android.widget.Toast
import com.example.sitharatrad.R

abstract class InternetCheckReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val status = NetworkUtil.getConnectivityStatusString(context)
        if (status == 0) {
            onNetworkDisConnected()
           Toast.makeText(context,context.getString(R.string.internet_discon),Toast.LENGTH_LONG).show()
        } else {
            onNetworkConnected()
            Toast.makeText(context,context.getString(R.string.internet_con),Toast.LENGTH_LONG).show()
        }
    }

    protected abstract fun onNetworkConnected()
    protected abstract fun onNetworkDisConnected()
}