package ru.dagdelo.business05.data.sendcomplaint

import io.reactivex.Completable
import ru.dagdelo.business05.data.global.netwotk.ApiDagDelo
import ru.dagdelo.business05.domain.global.common.io
import ru.dagdelo.business05.domain.global.models.Complaint
import ru.dagdelo.business05.domain.global.repositories.SendComplaintRepository
import javax.inject.Inject

class SendComplaintRepositoryImpl @Inject constructor(
    private val apiDagDelo: ApiDagDelo
) : SendComplaintRepository {

    override fun sendComplaint(complaint: Complaint): Completable =
        apiDagDelo.sendComplaint(
            problemDesc = complaint.problemDesc,
            address = complaint.address,
            date = complaint.date
        )
            .subscribeOn(io)
}