package com.example.needtechnology.presentation.screens.auth.signup.mvp

import com.arellomobile.mvp.InjectViewState
import com.example.needtechnology.di.global.nameds.SIGN_UP_FLOW
import com.example.needtechnology.domain.auth.AuthInteractor
import com.example.needtechnology.domain.global.models.UserInfo
import com.example.needtechnology.domain.global.models.UserReg
import com.example.needtechnology.presentation.global.AndroidResourceManager
import com.example.needtechnology.presentation.global.Screens
import com.example.needtechnology.presentation.global.base.BasePresenter
import com.example.needtechnology.presentation.global.navigation.FlowRouter
import com.example.needtechnology.presentation.global.utils.ErrorHandler
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject
import javax.inject.Named

@InjectViewState
class SignUpPresenter @Inject constructor(
    @Named(SIGN_UP_FLOW) private val flowRouter: FlowRouter,
    private val interactor: AuthInteractor,
    private var userInfo: UserInfo,
    private val resourceManager: AndroidResourceManager,
    private val errorHandler: ErrorHandler
) : BasePresenter<SignUpView>(flowRouter) {

    fun onDoneClicked(userInfo: UserInfo) {

        viewState.showProgress(true)
        val userReg = UserReg(
            email = userInfo.email ?: "",
            name = userInfo.name ?: "",
            phone = userInfo.phone ?: "",
            password = userInfo.password ?: "",
            birth = userInfo.birth ?: "",
            gender = userInfo.gender ?: 1
        )
        subscription += interactor.registerUser(userReg)
            .subscribeBy(
                onSuccess = {
                    if (it.status) {
                        interactor.setIsLogin(true)
                        interactor.saveToken(it.token)
                        flowRouter.newRootFlow(Screens.MainFlow())
                    } else {
                        viewState.showDataError(
                            "Пользователь с таким E-mail или Номером телефона уже существует"
                        )
                    }
                    viewState.showProgress(false)
                },
                onError = {
                    errorHandler.proceed(it) {
                            msg -> viewState.showError(msg)
                    }
                    viewState.showProgress(false)
                }
            )
    }

    override fun onBackPressed() = flowRouter.exitFlow()
}
