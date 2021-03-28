package ru.agladkov.questgo.data.features.quest.list.room.dao

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.agladkov.questgo.data.features.quest.list.room.dao.QuestListEntity.Companion.TABLE_NAME
import ru.agladkov.questgo.data.features.quest.remote.common.RemoteListItem
import ru.agladkov.questgo.data.features.quest.remote.quest.QuestListItem
import java.lang.reflect.Type

@Entity(tableName = TABLE_NAME)
data class QuestListEntity(
    @PrimaryKey @ColumnInfo(name = "questId")
    val questId: Int,
    @ColumnInfo(name = "questName")
    val name: String,
    @ColumnInfo(name = "questSubtitle")
    val subtitle: String,
    @ColumnInfo(name = "questImage")
    val image: String,
    @ColumnInfo(name = "description")
    val items: String
) {

    companion object {
        const val TABLE_NAME = "quest_list_entities_table"
    }
}

fun QuestListEntity.mapToRemote(gson: Gson): QuestListItem {
    val type: Type = object : TypeToken<List<RemoteListItem>>() {}.type

    return QuestListItem(
        questId = this.questId,
        name = this.name,
        subtitle = this.subtitle,
        image = this.image,
        items = gson.fromJson<List<RemoteListItem>>(this.items, type)
    )
}