package ru.dagdelo.business05.presentation.screens.profile.mvp

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import ru.dagdelo.business05.domain.global.models.User

@StateStrategyType(AddToEndSingleStrategy::class)
interface ProfileView : MvpView {
    fun showUserInfo(userInfo: User)
    fun showSaveProgress(show: Boolean)
    fun showLoadProgress(show: Boolean)
    fun showContentLayout(show: Boolean)
    fun showNoNetworkLayout(show: Boolean)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showError(message: String)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showSaveError(message: String)


}