package ru.dagdelo.business05.presentation.screens.checklist.mvp

import com.arellomobile.mvp.MvpView
import ru.dagdelo.business05.domain.global.models.CheckInfo

interface ChecklistView : MvpView {
    fun showCheckList(checkList: List<CheckInfo>)
    fun showEmptyList(message: String)
    fun showProgress(show: Boolean)
    fun showError(message: String)
}