package ru.dagdelo.business05.presentation.screens.auth.enterphone.mvp

import com.arellomobile.mvp.InjectViewState
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import ru.dagdelo.business05.di.global.nameds.ENTER_PHONE_FLOW
import ru.dagdelo.business05.domain.auth.AuthInteractor
import ru.dagdelo.business05.presentation.global.Screens
import ru.dagdelo.business05.presentation.global.base.BasePresenter
import ru.dagdelo.business05.presentation.global.navigation.FlowRouter
import ru.dagdelo.business05.presentation.global.utils.ErrorHandler
import javax.inject.Inject
import javax.inject.Named

@InjectViewState
class EnterPhonePresenter @Inject constructor(
    @Named(ENTER_PHONE_FLOW) private val flowRouter: FlowRouter,
    private val interactor: AuthInteractor,
    private var errorHandler: ErrorHandler
) : BasePresenter<EnterPhoneView>(flowRouter) {

    fun onNextClicked(maskedPhone: String, phone: String) {
        viewState.showProgress(true)
        subscription += interactor.getSmsCode("7$phone")
            .doOnSubscribe { viewState.showProgress(true) }
            .doAfterTerminate { viewState.showProgress(false) }
            .subscribeBy(
                onComplete = {
                    navigateToEnterCode(maskedPhone, phone)
                },
                onError = { errorHandler.proceed(it) { msg -> viewState.showError(msg) } }
            )
    }

    private fun navigateToEnterCode(maskedPhone: String, phone: String) {
        flowRouter.navigateToFlow(
            Screens.EnterCode(
                maskedPhone = maskedPhone,
                phone = phone
            )
        )
    }
}