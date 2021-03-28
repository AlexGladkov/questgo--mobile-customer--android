package ru.agladkov.questgo.di.modules

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.agladkov.questgo.data.features.configuration.UserConfigurationLocalDataSource
import ru.agladkov.questgo.data.features.configuration.sharedprefs.SharedPreferencesConfigurationLocalDataSource

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindUserConfigurationDataSource(
        sharedPreferencesConfigurationLocalDataSource:
        SharedPreferencesConfigurationLocalDataSource
    ): UserConfigurationLocalDataSource
}