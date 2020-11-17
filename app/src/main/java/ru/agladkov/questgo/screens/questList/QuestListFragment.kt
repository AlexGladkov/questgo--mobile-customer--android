package ru.agladkov.questgo.screens.questList

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import ru.agladkov.QuestApp
import ru.agladkov.questgo.R

class QuestListFragment: Fragment(R.layout.fragment_quest_list) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val questListViewModel = ViewModelProviders.of(this).get(QuestListViewModel::class.java)
        questListViewModel.fetchQuestList((activity?.application as? QuestApp)?.questApi)
    }
}