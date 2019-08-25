package ru.dagdelo.business05.presentation.screens.sendcomplaint.mvp

import com.arellomobile.mvp.InjectViewState
import ru.dagdelo.business05.di.global.nameds.SEND_COMPLAINT_FLOW
import ru.dagdelo.business05.presentation.global.base.BasePresenter
import ru.dagdelo.business05.presentation.global.base.FlowPresenter
import ru.dagdelo.business05.presentation.global.navigation.FlowRouter
import ru.terrakok.cicerone.Router
import javax.inject.Inject
import javax.inject.Named

@InjectViewState
class SendComplaintPresenter @Inject constructor(
    @Named(SEND_COMPLAINT_FLOW) private val flowRouter: FlowRouter
) : FlowPresenter<SendComplaintView>(flowRouter) {


}