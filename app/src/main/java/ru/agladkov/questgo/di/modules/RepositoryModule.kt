package ru.agladkov.questgo.di.modules

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import ru.agladkov.questgo.data.features.configuration.UserConfigurationLocalDataSource
import ru.agladkov.questgo.data.features.configuration.sharedprefs.SharedPreferencesConfigurationLocalDataSource

@Module
@InstallIn(ApplicationComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindUserConfigurationDataSource(
        sharedPreferencesConfigurationLocalDataSource:
        SharedPreferencesConfigurationLocalDataSource
    ): UserConfigurationLocalDataSource
}