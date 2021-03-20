package ru.agladkov.questgo.data.features.quest.list.retrofit

import io.reactivex.Single
import ru.agladkov.questgo.data.features.quest.list.QuestListRemoteDataSource
import ru.agladkov.questgo.data.features.quest.remote.quest.QuestListResponse

class RetrofitQuestListDataSource(private val questListApi: QuestListApi): QuestListRemoteDataSource {

    override fun fetchRemoteQuestList(): Single<QuestListResponse> =
        questListApi.getQuestList()
}