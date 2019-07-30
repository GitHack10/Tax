package com.example.needtechnology.presentation.screens.auth.signin.mvp

import com.arellomobile.mvp.InjectViewState
import com.example.needtechnology.di.global.nameds.SIGN_IN_FLOW
import com.example.needtechnology.domain.auth.AuthInteractor
import com.example.needtechnology.domain.global.models.UserInfo
import com.example.needtechnology.presentation.global.Screens
import com.example.needtechnology.presentation.global.base.BasePresenter
import com.example.needtechnology.presentation.global.navigation.FlowRouter
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject
import javax.inject.Named

@InjectViewState
class SignInPresenter @Inject constructor(
    @Named(SIGN_IN_FLOW) private val flowRouter: FlowRouter,
    private val interactor: AuthInteractor,
    private var userInfo: UserInfo
) : BasePresenter<SignInView>(flowRouter) {

    fun signInClicked(email: String, password: String) {
        viewState.showProgress(true)
        subscription += interactor.signInRequest(email, password)
            .subscribeBy(
                onSuccess = {
                    if (it.status) {
                        interactor.saveToken(it.token)
                        interactor.setIsLogin(true)
//                    interactor.savePhone(phone, maskedPhone)
                        navigateToHome()
                    }
                    viewState.showProgress(false)
                },
                onError = {
                    if (it.message == "HTTP 401 Unauthorized") {
                        viewState.showAuthError("Неверный Email или Пароль")
                    } else {
                        viewState.showError("Проверьте ваше подключение к интернету")
                    }
                    viewState.showProgress(false)
                    val message = it.message
                }
            )
    }

    private fun navigateToHome() {
        flowRouter.newRootFlow(Screens.MainFlow())
    }

    fun onRegistrationClicked() {
        flowRouter.navigateToFlow(Screens.SignUp())
    }
}
