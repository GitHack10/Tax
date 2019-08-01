package ru.dagdelo.business05.presentation.screens.auth.signup.mvp

import com.arellomobile.mvp.InjectViewState
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
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
