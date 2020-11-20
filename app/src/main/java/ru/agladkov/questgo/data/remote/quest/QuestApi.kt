package ru.agladkov.questgo.data.remote.quest

import io.reactivex.Single
import retrofit2.http.*

interface QuestApi {

    @GET("./questList")
    @Headers("Content-Type: application/json", "apiKey: hREenbWAcsYl4tJKN47c")
    fun getQuestList(): Single<QuestListResponse>

    @GET("/questInfo")
    @Headers("Content-Type: application/json", "apiKey: hREenbWAcsYl4tJKN47c")
    fun getQuest(@Query("questId") questId: Int): Single<QuestResponse>
}