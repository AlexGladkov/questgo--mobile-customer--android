package ru.agladkov.questgo.screens.promo.models

import ru.agladkov.questgo.common.models.ListItem

data class PromoViewState(
    val isSending: Boolean,
    val code: String,
    val renderData: List<ListItem> = emptyList()
)