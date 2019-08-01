package ru.dagdelo.business05.presentation.global.base

import com.arellomobile.mvp.MvpPresenter
import com.arellomobile.mvp.MvpView
import io.reactivex.disposables.CompositeDisposable
import ru.terrakok.cicerone.Router

abstract class BasePresenter<T : MvpView>(protected val router: Router? = null) :
    MvpPresenter<T>() {

    protected val subscription = CompositeDisposable()

    override fun onDestroy() {
        subscription.dispose()
        super.onDestroy()
    }

    open fun onBackPressed() {
        router?.exit()
    }
}