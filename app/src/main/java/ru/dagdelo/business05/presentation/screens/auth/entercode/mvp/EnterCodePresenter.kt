package ru.dagdelo.business05.presentation.screens.auth.entercode.mvp

import com.arellomobile.mvp.InjectViewState
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import ru.dagdelo.business05.R
import ru.dagdelo.business05.di.global.nameds.ENTER_CODE_FLOW
import ru.dagdelo.business05.domain.auth.AuthInteractor
import ru.dagdelo.business05.presentation.global.AndroidResourceManager
import ru.dagdelo.business05.presentation.global.Screens
import ru.dagdelo.business05.presentation.global.base.BasePresenter
import ru.dagdelo.business05.presentation.global.base.FlowPresenter
import ru.dagdelo.business05.presentation.global.navigation.FlowRouter
import ru.dagdelo.business05.presentation.global.utils.ErrorHandler
import javax.inject.Inject
import javax.inject.Named

@InjectViewState
class EnterCodePresenter @Inject constructor(
    @Named(ENTER_CODE_FLOW) private val flowRouter: FlowRouter,
    private val interactor: AuthInteractor,
    private val resourceManager: AndroidResourceManager,
    private var errorHandler: ErrorHandler
) : FlowPresenter<EnterCodeView>(flowRouter) {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.startTimer()
    }

    fun sendSmsClicked(phone: String, smsCode: String, maskedPhone: String) {
        subscription += interactor.auth("7$phone", smsCode)
            .doOnSubscribe { viewState.showProgress(true) }
            .doAfterTerminate { viewState.showProgress(false) }
            .subscribeBy(
                onSuccess = {
                    if (it.success) {
                        interactor.setIsLogin(isLogin = true, token = it.token)
                        flowRouter.newRootFlow(Screens.MainFlow)
                    } else flowRouter.replaceFlow(Screens.SignUp(maskedPhone))
                },
                onError = {
                    errorHandler.proceed(it) { error ->
                        if (error.message == "AUTH_ERROR") {
                            viewState.showAuthError(
                                if (!error.errors.isNullOrEmpty()) error.errors.first().message
                                else error.message
                            )
                        } else viewState.showError(
                            if (!error.errors.isNullOrEmpty()) error.errors.first().message
                            else error.message
                        )
                    }
                }
            )
    }

    fun retrySmsRequest(phone: String) {
        subscription += interactor.getSmsCode(phone)
            .doOnSubscribe { viewState.showRetrySmsProgress(true) }
            .doAfterTerminate { viewState.showRetrySmsProgress(false) }
            .subscribeBy(
                onComplete = { viewState.startTimer() },
                onError = {
                    errorHandler.proceed(it) { error ->
                        viewState.showError(
                            if (!error.errors.isNullOrEmpty()) error.errors.first().message
                            else error.message
                        )
                    }
                }
            )
    }

    fun wrongPhoneClicked() = onBackPressed()
}