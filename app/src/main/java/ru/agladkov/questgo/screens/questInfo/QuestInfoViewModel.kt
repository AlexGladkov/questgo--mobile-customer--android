package ru.agladkov.questgo.screens.questInfo

import android.os.Handler
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.android.billingclient.api.*
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.agladkov.questgo.base.BaseViewModel
import ru.agladkov.questgo.common.models.ButtonCellModel
import ru.agladkov.questgo.common.models.ListItem
import ru.agladkov.questgo.data.features.configuration.UserConfigurationLocalDataSource
import ru.agladkov.questgo.screens.questInfo.models.QuestInfoAction
import ru.agladkov.questgo.screens.questInfo.models.QuestInfoEvent
import ru.agladkov.questgo.screens.questInfo.models.QuestInfoViewState
import ru.agladkov.questgo.screens.questList.adapter.QuestCellModel
import javax.inject.Inject

class QuestInfoViewModel @Inject constructor(
    private val localDataSource: UserConfigurationLocalDataSource
) : BaseViewModel<QuestInfoViewState, QuestInfoAction, QuestInfoEvent>() {

    private val compositeDisposable = CompositeDisposable()
    private var questId = -1
    private val userConfiguration = localDataSource.readConfiguration()

    init {
        viewState = QuestInfoViewState(isBuying = false)
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

    override fun obtainEvent(viewEvent: QuestInfoEvent) {
        when (viewEvent) {
            is QuestInfoEvent.BuyQuest -> buyQuest()
            is QuestInfoEvent.StartBillingConnection -> showDefaultData(
                questCellModel = viewEvent.questCellModel,
                billingClient = viewEvent.billingClient
            )
        }
    }

    private var maxAttemptsToReconnect = 3
    private var currentAttempt = 0
    private fun showDefaultData(questCellModel: QuestCellModel?, billingClient: BillingClient) {
        viewState = viewState.copy(
            isLoading = true
        )

        if (questCellModel == null) {
            // Fallback to error
            return
        }

        billingClient.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(billingResult: BillingResult) {
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                    currentAttempt = 0

                    val skuList = ArrayList<String>()
                    skuList.add("ru.quest.once")
                    val params = SkuDetailsParams.newBuilder()
                    params.setSkusList(skuList).setType(BillingClient.SkuType.INAPP)

                    viewModelScope.launch {
                        val skuDetailsResult = withContext(Dispatchers.IO) {
                            billingClient.querySkuDetails(params.build())
                        }

                        val questSkuDetails = skuDetailsResult.skuDetailsList?.firstOrNull()

                        questId = questCellModel.questId
                        val items = ArrayList<ListItem>().apply {
                            addAll(questCellModel.description ?: emptyList())
                            add(ButtonCellModel("Купить за ${questSkuDetails?.price.orEmpty()}"))
                        }

                        viewState = viewState.copy(
                            isLoading = false,
                            visualItems = items
                        )
                    }
                } else {
                    Log.e("TAG", "Billing response ${billingResult.responseCode}")
                }
            }

            override fun onBillingServiceDisconnected() {
                currentAttempt += 1
                if (currentAttempt > maxAttemptsToReconnect) {
                    return
                }

                Handler().postDelayed({
                    obtainEvent(
                        viewEvent = QuestInfoEvent.StartBillingConnection(
                            billingClient = billingClient,
                            questCellModel = questCellModel
                        )
                    )
                }, 5000)
            }
        })
    }

    private fun buyQuest() {
        if (questId >= 0) {
            localDataSource.updateConfiguration(
                userConfiguration.copy(
                    currentUserPage = 0,
                    currentQuestId = questId
                )
            )

            viewAction = QuestInfoAction.OpenQuest(questId = questId, questPage = 0)
        } else {
            // Fallback to error
        }
    }
}