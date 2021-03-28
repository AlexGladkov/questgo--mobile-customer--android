package ru.agladkov.questgo.data.features.quest.list.room

import com.google.gson.Gson
import io.reactivex.Completable
import io.reactivex.Single
import ru.agladkov.questgo.data.features.quest.list.QuestListLocalDataSource
import ru.agladkov.questgo.data.features.quest.list.room.dao.QuestListDao
import ru.agladkov.questgo.data.features.quest.list.room.dao.QuestListEntity
import ru.agladkov.questgo.data.features.quest.remote.quest.QuestListResponse

class RoomQuestListDataSource(private val gson: Gson, private val questListDao: QuestListDao) :
    QuestListLocalDataSource {

    override fun loadAllQuests(): Single<List<QuestListEntity>> = questListDao.loadAllQuests()

    override fun saveRemoteResponse(response: QuestListResponse): Completable =
        questListDao.saveAllQuests(response.items.map {
            QuestListEntity(
                questId = it.questId,
                name = it.name,
                subtitle = it.subtitle,
                items = gson.toJson(it.items),
                image = it.image
            )
        })

}