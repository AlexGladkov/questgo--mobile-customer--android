package ru.agladkov.questgo.common.models

data class HeaderCellModel(val value: String): ListItem {
    override fun uniqueViewTypeId(): Int = 1
}