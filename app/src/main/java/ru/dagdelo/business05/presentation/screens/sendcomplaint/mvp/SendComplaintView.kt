package ru.dagdelo.business05.presentation.screens.sendcomplaint.mvp

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface SendComplaintView : MvpView {
    fun showProgress(show: Boolean)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showError(message: String)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showComplete(title: String, message: String)
}