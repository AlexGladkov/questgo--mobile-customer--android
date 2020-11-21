package ru.agladkov.questgo.screens.questPage.models

sealed class QuestPageEvent {
    data class FetchInitial(val questId: Int?, val questPage: Int?) : QuestPageEvent()
    data class SendAnswer(val code: String) : QuestPageEvent()
    object ShowNextPage : QuestPageEvent()
}