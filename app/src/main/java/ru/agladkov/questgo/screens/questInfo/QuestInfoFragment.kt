package ru.agladkov.questgo.screens.questInfo

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_quest_info.*
import ru.agladkov.questgo.R
import ru.agladkov.questgo.common.VisualComponentsAdapter
import ru.agladkov.questgo.screens.questList.adapter.QuestCellModel

class QuestInfoFragment: Fragment(R.layout.fragment_quest_info) {

    private val visualComponentsAdapter = VisualComponentsAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        itemsView.adapter = visualComponentsAdapter
        itemsView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        val model = arguments?.get(QUEST) as? QuestCellModel
        model?.let {
            visualComponentsAdapter.setItems(it.description)
        }
    }

    companion object {
        const val QUEST = "quest_key"
    }
}