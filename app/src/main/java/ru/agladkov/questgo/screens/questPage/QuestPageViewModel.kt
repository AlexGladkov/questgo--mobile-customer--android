package ru.agladkov.questgo.screens.questPage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import ru.agladkov.questgo.common.models.ButtonCellModel
import ru.agladkov.questgo.common.models.ListItem
import ru.agladkov.questgo.common.models.mapToUI
import ru.agladkov.questgo.data.remote.quest.QuestApi
import ru.agladkov.questgo.helpers.SingleLiveAction
import java.util.*
import javax.inject.Inject

sealed class QuestPageError {
    object RequestException : QuestPageError()
    object WrongAnswerException : QuestPageError()
}

class QuestPageViewModel @Inject constructor(
    private val questApi: QuestApi
) : ViewModel() {

    private var questPageId: Int? = null
    private var questId: Int? = null
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

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }

    fun setParams(pageId: Int?, questId: Int?) {
        this.questPageId = pageId
        this.questId = questId
    }

    fun fetchPage() {
        _isLoading.postValue(true)

        if (questId != null && questPageId != null) {
            questId?.let { questId ->
                questApi.getQuest(questId = questId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ response ->
                        _isLoading.postValue(false)

                        try {
                            val currentItem = response.pages[questPageId!! - 1]
                            _items.postValue(currentItem.components.map { it.mapToUI() })
                            infoBlock = currentItem.info.map { it.mapToUI() }.toMutableList()
                            correctAnswer = currentItem.code
                        } catch (e: Exception) {
                            _errorMessage.postValue(QuestPageError.RequestException)
                        }
                    }, {
                        _isLoading.postValue(false)
                        _errorMessage.postValue(QuestPageError.RequestException)
                    })
            }
        } else {
            _errorMessage.postValue(QuestPageError.RequestException)
        }
    }

    fun checkAnswer(text: String) {
        if (text.toUpperCase() == correctAnswer.toUpperCase()) {
            successAction.postValue(true)
            infoBlock.add(ButtonCellModel("Продолжить"))
            _items.postValue(infoBlock)
        } else {
            _errorMessage.postValue(QuestPageError.WrongAnswerException)
        }
    }
}