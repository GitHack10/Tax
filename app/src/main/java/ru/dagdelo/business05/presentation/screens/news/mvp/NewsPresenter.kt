package ru.dagdelo.business05.presentation.screens.news.mvp

import com.arellomobile.mvp.InjectViewState
import ru.dagdelo.business05.R
import ru.dagdelo.business05.data.global.netwotk.utils.NetworkChecking
import ru.dagdelo.business05.presentation.global.AndroidResourceManager
import ru.dagdelo.business05.presentation.global.base.BasePresenter
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class NewsPresenter @Inject constructor(
    private val appRouter: Router,
    private val networkChecking: NetworkChecking,
    private val resourceManager: AndroidResourceManager
): BasePresenter<NewsView>(appRouter) {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        if (networkChecking.hasConnection()) viewState.showNews()
        else viewState.showError(resourceManager.getString(R.string.checkYourInternetConnection))
    }

    fun retryLoad() {
        if (networkChecking.hasConnection()) viewState.showNews()
        else viewState.showError(resourceManager.getString(R.string.checkYourInternetConnection))
    }
}