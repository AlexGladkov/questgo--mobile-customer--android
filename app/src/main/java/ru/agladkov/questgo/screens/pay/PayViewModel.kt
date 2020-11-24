package ru.agladkov.questgo.screens.pay

import android.widget.Button
import com.android.billingclient.api.BillingClient
import ru.agladkov.questgo.base.BaseViewModel
import ru.agladkov.questgo.common.models.*
import ru.agladkov.questgo.data.features.configuration.UserConfigurationLocalDataSource
import ru.agladkov.questgo.screens.pay.models.PayAction
import ru.agladkov.questgo.screens.pay.models.PayEvent
import ru.agladkov.questgo.screens.pay.models.PayViewState
import javax.inject.Inject

class PayViewModel @Inject constructor(
    private val localDataSource: UserConfigurationLocalDataSource
) : BaseViewModel<PayViewState, PayAction, PayEvent>() {

    init {
        viewState = PayViewState()
    }

    override fun obtainEvent(viewEvent: PayEvent) {
        when (viewEvent) {
            is PayEvent.BuyQuest -> performBuy(billingClient = viewEvent.billingClient)
            is PayEvent.ScreenShown -> renderScreen()
        }
    }

    private fun renderScreen() {
        val items = ArrayList<ListItem>().apply {
            this += HeaderCellModel(value = "Конец ознакомительного фрагмента")
            this += TextCellModel(value = "Вы прошли ознакомительный участок квеста. Дальнейшее прохождение " +
                    "будет доступно после оплаты.")
            this += ButtonCellModel(title = "Купить квест")
            this += TextButtonCellModel(title = "Применить промо-код")
        }

        viewState = viewState.copy(
            renderItems = items
        )
    }

    private fun performBuy(billingClient: BillingClient) {

    }
}