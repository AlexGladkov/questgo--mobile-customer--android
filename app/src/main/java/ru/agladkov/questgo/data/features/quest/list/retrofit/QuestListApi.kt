package ru.agladkov.questgo.data.features.quest.list.retrofit

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Headers
import ru.agladkov.questgo.data.features.quest.remote.quest.QuestListResponse

interface QuestListApi {

    @GET("./questList")
    @Headers("Content-Type: application/json", "apiKey: hREenbWAcsYl4tJKN47c")
    fun getQuestList(): Single<QuestListResponse>
}