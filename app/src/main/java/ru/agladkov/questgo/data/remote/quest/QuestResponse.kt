package ru.agladkov.questgo.data.remote.quest

import ru.agladkov.questgo.data.remote.common.RemoteListItem

data class QuestResponse(
    val questId: Int,
    val pages: List<QuestPage>
)

data class QuestPage(
    val pageId: Int,
    val code: String,
    val components: List<RemoteListItem>,
    val info: List<RemoteListItem>
)