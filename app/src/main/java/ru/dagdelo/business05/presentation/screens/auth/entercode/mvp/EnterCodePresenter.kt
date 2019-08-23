package ru.dagdelo.business05.presentation.screens.auth.entercode.mvp

import com.arellomobile.mvp.InjectViewState
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import ru.dagdelo.business05.di.global.nameds.ENTER_CODE_FLOW
import ru.dagdelo.business05.domain.auth.AuthInteractor
import ru.dagdelo.business05.presentation.global.Screens
import ru.dagdelo.business05.presentation.global.base.BasePresenter
import ru.dagdelo.business05.presentation.global.navigation.FlowRouter
import ru.dagdelo.business05.presentation.global.utils.ErrorHandler
import javax.inject.Inject
import javax.inject.Named

@InjectViewState
class EnterCodePresenter @Inject constructor(
    @Named(ENTER_CODE_FLOW) private val flowRouter: FlowRouter,
    private val interactor: AuthInteractor,
    private var errorHandler: ErrorHandler
) : BasePresenter<EnterCodeView>(flowRouter) {

    fun sendSmsClicked(phone: String, smsCode: String, maskedPhone: String) {
        subscription += interactor.auth(phone, smsCode)
            .doOnSubscribe { viewState.showProgress(true) }
            .doAfterTerminate { viewState.showProgress(false) }
            .subscribeBy(
                onSuccess = {
                    if (it.success) {
                        interactor.setIsLogin(isLogin = true, token = it.token)
                        flowRouter.newRootFlow(Screens.Home)
                    } else flowRouter.replaceFlow(Screens.SignUp(maskedPhone))
                },
                onError = { errorHandler.proceed(it) { msg -> viewState.showError(msg) } }
            )
    }

    fun wrongPhoneClicked() = flowRouter.exitFlow()
}