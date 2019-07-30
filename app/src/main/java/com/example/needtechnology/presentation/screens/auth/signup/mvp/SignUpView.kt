package com.example.needtechnology.presentation.screens.auth.signup.mvp

import com.arellomobile.mvp.MvpView

interface SignUpView : MvpView {
    fun showError(message: String)
    fun showProgress(show: Boolean)
}