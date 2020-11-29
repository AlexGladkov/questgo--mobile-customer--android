package ru.agladkov.questgo.screens.promo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_promo.*
import ru.agladkov.questgo.R
import ru.agladkov.questgo.common.VisualComponentsAdapter
import ru.agladkov.questgo.common.models.ButtonCellModel
import ru.agladkov.questgo.common.models.TextFieldCellModel
import ru.agladkov.questgo.common.viewholders.ButtonCellDelegate
import ru.agladkov.questgo.common.viewholders.TextFieldCellDelegate
import ru.agladkov.questgo.helpers.setNavigationResult
import ru.agladkov.questgo.screens.promo.models.PromoAction
import ru.agladkov.questgo.screens.promo.models.PromoEvent
import ru.agladkov.questgo.screens.promo.models.PromoViewState

@AndroidEntryPoint
class PromoFragment : BottomSheetDialogFragment() {

    private val viewModel: PromoViewModel by viewModels()

    private val visualComponentsAdapter = VisualComponentsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        visualComponentsAdapter.textFieldCellDelegate = object : TextFieldCellDelegate {
            override fun onTextChanged(newValue: String, model: TextFieldCellModel) {
                viewModel.obtainEvent(PromoEvent.CodeChanges(newValue = newValue))
            }
        }

        visualComponentsAdapter.buttonCellDelegate = object : ButtonCellDelegate {
            override fun onButtonClick(model: ButtonCellModel) {
                viewModel.obtainEvent(PromoEvent.ApplyCode)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_promo, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        itemsView.adapter = visualComponentsAdapter
        itemsView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        viewModel.viewStates().observe(viewLifecycleOwner, Observer { bindViewState(it) })
        viewModel.viewEffects().observe(viewLifecycleOwner, Observer { bindViewAction(it) })
        viewModel.obtainEvent(PromoEvent.RenderScreen)
    }

    private fun bindViewState(viewState: PromoViewState) {
        visualComponentsAdapter.setItems(viewState.renderData)
    }

    private fun bindViewAction(viewAction: PromoAction) {
        when (viewAction) {
            is PromoAction.ApplyResult -> {
                setNavigationResult(PROMO_RESULT_KEY, true)
                activity?.onBackPressed()
            }

            is PromoAction.ShowError -> {
                Toast.makeText(context, getString(viewAction.message), Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        const val PROMO_RESULT_KEY = "promoResultKey"
    }
}