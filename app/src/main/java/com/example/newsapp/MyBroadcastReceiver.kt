package com.example.newsapi

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class MyBroadcastReceiver(val listener: MyBroadcastListener) : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
       listener.updateDetailActivity()
    }
}