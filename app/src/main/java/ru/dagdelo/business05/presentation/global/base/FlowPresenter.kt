package ru.dagdelo.business05.presentation.global.base

import com.arellomobile.mvp.MvpPresenter
import com.arellomobile.mvp.MvpView
import io.reactivex.disposables.CompositeDisposable
import ru.dagdelo.business05.presentation.global.navigation.FlowRouter
import ru.terrakok.cicerone.Router

abstract class FlowPresenter<T : MvpView>(protected val router: FlowRouter? = null) :
    MvpPresenter<T>() {

    protected val subscription = CompositeDisposable()

    override fun onDestroy() {
        subscription.dispose()
        super.onDestroy()
    }

    open fun onBackPressed() {
        router?.exitFlow()
    }
}