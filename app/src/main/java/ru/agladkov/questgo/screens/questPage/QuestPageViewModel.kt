package ru.agladkov.questgo.screens.questPage

import android.widget.Button
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import ru.agladkov.questgo.R
import ru.agladkov.questgo.base.BaseViewModel
import ru.agladkov.questgo.common.models.ButtonCellModel
import ru.agladkov.questgo.common.models.ListItem
import ru.agladkov.questgo.common.models.mapToUI
import ru.agladkov.questgo.data.features.quest.remote.quest.QuestApi
import ru.agladkov.questgo.screens.questPage.models.QuestPageAction
import ru.agladkov.questgo.screens.questPage.models.QuestPageEvent
import ru.agladkov.questgo.screens.questPage.models.QuestPageFetchStatus
import ru.agladkov.questgo.screens.questPage.models.QuestPageViewState
import java.lang.Exception
import javax.inject.Inject

sealed class QuestPageError {
    object RequestException : QuestPageError()
    object WrongAnswerException : QuestPageError()
}

class QuestPageViewModel @Inject constructor(
    private val questApi: QuestApi
) : BaseViewModel<QuestPageViewState, QuestPageAction, QuestPageEvent>() {

    private var maxQuestPages = -1
    private val compositeDisposable = CompositeDisposable()
    private var correctAnswer = ""
    private var infoBlock: MutableList<ListItem> = emptyList<ListItem>().toMutableList()

    private val _items: MutableLiveData<List<ListItem>> = MutableLiveData(emptyList())
    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData(true)
    private val _errorMessage: MutableLiveData<QuestPageError?> = MutableLiveData(null)

    val items: LiveData<List<ListItem>> = _items
    val isLoading: LiveData<Boolean> = _isLoading
    val errorMessage: LiveData<QuestPageError?> = _errorMessage
    val successAction = MutableLiveData<Boolean>(false)

    init {
        viewState = QuestPageViewState()
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }

    override fun obtainEvent(viewEvent: QuestPageEvent) {
        when (viewEvent) {
            is QuestPageEvent.FetchInitial -> fetchPage(viewEvent.questId, viewEvent.questPage)
            is QuestPageEvent.SendAnswer -> checkAnswer(viewEvent.code)
            is QuestPageEvent.ShowNextPage -> checkNextPageAvailable()
        }
    }

    private fun fetchPage(questId: Int?, questPage: Int?) {
        if (questId == null) {
            // Fallback to error
            return
        }

        viewState = viewState.copy(
            fetchStatus = QuestPageFetchStatus.Loading,
            currentQuestId = questId,
            currentPage = questPage ?: 0
        )

        compositeDisposable.add(
            questApi.getQuest(questId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    viewState = try {
                        val currentPage = response.pages[viewState.currentPage]
                        viewState.copy(
                            infoData = currentPage.info.map { it.mapToUI() },
                            currentQuestMaxPages = response.pages.count(),
                            correctAnswer = currentPage.code,
                            fetchStatus = QuestPageFetchStatus.ShowContent(
                                items = currentPage.components.map { it.mapToUI() }
                            )
                        )
                    } catch (e: Exception) {
                        viewState.copy(
                            fetchStatus = QuestPageFetchStatus.Error(QuestPageError.RequestException)
                        )
                    }
                }, {
                    viewState = viewState.copy(
                        fetchStatus = QuestPageFetchStatus.Error(QuestPageError.RequestException)
                    )
                })
        )
    }

    private fun checkNextPageAvailable() {
        viewAction = if (viewState.currentQuestMaxPages == viewState.currentPage + 1) {
            QuestPageAction.OpenFinalPage
        } else {
            QuestPageAction.OpenNextPage(
                questId = viewState.currentQuestId,
                questPage = viewState.currentPage + 1
            )
        }
    }

    private fun checkAnswer(text: String) {
        viewState = if (text.equals(viewState.correctAnswer, ignoreCase = true)) {
            val currentInfoBlock = viewState.infoData.toMutableList()
            viewState.copy(
                fetchStatus = QuestPageFetchStatus.ShowInfo(currentInfoBlock.apply {
                    this += ButtonCellModel("Продолжить")
                })
            )
        } else {
            viewState.copy(
                fetchStatus = QuestPageFetchStatus.Error(QuestPageError.WrongAnswerException)
            )
        }
    }
}