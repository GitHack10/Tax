package ru.dagdelo.business05.presentation.screens.sendcomplaint.mvp

import com.arellomobile.mvp.InjectViewState
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import ru.dagdelo.business05.R
import ru.dagdelo.business05.di.global.nameds.SEND_COMPLAINT_FLOW
import ru.dagdelo.business05.domain.global.models.Complaint
import ru.dagdelo.business05.domain.sendcomplaint.SendComplaintInteractor
import ru.dagdelo.business05.presentation.global.AndroidResourceManager
import ru.dagdelo.business05.presentation.global.base.FlowPresenter
import ru.dagdelo.business05.presentation.global.navigation.FlowRouter
import ru.dagdelo.business05.presentation.global.utils.ErrorHandler
import javax.inject.Inject
import javax.inject.Named

@InjectViewState
class SendComplaintPresenter @Inject constructor(
    @Named(SEND_COMPLAINT_FLOW) private val flowRouter: FlowRouter,
    private val interactor: SendComplaintInteractor,
    private val resourceManager: AndroidResourceManager,
    private val errorHandler: ErrorHandler
) : FlowPresenter<SendComplaintView>(flowRouter) {

    fun onSendComplaintClicked(complaint: Complaint) {
        subscription += interactor.sendComplaint(complaint)
            .doOnSubscribe { viewState.showProgress(true) }
            .doAfterTerminate { viewState.showProgress(false) }
            .subscribeBy(
                onComplete = {
                    viewState.showComplete(
                        title = resourceManager.getString(R.string.send_complaint_title_complete),
                        message = resourceManager.getString(R.string.send_complaint_msg_complete)
                    )
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
}