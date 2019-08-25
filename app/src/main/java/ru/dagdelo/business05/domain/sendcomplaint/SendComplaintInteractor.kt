package ru.dagdelo.business05.domain.sendcomplaint

import io.reactivex.Completable
import ru.dagdelo.business05.data.global.local.PreferenceStorage
import ru.dagdelo.business05.data.sendcomplaint.SendComplaintRepositoryImpl
import ru.dagdelo.business05.domain.global.common.ui
import ru.dagdelo.business05.domain.global.models.Complaint
import javax.inject.Inject

class SendComplaintInteractor @Inject constructor(
    private val sendComplaintRepositoryImpl: SendComplaintRepositoryImpl,
    private val prefs: PreferenceStorage
) {

    fun sendComplaint(complaint: Complaint): Completable =
        sendComplaintRepositoryImpl.sendComplaint(complaint)
            .observeOn(ui)
}