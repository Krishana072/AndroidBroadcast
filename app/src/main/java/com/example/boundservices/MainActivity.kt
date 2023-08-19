package com.example.boundservices

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log

class MainActivity : AppCompatActivity() {
    private var boundService: MyBoundService? = null
    private var isBound: Boolean = false

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as MyBoundService.MyBinder
            boundService = binder.getService()
            isBound = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            boundService = null
            isBound = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Bind to the service when the activity is created.
        val serviceIntent = Intent(this, MyBoundService::class.java)
        bindService(serviceIntent, serviceConnection, Context.BIND_AUTO_CREATE)
    }

    override fun onDestroy() {
        super.onDestroy()

        // Unbind from the service when the activity is destroyed to avoid resource leaks.
        if (isBound) {
            unbindService(serviceConnection)
            isBound = false
        }
    }

    // Example of using the bound service's method.
    private fun doTaskWithBoundService() {
        val result = boundService?.performTask()
        Log.d(TAG, "Result from bound service: $result")
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}