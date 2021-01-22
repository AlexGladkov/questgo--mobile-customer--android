package ru.agladkov.questgo.screens.questInfo

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_quest_info.*
import kotlinx.coroutines.flow.collect
import ru.agladkov.questgo.R
import ru.agladkov.questgo.YoutubeActivity
import ru.agladkov.questgo.common.VisualComponentsAdapter
import ru.agladkov.questgo.common.models.ButtonCellModel
import ru.agladkov.questgo.common.models.LinkModel
import ru.agladkov.questgo.common.models.VideoCellModel
import ru.agladkov.questgo.common.viewholders.ButtonCellDelegate
import ru.agladkov.questgo.common.viewholders.LinkedTextDelegate
import ru.agladkov.questgo.common.viewholders.VideoCellDelegate
import ru.agladkov.questgo.screens.questInfo.models.QuestInfoAction
import ru.agladkov.questgo.screens.questInfo.models.QuestInfoEvent
import ru.agladkov.questgo.screens.questInfo.models.QuestInfoViewState
import ru.agladkov.questgo.screens.questList.adapter.QuestCellModel
import ru.agladkov.questgo.screens.questPage.QuestPageFragment.Companion.PAGE_ID
import ru.agladkov.questgo.screens.questPage.QuestPageFragment.Companion.QUEST_ID


@AndroidEntryPoint
class QuestInfoFragment : Fragment(R.layout.fragment_quest_info) {

    private val visualComponentsAdapter = VisualComponentsAdapter()
    private val viewModel: QuestInfoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        visualComponentsAdapter.buttonCellDelegate = object : ButtonCellDelegate {
            override fun onButtonClick(model: ButtonCellModel) {
                viewModel.obtainEvent(QuestInfoEvent.BuyQuest)
            }
        }

        visualComponentsAdapter.linkedTextDelegate = object : LinkedTextDelegate {
            override fun onClick(model: LinkModel) {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(model.url))
                startActivity(browserIntent)
            }
        }

        visualComponentsAdapter.videoCellDelegate = object : VideoCellDelegate {
            override fun onPlayClick(model: VideoCellModel?) {
                val intent = Intent(context, YoutubeActivity::class.java)
                intent.putExtra(YoutubeActivity.modelKey, model)
                startActivity(intent)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        itemsView.adapter = visualComponentsAdapter
        itemsView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        lifecycleScope.launchWhenStarted {
            viewModel.viewStates().collect { state -> state?.let { bindViewState(it) } }
            viewModel.viewActions().collect { action -> action?.let { bindViewAction(it) } }
        }

        viewModel.obtainEvent(
            QuestInfoEvent.StartBillingConnection(
                questCellModel = arguments?.get(QUEST) as? QuestCellModel
            )
        )
    }

    private fun bindViewAction(viewAction: QuestInfoAction) {
        when (viewAction) {
            is QuestInfoAction.OpenQuest -> routeToQuest(
                questId = viewAction.questId,
                questPage = viewAction.questPage
            )

            is QuestInfoAction.ShowError -> {
                Toast.makeText(context, getString(viewAction.message), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun bindViewState(viewState: QuestInfoViewState) {
        when {
            viewState.isLoading -> {
                itemsView.visibility = View.GONE
                loaderView.visibility = View.VISIBLE
            }

            !viewState.isLoading -> {
                itemsView.visibility = View.VISIBLE
                loaderView.visibility = View.GONE
                visualComponentsAdapter.setItems(viewState.visualItems)
            }
        }
    }

    private fun routeToQuest(questId: Int, questPage: Int) {
        findNavController().navigate(
            R.id.action_questInfoFragment_to_questPageFragment,
            bundleOf(
                QUEST_ID to questId,
                PAGE_ID to questPage
            )
        )
    }

    companion object {
        const val QUEST = "quest_key"
    }
}