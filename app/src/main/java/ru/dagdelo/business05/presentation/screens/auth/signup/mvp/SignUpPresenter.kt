package ru.dagdelo.business05.presentation.screens.auth.signup.mvp

import com.arellomobile.mvp.InjectViewState
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import ru.dagdelo.business05.R
import ru.dagdelo.business05.di.global.nameds.SIGN_UP_FLOW
import ru.dagdelo.business05.domain.auth.AuthInteractor
import ru.dagdelo.business05.domain.global.models.UserInfo
import ru.dagdelo.business05.domain.global.models.UserReg
import ru.dagdelo.business05.presentation.global.AndroidResourceManager
import ru.dagdelo.business05.presentation.global.Screens
import ru.dagdelo.business05.presentation.global.base.BasePresenter
import ru.dagdelo.business05.presentation.global.navigation.FlowRouter
import ru.dagdelo.business05.presentation.global.utils.ErrorHandler
import javax.inject.Inject
import javax.inject.Named

@InjectViewState
class SignUpPresenter @Inject constructor(
    @Named(SIGN_UP_FLOW) private val flowRouter: FlowRouter,
    private val interactor: AuthInteractor,
    private val resourceManager: AndroidResourceManager,
    private val errorHandler: ErrorHandler
) : BasePresenter<SignUpView>(flowRouter) {

    fun onDoneClicked(userReg: UserReg) {

        subscription += interactor.registerUser(userReg)
            .doOnSubscribe { viewState.showProgress(true) }
            .doAfterTerminate { viewState.showProgress(false) }
            .subscribeBy(
                onSuccess = {
                    if (it.success) {
                        interactor.setIsLogin(isLogin = true, token = it.token)
                        flowRouter.newRootFlow(Screens.MainFlow)
                    } else {
                        viewState.showDataError(resourceManager.getString(R.string.error_unknown))
                    }
                },
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

    override fun onBackPressed() = flowRouter.exitFlow()
}