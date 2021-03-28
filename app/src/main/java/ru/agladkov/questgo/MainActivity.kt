package ru.agladkov.questgo

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var pushBroadcastReceiver: BroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val filter = IntentFilter()
        filter.addAction("MESSAGE_EVENT")

        pushBroadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                Log.e("TAG", "Message received")
                val extras = intent?.extras
                extras?.keySet()?.forEach { key ->
                    Log.e("TAG", "Key -> $key")
                    if (key == "action") {
                        Log.e("TAG", "Value -> ${extras.getString(key)?.toUpperCase()}")
                        when (extras.getString(key)?.toUpperCase()) {
                            "SHOW_MESSAGE" -> {
                                Toast.makeText(
                                    applicationContext,
                                    extras.getString(extras.keySet().first { it == "message" }),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }
            }
        }

        registerReceiver(pushBroadcastReceiver, filter)
    }

    override fun onDestroy() {
        unregisterReceiver(pushBroadcastReceiver)
        super.onDestroy()
    }
}