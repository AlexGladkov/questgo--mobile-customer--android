package ru.agladkov.questgo.common.models

data class TextCellModel(val value: String): ListItem {
    override fun uniqueViewTypeId(): Int = 3
}