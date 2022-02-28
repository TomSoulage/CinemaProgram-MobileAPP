package com.example.androidproject

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.androidproject.utils.setBooleanPreference
import com.example.androidproject.utils.startActivity

class MoviesReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        context.setBooleanPreference(DATA_IMPORTED,true)
        context.startActivity<HostActivity>()
    }
}