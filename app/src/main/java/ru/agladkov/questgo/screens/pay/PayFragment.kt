package ru.agladkov.questgo.screens.pay

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_quest_info.*
import ru.agladkov.questgo.R
import ru.agladkov.questgo.common.VisualComponentsAdapter
import ru.agladkov.questgo.helpers.injectViewModel
import ru.agladkov.questgo.screens.pay.models.PayAction
import ru.agladkov.questgo.screens.pay.models.PayEvent
import ru.agladkov.questgo.screens.pay.models.PayViewState
import ru.agladkov.questgo.screens.questInfo.QuestInfoFragment
import ru.agladkov.questgo.screens.questInfo.models.QuestInfoEvent
import ru.agladkov.questgo.screens.questList.adapter.QuestCellModel
import javax.inject.Inject

class PayFragment: BottomSheetDialogFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var viewModel: PayViewModel

    private val visualComponentsAdapter = VisualComponentsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_pay, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = injectViewModel(factory = viewModelFactory)

        itemsView.adapter = visualComponentsAdapter
        itemsView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        viewModel.viewStates().observe(viewLifecycleOwner, Observer { bindViewState(it) })
        viewModel.viewEffects().observe(viewLifecycleOwner, Observer { bindViewAction(it) })
        viewModel.obtainEvent(PayEvent.ScreenShown)
    }

    private fun bindViewState(viewState: PayViewState) {
        visualComponentsAdapter.setItems(viewState.renderItems)
    }

    private fun bindViewAction(viewAction: PayAction) {

    }
}