package ru.agladkov

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.HiltAndroidApp
import ru.agladkov.questgo.R

@HiltAndroidApp
class QuestApp : Application() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate() {
        super.onCreate()

        sharedPreferences = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE)
        val token = sharedPreferences.getString(TOKEN_KEY, null)

        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                return@addOnCompleteListener
            }

            val newToken = task.result
            sharedPreferences.edit().putString(TOKEN_KEY, newToken).apply()

            Log.e("TAG", "Token -> $token")
            Log.e("TAG", "New token -> $newToken")
        }
    }

    companion object {
        const val TOKEN_KEY = "TOKEN_KEY"
    }
}