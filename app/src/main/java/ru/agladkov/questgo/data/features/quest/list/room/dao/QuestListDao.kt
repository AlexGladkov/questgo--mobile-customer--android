package ru.agladkov.questgo.data.features.quest.list.room.dao

import androidx.room.Dao
import androidx.room.Query
import io.reactivex.Single

@Dao
interface QuestListDao {

    @Query("SELECT * FROM ${QuestListEntity.TABLE_NAME}")
    fun getQuestList(): Single<List<QuestListEntity>>
}