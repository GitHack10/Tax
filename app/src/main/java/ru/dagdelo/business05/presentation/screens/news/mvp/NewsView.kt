package ru.dagdelo.business05.presentation.screens.news.mvp

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface NewsView : MvpView {
    fun showNews()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showError(message: String)
}