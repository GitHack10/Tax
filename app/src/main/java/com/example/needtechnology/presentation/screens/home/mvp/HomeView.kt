package com.example.needtechnology.presentation.screens.home.mvp

import com.arellomobile.mvp.MvpView

interface HomeView : MvpView {
    fun showScannedData(fd: String, fpd: String, fn: String)
    fun showScanError(message: String)
    fun showProgress(show: Boolean)
    fun showError(message: String)
    fun showSuccess(message: String)
    fun showToast(message: String)
}