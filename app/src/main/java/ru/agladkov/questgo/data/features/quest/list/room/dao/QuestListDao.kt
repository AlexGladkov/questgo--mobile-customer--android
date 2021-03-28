package ru.agladkov.questgo.data.features.quest.list.room.dao

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface QuestListDao {

    @Query("SELECT * FROM ${QuestListEntity.TABLE_NAME}")
    fun loadAllQuests(): Single<List<QuestListEntity>>

    @Insert(entity = QuestListEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun addQuest(questEntity: QuestListEntity): Completable

    @Insert
    @JvmSuppressWildcards
    fun saveAllQuests(entities: List<QuestListEntity>): Completable

    @Query("DELETE FROM ${QuestListEntity.TABLE_NAME} WHERE :questId like questId")
    fun deleteQuest(questId: Int): Completable
}
