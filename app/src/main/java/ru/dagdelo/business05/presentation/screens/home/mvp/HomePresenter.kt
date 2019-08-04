package ru.dagdelo.business05.presentation.screens.home.mvp

import com.arellomobile.mvp.InjectViewState
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import ru.dagdelo.business05.domain.global.common.io
import ru.dagdelo.business05.domain.global.common.ui
import ru.dagdelo.business05.domain.global.models.Check
import ru.dagdelo.business05.domain.global.models.CheckInfoEntity
import ru.dagdelo.business05.domain.home.HomeInteractor
import ru.dagdelo.business05.presentation.global.Screens
import ru.dagdelo.business05.presentation.global.base.BasePresenter
import ru.dagdelo.business05.presentation.global.utils.ErrorHandler
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class HomePresenter @Inject constructor(
    private val appRouter: Router,
    private val interactor: HomeInteractor,
    private val errorHandler: ErrorHandler
) : BasePresenter<HomeView>(appRouter) {

    private var check: Check? = null

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
    }

    fun onQrScannerClicked() {
        appRouter.navigateTo(Screens.QrScreen())
    }

    fun prepareCheck(check: Check) {
        viewState.showProgress(true)

//        check = Check(fd, fpd, fn, date, n, sum)
        check.convertTimeSendFormat()
        subscription += interactor.prepareCheck(check)
            .subscribeBy(
                onComplete = {
                    viewState.showSuccess("Чек зарегистрирован!")
                    viewState.showProgress(false)
                },
                onError = {
                    errorHandler.proceed(it) { msg -> viewState.showError(msg) }
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
            check!!.convertTime()
            interactor.clearQrString()
            viewState.showScannedData(check)
        }
    }

    private fun insertCheckInDb(check: CheckInfoEntity) {
        subscription += interactor.insertCheckInDB(check.document.receipt)
            .subscribeOn(io)
            .observeOn(ui)
            .subscribe({
                viewState.showToast("Сохранено")
            }, {
                viewState.showError("Не удалось сохранить")
            })
    }

    override fun onDestroy() {
        super.onDestroy()
        interactor.clearQrString()
    }
}
