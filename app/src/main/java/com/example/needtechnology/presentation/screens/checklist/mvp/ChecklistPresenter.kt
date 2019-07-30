package com.example.needtechnology.presentation.screens.checklist.mvp

import com.arellomobile.mvp.InjectViewState
import com.example.needtechnology.domain.checklist.CheckListInteractor
import com.example.needtechnology.presentation.global.base.BasePresenter
import com.example.needtechnology.presentation.global.navigation.AppRouter
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class ChecklistPresenter @Inject constructor(
    private val appRouter: Router,
    private val interactor: CheckListInteractor
): BasePresenter<ChecklistView>(appRouter) {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        getCheckList()
    }

    fun getCheckList() {
        viewState.showProgress(true)
        subscription += interactor.getCheckList()
            .subscribeBy(
                onSuccess = {
                    if (it.isNullOrEmpty()) viewState.showEmptyList("Список пуст")
                    else viewState.showCheckList(it)

                    viewState.showProgress(false)
                },
                onError = {
                    viewState.showError("Проверьте ваше соединение с интернетом")
                    viewState.showProgress(false)
                }
            )
    }

//    private fun getCheckList() {
//        viewState.showProgress(true)
//        subscription += interactor.getAllChecks()
//            .subscribeBy(
//                onNext = { checkList ->
//                    viewState.showCheckList(checkList)
//                    viewState.showProgress(false)
//                },
//                onError = {
//                    viewState.showEmptyList("Список пуст")
//                    viewState.showProgress(false)
//                }
//            )
//    }
}