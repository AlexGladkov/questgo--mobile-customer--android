package ru.agladkov.questgo.screens.pay.models

sealed class PayAction {
    object CloseWithResult : PayAction()
}