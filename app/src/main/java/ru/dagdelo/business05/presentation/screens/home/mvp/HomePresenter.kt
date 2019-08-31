package ru.dagdelo.business05.presentation.screens.home.mvp

import com.arellomobile.mvp.InjectViewState
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import ru.dagdelo.business05.R
import ru.dagdelo.business05.domain.global.models.Check
import ru.dagdelo.business05.domain.home.HomeInteractor
import ru.dagdelo.business05.presentation.global.AndroidResourceManager
import ru.dagdelo.business05.presentation.global.Screens
import ru.dagdelo.business05.presentation.global.base.BasePresenter
import ru.dagdelo.business05.presentation.global.utils.ErrorHandler
import ru.dagdelo.business05.presentation.global.utils.toHumanDate
import ru.dagdelo.business05.presentation.global.utils.toSendFormatCheckDate
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class HomePresenter @Inject constructor(
    private val appRouter: Router,
    private val interactor: HomeInteractor,
    private val resourceManager: AndroidResourceManager,
    private val errorHandler: ErrorHandler
) : BasePresenter<HomeView>(appRouter) {

    private var check: Check? = null

    fun onQrScannerClicked() {
        appRouter.navigateTo(Screens.QrScreen())
    }

    fun prepareCheck(check: Check) {
        viewState.showProgress(true)

        check.date = toSendFormatCheckDate(check.date)
        subscription += interactor.prepareCheck(check)
            .subscribeBy(
                onComplete = {
                    viewState.showSuccess(
                        resourceManager.getString(R.string.home_success_alert_title),
                        resourceManager.getString(R.string.home_success_alert_desc),
                        resourceManager.getString(R.string.home_success_alert_button)
                    )
                    viewState.showProgress(false)
                },
                onError = {
                    errorHandler.proceed(it) { error ->
                        viewState.showError(
                            if (!error.errors.isNullOrEmpty()) error.errors.first().message
                            else error.message
                        )
                    }
                    viewState.showProgress(false)
                }
            )
    }

    fun getScannedString() {
        val qrScannedString = interactor.getQrString()
        if (qrScannedString.isNotEmpty()) {
            var sum = ""
            var fd = ""
            var fn = ""
            var fpd = ""
            var date = ""
            var type = ""
            val data = qrScannedString.split("&")
            for (string in data) {
                when ("${string[0]}${string[1]}") {
                    "s=" -> sum = string.removeRange(0, 2)
                    "fn" -> fn = string.removeRange(0, 3)
                    "i=" -> fd = string.removeRange(0, 2)
                    "fp" -> fpd = string.removeRange(0, 3)
                    "t=" -> date = string.removeRange(0, 2)
                    "n=" -> type = string.removeRange(0, 2)
                }
            }
            check = Check(fd, fpd, fn, date, type.toIntOrNull() ?: 1, sum)
            check?.let { it.date = toHumanDate(check!!.date) }
            interactor.clearQrString()
            viewState.showScannedData(check)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        interactor.clearQrString()
    }

    fun onSendComplaintClicked() = appRouter.navigateTo(Screens.SendComplaintScreen)
}
