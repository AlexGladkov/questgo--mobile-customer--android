package ru.agladkov.questgo.common.models

data class ImageCellModel(val value: String): ListItem {
    override fun uniqueViewTypeId(): Int = 2
}