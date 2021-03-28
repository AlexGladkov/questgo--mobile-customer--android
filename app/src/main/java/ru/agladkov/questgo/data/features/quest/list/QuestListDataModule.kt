package ru.agladkov.questgo.data.features.quest.list

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.agladkov.questgo.base.QuestRoomDatabase
import ru.agladkov.questgo.data.features.quest.list.retrofit.RetrofitQuestListDataSource
import ru.agladkov.questgo.data.features.quest.list.room.RoomQuestListDataSource
import ru.agladkov.questgo.data.features.quest.remote.quest.QuestApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class QuestListDataModule {

    @Provides
    @Singleton
    fun provideLocalDataSource(gson: Gson, roomDatabase: QuestRoomDatabase): QuestListLocalDataSource =
        RoomQuestListDataSource(gson, roomDatabase.questListDao())

    @Provides
    @Singleton
    fun provideRemoteDataSource(questApi: QuestApi): QuestListRemoteDataSource =
        RetrofitQuestListDataSource(questApi)

    @Provides
    @Singleton
    fun provideQuestListRepository(
        gson: Gson,
        local: QuestListLocalDataSource,
        remote: QuestListRemoteDataSource
    ): QuestListRepository =
        QuestListRepository(gson, local, remote)
}