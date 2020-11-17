package ru.agladkov.questgo.common.models

data class VideoCellModel(val value: String): ListItem {
    override fun uniqueViewTypeId(): Int = 5
}