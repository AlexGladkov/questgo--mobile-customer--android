package ru.agladkov.questgo.screens.questList

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_quest_list.*
import ru.agladkov.questgo.R
import ru.agladkov.questgo.helpers.injectViewModel
import ru.agladkov.questgo.screens.questInfo.QuestInfoFragment
import ru.agladkov.questgo.screens.questList.adapter.QuestCellModel
import ru.agladkov.questgo.screens.questList.adapter.QuestListAdapter
import ru.agladkov.questgo.screens.questList.adapter.QuestListAdapterClicks
import javax.inject.Inject

class QuestListFragment : Fragment(R.layout.fragment_quest_list) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var viewModel: QuestListViewModel

    private val questListAdapter = QuestListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)

        questListAdapter.attachClicks(object : QuestListAdapterClicks {
            override fun onItemClick(model: QuestCellModel) {
                routeToQuest(model = model)
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = injectViewModel(factory = viewModelFactory)

        viewModel.isLoading.observe(viewLifecycleOwner, Observer {
            loaderView.visibility = if (it) View.VISIBLE else View.GONE
            itemsView.visibility = if (it) View.GONE else View.VISIBLE
        })
        viewModel.items.observe(viewLifecycleOwner, Observer { response ->
            response?.let {
                questListAdapter.setItems(newItems = it)
            }
        })

        itemsView.adapter = questListAdapter
        itemsView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        viewModel.fetchQuestList()
    }

    private fun routeToQuest(model: QuestCellModel) {
        findNavController().navigate(
            R.id.action_questListFragment_to_questInfoFragment,
            bundleOf(QuestInfoFragment.QUEST to model)
        )
    }
}