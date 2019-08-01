package ru.dagdelo.business05.presentation.screens.auth.signin.mvp

import com.arellomobile.mvp.InjectViewState
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import ru.dagdelo.business05.di.global.nameds.SIGN_IN_FLOW
import ru.dagdelo.business05.domain.auth.AuthInteractor
import ru.dagdelo.business05.domain.global.models.UserInfo
import ru.dagdelo.business05.presentation.global.Screens
import ru.dagdelo.business05.presentation.global.base.BasePresenter
import ru.dagdelo.business05.presentation.global.navigation.FlowRouter
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
