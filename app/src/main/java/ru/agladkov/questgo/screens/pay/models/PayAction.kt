package ru.agladkov.questgo.screens.pay.models

sealed class PayAction {
    data class CloseWithResult(val isSuccessful: Boolean) : PayAction()
}