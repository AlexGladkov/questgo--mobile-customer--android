package ru.agladkov.questgo.data.features.quest.list

import io.reactivex.Completable
import io.reactivex.Single
import ru.agladkov.questgo.data.features.quest.list.room.dao.QuestListEntity
import ru.agladkov.questgo.data.features.quest.remote.quest.QuestListResponse

interface QuestListLocalDataSource {

    fun loadAllQuests(): Single<List<QuestListEntity>>

    fun saveRemoteResponse(response: QuestListResponse): Completable
}