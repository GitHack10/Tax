package com.example.needtechnology.presentation.screens.profile.mvp

import com.arellomobile.mvp.MvpView
import com.example.needtechnology.domain.global.UserInfo

interface ProfileView : MvpView {
    fun showUserInfo(userInfo: UserInfo)
}