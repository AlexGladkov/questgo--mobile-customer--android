package ru.agladkov.questgo.data.remote.quest

import io.reactivex.Single
import retrofit2.http.*

interface QuestApi {

    @GET("./getQuestList")
    @Headers("Content-Type: application/json")
    fun getQuestList(): Single<QuestListResponse>

    @GET("/getQuest/{questId}")
    @Headers("Content-Type: application/json")
    fun getQuest(@Path("questId") questId: String): Single<String>
}