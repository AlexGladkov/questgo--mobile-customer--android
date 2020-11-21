package ru.agladkov.questgo.screens.questPage.models

sealed class QuestPageAction {
    data class OpenNextPage(val questId: Int, val questPage: Int): QuestPageAction()
    object OpenFinalPage : QuestPageAction()
}