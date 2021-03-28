package ru.agladkov.questgo.data.features.quest.list

import io.reactivex.Single
import ru.agladkov.questgo.data.features.quest.remote.quest.QuestListResponse

interface QuestListRemoteDataSource {

    fun getQuestList(): Single<QuestListResponse>
}