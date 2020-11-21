package ru.agladkov.questgo.data.features.configuration.models

data class UserConfiguration(
    val currentUserPage: Int,
    val currentQuestId: Int,
    val availableQuestCount: Int
) {

    companion object {
        fun getInstance(): UserConfiguration = UserConfiguration(
            currentUserPage = -1, currentQuestId = -1,
            availableQuestCount = 0
        )
    }
}