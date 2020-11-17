package ru.agladkov.questgo.common.models


data class ButtonCellModel(val title: String): ListItem {
    override fun uniqueViewTypeId(): Int = 0
}