package ru.agladkov.questgo.screens.questInfo

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_quest_info.*
import ru.agladkov.questgo.R
import ru.agladkov.questgo.common.VisualComponentsAdapter
import ru.agladkov.questgo.common.models.ButtonCellModel
import ru.agladkov.questgo.common.models.ListItem
import ru.agladkov.questgo.common.viewholders.ButtonCellDelegate
import ru.agladkov.questgo.screens.questList.adapter.QuestCellModel
import ru.agladkov.questgo.screens.questPage.QuestPageFragment.Companion.PAGE_ID
import ru.agladkov.questgo.screens.questPage.QuestPageFragment.Companion.QUEST_ID

class QuestInfoFragment : Fragment(R.layout.fragment_quest_info) {

    private val visualComponentsAdapter = VisualComponentsAdapter()
    private var questModel: QuestCellModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        visualComponentsAdapter.buttonCellDelegate = object : ButtonCellDelegate {
            override fun onButtonClick(model: ButtonCellModel) {
                routeToQuest()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        itemsView.adapter = visualComponentsAdapter
        itemsView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        questModel = arguments?.get(QUEST) as? QuestCellModel
        val items = ArrayList<ListItem>().apply {
            addAll(questModel?.description ?: emptyList())
            add(ButtonCellModel("Продолжить"))
        }

        visualComponentsAdapter.setItems(items)
    }

    private fun routeToQuest() {
        questModel?.let {
            findNavController().navigate(
                R.id.action_questInfoFragment_to_questPageFragment,
                bundleOf(
                    QUEST_ID to it.questId,
                    PAGE_ID to 1
                )
            )
        }
    }

    companion object {
        const val QUEST = "quest_key"
    }
}