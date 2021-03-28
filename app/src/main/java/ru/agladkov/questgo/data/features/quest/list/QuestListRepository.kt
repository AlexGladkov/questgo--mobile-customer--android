package ru.agladkov.questgo.data.features.quest.list

import android.util.Log
import com.google.gson.Gson
import io.reactivex.Observable
import io.reactivex.Single
import ru.agladkov.questgo.data.features.quest.list.room.dao.mapToRemote
import ru.agladkov.questgo.data.features.quest.remote.quest.QuestListItem
import ru.agladkov.questgo.data.features.quest.remote.quest.QuestListResponse

class QuestListRepository(
    private val gson: Gson,
    private val questListLocalDataSource: QuestListLocalDataSource,
    private val questListRemoteDataSource: QuestListRemoteDataSource
) {

    fun fetchQuestList(): Observable<QuestListResponse> {
        val remoteRequest: Observable<QuestListResponse> = questListRemoteDataSource.getQuestList().toObservable()
            .flatMap {
                val newArray = it.items + it.items
                questListLocalDataSource.saveRemoteResponse(it)
                    .andThen(Observable.just(QuestListResponse(newArray)))
            }

        val localRequest: Observable<QuestListResponse> = questListLocalDataSource.loadAllQuests().toObservable()
            .flatMap { entities ->
                Observable.just(
                    QuestListResponse(
                        items = entities.map { it.mapToRemote(gson) }
                    )
                )
            }

        return Observable.concat(localRequest, questListRemoteDataSource.getQuestList().toObservable())
    }
}