package com.example.needtechnology.presentation.screens.news.mvp

import com.arellomobile.mvp.InjectViewState
import com.example.needtechnology.presentation.global.base.BasePresenter
import com.example.needtechnology.presentation.global.navigation.AppRouter
import javax.inject.Inject

@InjectViewState
class NewsPresenter @Inject constructor(
    private val appRouter: AppRouter
): BasePresenter<NewsView>(appRouter) {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
    }
}