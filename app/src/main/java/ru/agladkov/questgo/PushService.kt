package ru.agladkov.questgo

import android.os.Handler

interface PushListener {
    fun onValueChanged(message: String)
}

class PushService {

    private val subscribers: MutableList<PushListener> = ArrayList()

    fun attachListener(listener: PushListener) {
        subscribers.add(listener)
    }

    fun detachListener(listener: PushListener) {
        subscribers.remove(listener)
    }

    fun startBody() {
        Handler().postDelayed({
            sendPush()
        }, 3000)
    }

    private fun sendPush() {
        subscribers.forEach {
            it.onValueChanged(message = "You've got a push")
        }
    }
}