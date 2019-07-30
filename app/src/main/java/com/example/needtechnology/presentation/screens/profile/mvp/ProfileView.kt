package com.example.needtechnology.presentation.screens.profile.mvp

import com.arellomobile.mvp.MvpView
import com.example.needtechnology.domain.global.models.User
import com.example.needtechnology.domain.global.models.UserInfo

interface ProfileView : MvpView {
    fun showUserInfo(userInfo: User)
    fun showSaveProgress(show: Boolean)
    fun showLoadProgress(show: Boolean)
    fun showError(message: String)
}