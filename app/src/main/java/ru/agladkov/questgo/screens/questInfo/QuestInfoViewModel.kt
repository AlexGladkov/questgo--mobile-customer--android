package ru.agladkov.questgo.screens.questInfo

import io.reactivex.disposables.CompositeDisposable
import ru.agladkov.questgo.base.BaseViewModel
import ru.agladkov.questgo.common.models.ButtonCellModel
import ru.agladkov.questgo.common.models.ListItem
import ru.agladkov.questgo.data.features.configuration.UserConfigurationLocalDataSource
import ru.agladkov.questgo.screens.questInfo.models.QuestInfoAction
import ru.agladkov.questgo.screens.questInfo.models.QuestInfoEvent
import ru.agladkov.questgo.screens.questInfo.models.QuestInfoViewState
import ru.agladkov.questgo.screens.questList.adapter.QuestCellModel
import javax.inject.Inject

class QuestInfoViewModel @Inject constructor(
    private val localDataSource: UserConfigurationLocalDataSource
) : BaseViewModel<QuestInfoViewState, QuestInfoAction, QuestInfoEvent>() {

    private val compositeDisposable = CompositeDisposable()
    private var questId = -1
    private val userConfiguration = localDataSource.readConfiguration()

    init {
        viewState = QuestInfoViewState(isBuying = false)
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

    override fun obtainEvent(viewEvent: QuestInfoEvent) {
        when (viewEvent) {
            is QuestInfoEvent.ScreenShown -> showDefaultData(questCellModel = viewEvent.questCellModel)
            is QuestInfoEvent.BuyQuest -> buyQuest()
        }
    }

    private fun showDefaultData(questCellModel: QuestCellModel?) {
        if (questCellModel == null) {
            // Fallback to error
            return
        }

        questId = questCellModel.questId
        val items = ArrayList<ListItem>().apply {
            addAll(questCellModel.description ?: emptyList())
            add(ButtonCellModel("Продолжить"))
        }

        viewState = viewState.copy(
            visualItems = items
        )
    }

    private fun buyQuest() {
        if (questId >= 0) {
            localDataSource.updateConfiguration(
                userConfiguration.copy(
                    currentUserPage = 0,
                    currentQuestId = questId
                )
            )

            viewAction = QuestInfoAction.OpenQuest(questId = questId, questPage = 0)
        } else {
            // Fallback to error
        }
    }
}