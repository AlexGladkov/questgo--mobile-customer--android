package ru.agladkov.questgo.screens.questInfo.models

import ru.agladkov.questgo.screens.questList.adapter.QuestCellModel

sealed class QuestInfoEvent {
    data class ScreenShown(val questCellModel: QuestCellModel?) : QuestInfoEvent()
    object BuyQuest : QuestInfoEvent()
}