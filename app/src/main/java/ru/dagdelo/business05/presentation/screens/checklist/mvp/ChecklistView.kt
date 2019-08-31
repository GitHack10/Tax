package ru.dagdelo.business05.presentation.screens.checklist.mvp

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import ru.dagdelo.business05.domain.global.models.CheckInfo
import java.util.ArrayList

@StateStrategyType(AddToEndSingleStrategy::class)
interface ChecklistView : MvpView {
    fun showCheckList(checkList: List<CheckInfo>)
    fun showEmptyList(show: Boolean)
    fun showContentLayout(show: Boolean)
    fun showNoNetworkLayout(show: Boolean)

    fun showProgress(show: Boolean)
    fun showPaginationProgress(show: Boolean)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showError(message: String)

    fun hideRefreshingProgress()
    fun showRefreshedList(newList: List<CheckInfo>)
    fun showFilteredList(filteredList: List<CheckInfo>)
}