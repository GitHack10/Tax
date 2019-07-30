package com.example.needtechnology.presentation.screens.checklist.mvp

import com.arellomobile.mvp.MvpView
import com.example.needtechnology.domain.global.models.CheckInfoEntity

interface ChecklistView : MvpView {
    fun showCheckList(checkList: List<CheckInfoEntity>)
    fun showEmptyList(message: String)
    fun showProgress(show: Boolean)
    fun showError(message: String)
}