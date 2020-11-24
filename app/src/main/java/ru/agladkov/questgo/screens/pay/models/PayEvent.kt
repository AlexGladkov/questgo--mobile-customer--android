package ru.agladkov.questgo.screens.pay.models

import com.android.billingclient.api.BillingClient

sealed class PayEvent {
    object ScreenShown : PayEvent()
    data class BuyQuest(val billingClient: BillingClient) : PayEvent()
}