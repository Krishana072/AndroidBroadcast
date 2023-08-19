package com.example.boundservices

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log

class MyBoundService : Service() {

    private val binder = MyBinder()

    inner class MyBinder : Binder() {
        fun getService(): MyBoundService = this@MyBoundService
    }

    override fun onBind(intent: Intent?): IBinder? {
        Log.d(TAG, "Service onBind")
        return binder
    }

    fun performTask(): String {
        // Perform your task here.
        return "Task completed successfully!"
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.d(TAG, "Service onUnbind")
        // Return true if you want to allow re-binding to this service, false otherwise.
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "Service onDestroy")
    }

    companion object {
        private const val TAG = "MyBoundService"
    }
}
