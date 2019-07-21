package com.example.needtechnology.presentation.screens.auth.signin.mvp

import com.arellomobile.mvp.MvpView

interface SignInView : MvpView {
    fun showErrorData(show: Boolean)
}