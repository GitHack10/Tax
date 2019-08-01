package ru.dagdelo.business05.presentation.screens.news.mvp

import com.arellomobile.mvp.InjectViewState
import ru.dagdelo.business05.presentation.global.base.BasePresenter
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class NewsPresenter @Inject constructor(
    private val appRouter: Router
): BasePresenter<NewsView>(appRouter) {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
    }
}