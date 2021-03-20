package ru.agladkov.questgo.base

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.agladkov.questgo.data.features.quest.list.room.dao.QuestListDao
import ru.agladkov.questgo.data.features.quest.list.room.dao.QuestListEntity

@Database(
    entities = [
        QuestListEntity::class
    ], version = 1, exportSchema = true
)
abstract class QuestRoomDatabase : RoomDatabase() {

    abstract fun questListDao(): QuestListDao
}