package ru.dagdelo.business05.domain.global.repositories

import io.reactivex.Completable
import ru.dagdelo.business05.domain.global.models.Complaint

interface SendComplaintRepository {
    fun sendComplaint(complaint: Complaint): Completable
}