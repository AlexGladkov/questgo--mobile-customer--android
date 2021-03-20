package ru.agladkov.questgo.data.features.quest.list

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import ru.agladkov.questgo.base.QuestRoomDatabase
import ru.agladkov.questgo.data.features.quest.list.retrofit.QuestListApi
import ru.agladkov.questgo.data.features.quest.list.retrofit.RetrofitQuestListDataSource
import ru.agladkov.questgo.data.features.quest.list.room.RoomQuestListDataSource
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class QuestListModule {

    @Provides
    @Singleton
    fun provideLocalDataSource(roomDatabase: QuestRoomDatabase): QuestListLocalDataSource =
        RoomQuestListDataSource(roomDatabase.questListDao())

    @Provides
    @Singleton
    fun provideRemoteDataSource(questListApi: QuestListApi): QuestListRemoteDataSource =
        RetrofitQuestListDataSource(questListApi)

    @Provides
    @Singleton
    fun provideQuestListRepository(
        localDataSource: QuestListLocalDataSource,
        remoteDataSource: QuestListRemoteDataSource
    ) = QuestListRepository(localDataSource, remoteDataSource)
}