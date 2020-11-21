package ru.agladkov.questgo.screens.questPage

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_quest_page.*
import ru.agladkov.questgo.R
import ru.agladkov.questgo.common.VisualComponentsAdapter
import ru.agladkov.questgo.common.models.ButtonCellModel
import ru.agladkov.questgo.common.viewholders.ButtonCellDelegate
import ru.agladkov.questgo.helpers.injectViewModel
import javax.inject.Inject

class QuestPageFragment : Fragment(R.layout.fragment_quest_page) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var viewModel: QuestPageViewModel

    private val visualComponentsAdapter = VisualComponentsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)

        visualComponentsAdapter.buttonCellDelegate = object: ButtonCellDelegate {
            override fun onButtonClick(model: ButtonCellModel) {
                if (model.title != "Продолжить") {
                    routeToNextPage()
                } else {
                    routeToThankYouPage()
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = injectViewModel(factory = viewModelFactory)

        viewModel.setParams(
            pageId = arguments?.getInt(PAGE_ID),
            questId = arguments?.getInt(QUEST_ID)
        )
        observeViewModel()

        itemsView.adapter = visualComponentsAdapter
        itemsView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        sendButtonView.setOnClickListener {
            viewModel.checkAnswer(answerTextFieldView.text.toString())
        }

        answerTextFieldView.setOnEditorActionListener { _, _, _ ->
            viewModel.checkAnswer(answerTextFieldView.text.toString())
            true
        }

        viewModel.fetchPage()
    }

    private fun observeViewModel() {
        viewModel.isLoading.observe(viewLifecycleOwner, Observer {
            pageLoaderView.visibility = if (it) View.VISIBLE else View.GONE
            itemsView.visibility = if (it) View.GONE else View.VISIBLE
            answerHolderCardView.visibility = if (it) View.GONE else View.VISIBLE
        })

        viewModel.errorMessage.observe(viewLifecycleOwner, Observer { error ->
            when (error) {
                is QuestPageError.RequestException -> {

                }
                is QuestPageError.WrongAnswerException -> {
                    findNavController().navigate(R.id.action_questPageFragment_to_errorFragment)
                }
            }
        })

        viewModel.successAction.observe(viewLifecycleOwner, Observer { response ->
            if (response) {
                answerTextFieldView.setText("")
                answerHolderCardView.visibility = View.GONE
                val imm: InputMethodManager? =
                    context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as? InputMethodManager
                imm?.hideSoftInputFromWindow(view?.windowToken, 0)

                itemsView.smoothScrollToPosition(0)
            }
        })

        viewModel.items.observe(viewLifecycleOwner, Observer { response ->
            if (response.isNotEmpty()) {
                visualComponentsAdapter.setItems(response)
            }
        })
    }

    private fun routeToNextPage() {
        findNavController().navigate(
            R.id.action_questPageFragment_self,
            bundleOf(
                QUEST_ID to arguments?.getInt(QUEST_ID),
                PAGE_ID to (arguments?.getInt(PAGE_ID) ?: 0) + 1
            )
        )
    }

    private fun routeToThankYouPage() {
        findNavController().navigate(
            R.id.action_questPageFragment_to_thankYouPageFragment
        )
    }

    companion object {
        const val QUEST_ID = "questIDKey"
        const val PAGE_ID = "pageIDKey"
    }
}