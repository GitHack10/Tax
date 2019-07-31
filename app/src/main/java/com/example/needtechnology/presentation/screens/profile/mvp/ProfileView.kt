package com.example.needtechnology.presentation.screens.profile.mvp

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.example.needtechnology.domain.global.models.User
import com.example.needtechnology.domain.global.models.UserInfo

@StateStrategyType(AddToEndSingleStrategy::class)
interface ProfileView : MvpView {
    fun showUserInfo(userInfo: User)
    fun showSaveProgress(show: Boolean)
    fun showLoadProgress(show: Boolean)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showError(message: String)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showSaveError(message: String)
}