package com.example.needtechnology.presentation.screens.auth.signin.mvp

import com.arellomobile.mvp.MvpView

interface SignInView : MvpView {
    fun showError(message: String)
    fun showProgress(show: Boolean)
    fun showAuthError(message: String)
}