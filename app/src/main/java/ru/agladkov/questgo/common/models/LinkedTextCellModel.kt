package ru.agladkov.questgo.common.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LinkedTextCellModel(
    val text: String,
    val selectedStrings: List<LinkModel> = emptyList()
) : ListItem, Parcelable {
    override fun uniqueViewTypeId(): Int = 8
}

@Parcelize
data class LinkModel(
    val url: String,
    val selectedText: String
): Parcelable