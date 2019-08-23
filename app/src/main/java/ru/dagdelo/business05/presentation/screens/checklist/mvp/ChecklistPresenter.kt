package ru.dagdelo.business05.presentation.screens.checklist.mvp

import com.arellomobile.mvp.InjectViewState
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import ru.dagdelo.business05.domain.checklist.CheckListInteractor
import ru.dagdelo.business05.presentation.global.base.BasePresenter
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
}