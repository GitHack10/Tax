package com.example.needtechnology.presentation.screens.home.mvp

import android.annotation.SuppressLint
import com.arellomobile.mvp.InjectViewState
import com.example.needtechnology.domain.global.common.io
import com.example.needtechnology.domain.global.common.ui
import com.example.needtechnology.domain.global.models.Check
import com.example.needtechnology.domain.global.models.CheckInfoEntity
import com.example.needtechnology.domain.home.HomeInteractor
import com.example.needtechnology.presentation.global.Screens
import com.example.needtechnology.presentation.global.base.BasePresenter
import com.example.needtechnology.presentation.global.utils.ErrorHandler
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import ru.terrakok.cicerone.Router
import java.text.SimpleDateFormat
import java.util.*
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

    fun prepareCheck(fpd: String, fd: String, fn: String) {
        viewState.showProgress(true)

        check = Check(fd, fpd, fn)
        subscription += interactor.prepareCheck(check!!)
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

    fun getScannedString(qrScannedString: String) {
        var sum = ""
        var fd = ""
        var fn = ""
        var fpd = ""
        if (qrScannedString.contains("&") && qrScannedString.contains("fp=")) {
            val data = qrScannedString.split("&")
            for (string in data) {
                when ("${string[0]}${string[1]}") {
                    "s=" -> sum = string.removeRange(0, 2)
                    "fn" -> fn = string.removeRange(0, 3)
                    "i=" -> fd = string.removeRange(0, 2)
                    "fp" -> fpd = string.removeRange(0, 3)
                }
            }
            check = Check(fd, fpd, fn)
            viewState.showScannedData(fd, fpd, fn)
        } else viewState.showScanError("Не удалось просканировать, убедитесь что это QR-code чека!")
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

    @SuppressLint("SimpleDateFormat")
    private fun convertTime(date: String): String {
        val calendar = Calendar.getInstance()
        calendar.time = SimpleDateFormat("yyyyMMddTkkmmss").parse(date)

        return SimpleDateFormat("dd.MM.yyyy' 'kk:mm").format(calendar.time)
    }
}
