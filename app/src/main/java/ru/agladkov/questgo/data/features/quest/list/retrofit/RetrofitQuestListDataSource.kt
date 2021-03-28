package ru.agladkov.questgo.data.features.quest.list.retrofit

import io.reactivex.Single
import ru.agladkov.questgo.data.features.quest.list.QuestListRemoteDataSource
import ru.agladkov.questgo.data.features.quest.remote.quest.QuestApi
import ru.agladkov.questgo.data.features.quest.remote.quest.QuestListResponse

class RetrofitQuestListDataSource(private val questApi: QuestApi): QuestListRemoteDataSource {

    override fun getQuestList(): Single<QuestListResponse> = questApi.getQuestList()
}