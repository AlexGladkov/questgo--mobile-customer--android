package ru.agladkov.questgo.screens.questPage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import ru.agladkov.questgo.common.models.ListItem
import ru.agladkov.questgo.common.models.mapToUI
import ru.agladkov.questgo.data.remote.quest.QuestApi
import javax.inject.Inject

class QuestPageViewModel @Inject constructor(
    private val questApi: QuestApi
) : ViewModel() {

    private var questPageId: Int? = null
    private var questId: Int? = null
    private val compositeDisposable = CompositeDisposable()

    private val _items: MutableLiveData<List<ListItem>> = MutableLiveData(emptyList())
    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData(true)
    private val _errorMessage: MutableLiveData<String> = MutableLiveData("")

    val items: LiveData<List<ListItem>> = _items
    val isLoading: LiveData<Boolean> = _isLoading
    val errorMessage: LiveData<String> = _errorMessage

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
                        _items.postValue(response.pages[questPageId!! - 1].components.map { it.mapToUI() })
                    }, {
                        _isLoading.postValue(false)
                        _errorMessage.postValue(it.localizedMessage)
                    })
            }
        } else {
            _errorMessage.postValue("Params didn't valid")
        }
    }
}