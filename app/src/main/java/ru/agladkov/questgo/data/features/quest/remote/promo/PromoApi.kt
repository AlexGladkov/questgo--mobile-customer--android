package ru.agladkov.questgo.data.features.quest.remote.promo

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface PromoApi {

    @GET("./promo")
    @Headers("Content-Type: application/json", "apiKey: hREenbWAcsYl4tJKN47c")
    fun getPromo(@Query("code") code: String): Single<PromoResponse>
}