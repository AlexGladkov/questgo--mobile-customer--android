package ru.agladkov.questgo.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import ru.agladkov.QuestApp
import ru.agladkov.questgo.di.modules.ActivityBindingModule
import ru.agladkov.questgo.di.modules.RemoteModule
import ru.agladkov.questgo.di.modules.ScreenBindingModule
import ru.agladkov.questgo.di.modules.ViewModelModule
import ru.agladkov.questgo.screens.questList.QuestListModule
import ru.agladkov.questgo.screens.questList.QuestListViewModel

@Component(
    modules = [
        AndroidInjectionModule::class,
        ActivityBindingModule::class,
        ScreenBindingModule::class,
        ViewModelModule::class,
        RemoteModule::class,
        QuestListModule::class
    ]
)
@AppScope
interface AppComponent : AndroidInjector<QuestApp> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): AppComponent.Builder

        fun build(): AppComponent
    }
}