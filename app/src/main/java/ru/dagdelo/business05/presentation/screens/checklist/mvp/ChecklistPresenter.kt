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
import ru.dagdelo.business05.presentation.global.utils.PAGINATION_COUNT
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

    // индекс загружаемых данных
    private var page = 1

    // флаг для определения - все ли элементы списка загружены
    var paginationEnd = false
    var isLoading = false

    // флаг для определения первой загрузки
    var isFirstRequest = true

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        getCheckList()
    }

    fun getCheckList() {
        subscription += interactor.getCheckList(page)
            .doOnSubscribe {
                isLoading = true
                if (isFirstRequest) {
                    viewState.showProgress(true)
                    viewState.showContentLayout(false)
                } else if (!paginationEnd) viewState.showPaginationProgress(true)
            }
            .doAfterTerminate {
                isLoading = false
                viewState.showProgress(false)
                viewState.showPaginationProgress(false)
            }
            .subscribeBy(
                onSuccess = {
                    when {
                        page == 1 && it.isNullOrEmpty() -> {
                            viewState.showEmptyList(true)
                            paginationEnd = true
                        }
                        else -> {
                            if (it.size < PAGINATION_COUNT) paginationEnd = true
                            viewState.showEmptyList(false)
                            viewState.showContentLayout(true)
                            viewState.showCheckList(it)

                            page += 1

                            // задаем флаг при первой загрузке списка чеков
                            isFirstRequest = false
                        }
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