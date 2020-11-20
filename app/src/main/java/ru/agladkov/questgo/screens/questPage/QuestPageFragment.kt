package ru.agladkov.questgo.screens.questPage

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_quest_list.*
import kotlinx.android.synthetic.main.fragment_quest_page.*
import kotlinx.android.synthetic.main.fragment_quest_page.itemsView
import ru.agladkov.questgo.R
import ru.agladkov.questgo.common.VisualComponentsAdapter
import ru.agladkov.questgo.helpers.injectViewModel
import ru.agladkov.questgo.screens.questList.QuestListViewModel
import javax.inject.Inject

class QuestPageFragment: Fragment(R.layout.fragment_quest_page) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var viewModel: QuestPageViewModel

    private val visualComponentsAdapter = VisualComponentsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = injectViewModel(factory = viewModelFactory)

        viewModel.setParams(pageId = arguments?.getInt(PAGE_ID), questId = arguments?.getInt(QUEST_ID))
        viewModel.isLoading.observe(viewLifecycleOwner, Observer {
            pageLoaderView.visibility = if (it) View.VISIBLE else View.GONE
            itemsView.visibility = if (it) View.GONE else View.VISIBLE
            answerHolderCardView.visibility = if (it) View.GONE else View.VISIBLE
        })
        viewModel.items.observe(viewLifecycleOwner, Observer { response ->
            if (response.isNotEmpty()) {
                visualComponentsAdapter.setItems(response)
            }
        })

        itemsView.adapter = visualComponentsAdapter
        itemsView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        answerTextFieldView.addTextChangedListener {
            Toast.makeText(context, "${it?.toString()}", Toast.LENGTH_SHORT).show()
        }

        viewModel.fetchPage()
    }

    companion object {
        const val QUEST_ID = "questIDKey"
        const val PAGE_ID = "pageIDKey"
    }
}