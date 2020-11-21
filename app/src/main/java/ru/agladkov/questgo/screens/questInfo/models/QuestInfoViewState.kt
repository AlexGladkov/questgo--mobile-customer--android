package ru.agladkov.questgo.screens.questInfo.models

import ru.agladkov.questgo.common.models.ListItem

data class QuestInfoViewState(
    val isBuying: Boolean = false,
    val visualItems: List<ListItem> = emptyList()
)