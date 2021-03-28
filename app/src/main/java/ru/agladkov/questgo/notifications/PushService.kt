package ru.agladkov.questgo.notifications

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.os.bundleOf
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class PushService: FirebaseMessagingService() {

    override fun onNewToken(newToken: String) {
        super.onNewToken(newToken)

        Log.e("TAG", "Second token -> $newToken")
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        val intent = Intent("MESSAGE_EVENT")
        remoteMessage.data.forEach { mapEntry ->
            intent.putExtra(mapEntry.key, mapEntry.value)
        }

        sendBroadcast(intent)
    }
}