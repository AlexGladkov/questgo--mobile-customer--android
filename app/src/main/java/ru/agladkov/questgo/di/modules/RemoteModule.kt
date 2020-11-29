package ru.agladkov.questgo.di.modules

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.agladkov.questgo.data.features.quest.remote.promo.PromoApi
import ru.agladkov.questgo.data.features.quest.remote.quest.QuestApi
import ru.agladkov.questgo.di.qualifiers.DebugRetrofit
import ru.agladkov.questgo.di.qualifiers.MainRetrofit
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class RemoteModule {

    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        return httpLoggingInterceptor
    }

    @Provides
    @Singleton
    fun provideHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()

    @MainRetrofit
    @Provides
    @Singleton
    fun provideMainRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://fierce-ravine-19828.herokuapp.com")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

    @DebugRetrofit
    @Provides
    @Singleton
    fun provideDebugRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl("http://localhost:5000")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideQuestApi(@MainRetrofit retrofit: Retrofit): QuestApi = retrofit.create(QuestApi::class.java)

    @Provides
    @Singleton
    fun providePromoApi(@MainRetrofit retrofit: Retrofit): PromoApi = retrofit.create(PromoApi::class.java)
}