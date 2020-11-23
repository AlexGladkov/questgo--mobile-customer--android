package ru.agladkov.questgo.screens.questInfo.models

import com.android.billingclient.api.BillingClient
import ru.agladkov.questgo.screens.questList.adapter.QuestCellModel

sealed class QuestInfoEvent {
    object BuyQuest : QuestInfoEvent()
    data class StartBillingConnection(val billingClient: BillingClient, val questCellModel: QuestCellModel?) : QuestInfoEvent()
}