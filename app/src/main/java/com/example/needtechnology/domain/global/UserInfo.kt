package com.example.needtechnology.domain.global

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserInfo(
    var name: String? = null,
    var phone: String? = null,
    var email: String? = null,
    var birth: String? = null,
    var gender: String? = null
): Parcelable