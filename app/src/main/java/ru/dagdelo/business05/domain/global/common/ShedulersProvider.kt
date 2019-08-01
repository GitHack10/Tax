package ru.dagdelo.business05.domain.global.common

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/** Created by Kamil Abdulatipov on 24.04.2019. */

val io: Scheduler
    get() = Schedulers.io()

val ui: Scheduler
    get() = AndroidSchedulers.mainThread()