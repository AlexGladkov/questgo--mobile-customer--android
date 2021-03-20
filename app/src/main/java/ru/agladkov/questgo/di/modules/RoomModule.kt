package ru.agladkov.questgo.di.modules

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import ru.agladkov.questgo.base.QuestRoomDatabase
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class RoomModule {

    @Provides
    @Singleton
    fun provideRoomDatabase(@ApplicationContext context: Context): QuestRoomDatabase =
        Room.databaseBuilder(
            context,
            QuestRoomDatabase::class.java,
            "quest_room_database"
        ).build()
}