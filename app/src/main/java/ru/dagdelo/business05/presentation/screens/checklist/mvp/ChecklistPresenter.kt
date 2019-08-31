package ru.dagdelo.business05.presentation.screens.checklist.mvp

import com.arellomobile.mvp.InjectViewState
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import ru.dagdelo.business05.R
import ru.dagdelo.business05.data.global.netwotk.utils.NetworkChecking
import ru.dagdelo.business05.domain.checklist.CheckListInteractor
import ru.dagdelo.business05.domain.global.models.CheckInfo
import ru.dagdelo.business05.presentation.global.AndroidResourceManager
import ru.dagdelo.business05.presentation.global.base.BasePresenter
import ru.dagdelo.business05.presentation.global.utils.ErrorHandler
import ru.dagdelo.business05.presentation.global.utils.PAGINATION_COUNT
import ru.dagdelo.business05.presentation.global.utils.toHumanDate
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
    var page = 1

    // флаг для определения - все ли элементы списка загружены
    var paginationEnd = false
    var isLoading = false

    // флаг для определения первой загрузки
    private var isFirstRequest = true

    private var filterId: Int? = null

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        getCheckList()
    }

    fun getCheckList(refreshing: Boolean = false, selectedFilter: Boolean = false) {
        if (networkChecking.hasConnection()) {
            viewState.showNoNetworkLayout(false)
            subscription += interactor.getCheckList(
                page = if (refreshing || selectedFilter) 1 else page,
                selectedFilter = filterId
            )
                .doOnSubscribe {
                    isLoading = true
                    if (isFirstRequest || selectedFilter) {
                        viewState.showProgress(true)
                        viewState.showContentLayout(false)
                    } else if (!paginationEnd && !refreshing) {
                        viewState.showPaginationProgress(true)
                    } else if (refreshing) {
                        viewState.showContentLayout(false)
                    }
                }
                .doAfterTerminate {
                    isLoading = false
                    viewState.showProgress(false)
                    viewState.showPaginationProgress(false)
                    if (refreshing) {
                        viewState.hideRefreshingProgress()
                    }
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
                                it.forEach { check ->
                                    check.dateCheck.apply {
                                        if (!isNullOrEmpty()) {
                                            check.dateCheck = toHumanDate(this)
                                        }
                                    }
                                }
                                if (refreshing) {
                                    viewState.showRefreshedList(it)
                                } else {
                                    if (selectedFilter) {
                                        viewState.showFilteredList(it)
                                    } else {
                                        viewState.showCheckList(it)
                                    }
                                }
                                viewState.showEmptyList(false)
                                viewState.showContentLayout(true)
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
        } else {
            viewState.showProgress(false)
            viewState.showNoNetworkLayout(true)
        }
    }

    fun onRefreshCheckList() = getCheckList(true)

    fun onTryAgainClicked() = getCheckList()

    fun onFilterSelected(selectedFilter: Int?) {
        filterId = selectedFilter
        getCheckList(selectedFilter = true)
    }
}