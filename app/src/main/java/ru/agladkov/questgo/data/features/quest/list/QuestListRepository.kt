package ru.agladkov.questgo.data.features.quest.list

import io.reactivex.Single
import ru.agladkov.questgo.data.features.quest.remote.quest.QuestListResponse

class QuestListRepository(
    val localDataSource: QuestListLocalDataSource,
    private val remoteDataSource: QuestListRemoteDataSource
) {

    fun fetchQuestList(): Single<QuestListResponse> {
        return remoteDataSource.fetchRemoteQuestList()
    }
}