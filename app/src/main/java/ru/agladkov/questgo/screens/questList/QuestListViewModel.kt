package ru.agladkov.questgo.screens.questList

import android.app.Application
import android.util.Log
import android.widget.ListView
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import ru.agladkov.questgo.common.models.ListItem
import ru.agladkov.questgo.data.remote.quest.QuestApi
import ru.agladkov.questgo.data.remote.quest.QuestListItem
import ru.agladkov.questgo.data.remote.quest.QuestListResponse
import ru.agladkov.questgo.screens.questList.adapter.QuestCellModel
import ru.agladkov.questgo.screens.questList.adapter.mapToUI
import javax.inject.Inject

class QuestListViewModel @Inject constructor(
    private val questApi: QuestApi
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val _items: MutableLiveData<List<QuestCellModel>?> = MutableLiveData(null)
    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData(true)

    val items: LiveData<List<QuestCellModel>?> = _items
    val isLoading: LiveData<Boolean> = _isLoading

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }

    fun fetchQuestList() {
        _isLoading.postValue(true)
        compositeDisposable.add(
            questApi.getQuestList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    _isLoading.postValue(false)
                    _items.postValue(response.items.map { it.mapToUI() })
                }, {
                    _isLoading.postValue(false)
                })
        )
    }
}