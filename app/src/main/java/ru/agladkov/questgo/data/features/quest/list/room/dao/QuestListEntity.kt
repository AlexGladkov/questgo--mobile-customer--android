package ru.agladkov.questgo.data.features.quest.list.room.dao

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.agladkov.questgo.data.features.quest.list.room.dao.QuestListEntity.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
class QuestListEntity(
    @PrimaryKey @ColumnInfo(name = "questId") val questId: Int,
    @ColumnInfo(name = "questName") val name: String,
    @ColumnInfo(name = "questSubtitle") val subtitle: String,
    @ColumnInfo(name = "questImage") val image: String,
    @ColumnInfo(name = "description") val items: String
) {

    companion object {
        const val TABLE_NAME = "quest_list_table"
    }
}