package ru.agladkov.questgo.screens.questList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import ru.agladkov.questgo.R
import ru.agladkov.questgo.base.BaseViewModel
import ru.agladkov.questgo.data.features.configuration.UserConfigurationLocalDataSource
import ru.agladkov.questgo.data.features.quest.remote.quest.QuestApi
import ru.agladkov.questgo.screens.questInfo.models.QuestInfoAction
import ru.agladkov.questgo.screens.questInfo.models.QuestInfoEvent
import ru.agladkov.questgo.screens.questInfo.models.QuestInfoViewState
import ru.agladkov.questgo.screens.questList.adapter.QuestCellModel
import ru.agladkov.questgo.screens.questList.adapter.mapToUI
import ru.agladkov.questgo.screens.questList.models.QuestListAction
import ru.agladkov.questgo.screens.questList.models.QuestListEvent
import ru.agladkov.questgo.screens.questList.models.QuestListViewState
import javax.inject.Inject

class QuestListViewModel @Inject constructor(
    private val questApi: QuestApi,
    private val userConfigurationLocalDataSource: UserConfigurationLocalDataSource
) : BaseViewModel<QuestListViewState, QuestListAction, QuestListEvent>() {

    private val compositeDisposable = CompositeDisposable()

    init {
        viewState = QuestListViewState.Loading
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }

    override fun obtainEvent(viewEvent: QuestListEvent) {
        when (viewEvent) {
            is QuestListEvent.FetchInitial -> checkOpenedQuest()
            is QuestListEvent.QuestClicks -> openQuest(model = viewEvent.questCellModel)
        }
    }

    private fun checkOpenedQuest() {
        val userConfiguration = userConfigurationLocalDataSource.readConfiguration()
        if (userConfiguration.currentQuestId >= 0) {
            viewAction = QuestListAction.OpenQuestPage(
                questId = userConfiguration.currentQuestId,
                questPage = userConfiguration.currentUserPage
            )
        } else {
            fetchQuestList()
        }
    }

    private fun openQuest(model: QuestCellModel) {
        viewAction = QuestListAction.OpenQuestInfo(questCellModel = model)
    }

    private fun fetchQuestList() {
        viewState = QuestListViewState.Loading
        compositeDisposable.add(
            questApi.getQuestList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    viewState = QuestListViewState.Success(response.items.map { it.mapToUI() })
                }, {
                    viewState = QuestListViewState.Error(message = R.string.error_loading_data)
                })
        )
    }
}