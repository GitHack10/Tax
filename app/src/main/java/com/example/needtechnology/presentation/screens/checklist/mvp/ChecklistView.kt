package com.example.needtechnology.presentation.screens.checklist.mvp

import com.arellomobile.mvp.MvpView
import com.example.needtechnology.domain.global.models.CheckInfo

interface ChecklistView : MvpView {
    fun showCheckList(checkList: List<CheckInfo>)
    fun showEmptyList(message: String)
    fun showProgress(show: Boolean)
    fun showError(message: String)
}