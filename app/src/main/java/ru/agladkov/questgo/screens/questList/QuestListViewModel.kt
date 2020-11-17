package ru.agladkov.questgo.screens.questList

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import ru.agladkov.questgo.data.remote.quest.QuestApi

class QuestListViewModel(application: Application): AndroidViewModel(application) {

    private val compositeDisposable = CompositeDisposable()

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }

    fun fetchQuestList(questApi: QuestApi?) {
        questApi?.let {
            compositeDisposable.add(questApi.getQuest("1")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({

                }, {

                }))
        }
    }
}