package ru.dagdelo.business05.presentation.screens.home.mvp

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import ru.dagdelo.business05.domain.global.models.Check

@StateStrategyType(AddToEndSingleStrategy::class)
interface HomeView : MvpView {
    fun showScannedData(check: Check?)
    fun showProgress(show: Boolean)
    fun showSuccess(message: String)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showScanError(message: String)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showError(message: String)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showToast(message: String)
}