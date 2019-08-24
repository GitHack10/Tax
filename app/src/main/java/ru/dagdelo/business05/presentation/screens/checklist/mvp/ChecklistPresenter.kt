package ru.dagdelo.business05.presentation.screens.checklist.mvp

import com.arellomobile.mvp.InjectViewState
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import ru.dagdelo.business05.R
import ru.dagdelo.business05.data.global.netwotk.utils.NetworkChecking
import ru.dagdelo.business05.domain.checklist.CheckListInteractor
import ru.dagdelo.business05.presentation.global.AndroidResourceManager
import ru.dagdelo.business05.presentation.global.base.BasePresenter
import ru.dagdelo.business05.presentation.global.utils.ErrorHandler
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class ChecklistPresenter @Inject constructor(
    private val appRouter: Router,
    private val interactor: CheckListInteractor,
    private val resourceManager: AndroidResourceManager,
    private val errorHandler: ErrorHandler,
    private val networkChecking: NetworkChecking
): BasePresenter<ChecklistView>(appRouter) {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        getCheckList()
    }

    fun getCheckList() {
        subscription += interactor.getCheckList()
            .doOnSubscribe {
                viewState.showProgress(true)
                viewState.showContentLayout(false)
            }
            .doAfterTerminate { viewState.showProgress(false) }
            .subscribeBy(
                onSuccess = {
                    if (it.isNullOrEmpty()) viewState.showEmptyList(true)
                    else {
                        viewState.showEmptyList(false)
                        viewState.showContentLayout(true)
                        viewState.showCheckList(it)
                    }
                },
                onError = {
                    if (networkChecking.hasConnection()) {
                        errorHandler.proceed(it) { error ->
                            viewState.showError(
                                if (!error.errors.isNullOrEmpty()) error.errors.first().message
                                else error.message
                            )
                        }
                    } else viewState.showError(resourceManager.getString(R.string.checkYourInternetConnection))
                }
            )
    }
}