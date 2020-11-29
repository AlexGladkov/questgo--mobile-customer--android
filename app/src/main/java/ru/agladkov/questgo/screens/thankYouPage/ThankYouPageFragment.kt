package ru.agladkov.questgo.screens.thankYouPage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_thank_you_page.*
import ru.agladkov.questgo.R
import ru.agladkov.questgo.common.VisualComponentsAdapter
import ru.agladkov.questgo.common.models.*
import ru.agladkov.questgo.common.viewholders.ButtonCellDelegate
import ru.agladkov.questgo.screens.thankYouPage.models.ThankYouPageAction
import ru.agladkov.questgo.screens.thankYouPage.models.ThankYouPageEvent
import ru.agladkov.questgo.screens.thankYouPage.models.ThankYouPageViewState

@AndroidEntryPoint
class ThankYouPageFragment : BottomSheetDialogFragment() {

    private val viewModel: ThankYouPageViewModel by viewModels()
    private val visualComponentsAdapter = VisualComponentsAdapter()
    private lateinit var router: ThankYouPageRouter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        router = ThankYouPageRouterImpl(this)

        visualComponentsAdapter.buttonCellDelegate = object : ButtonCellDelegate {
            override fun onButtonClick(model: ButtonCellModel) {
                router.routeToShare()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_thank_you_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        itemsView.adapter = visualComponentsAdapter
        itemsView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        finishQuestButtonView.setOnClickListener {
            viewModel.obtainEvent(ThankYouPageEvent.GoToMain)
        }

        viewModel.viewStates().observe(viewLifecycleOwner, Observer { bindViewState(it) })
        viewModel.viewEffects().observe(viewLifecycleOwner, Observer { bindViewAction(it) })
        viewModel.obtainEvent(ThankYouPageEvent.ScreenShown)
    }

    private fun bindViewState(viewState: ThankYouPageViewState) {
        visualComponentsAdapter.setItems(viewState.screenRender)
    }

    private fun bindViewAction(viewAction: ThankYouPageAction) {
        when (viewAction) {
            is ThankYouPageAction.OpenMainScreen -> router.routeToMain()
        }
    }
}