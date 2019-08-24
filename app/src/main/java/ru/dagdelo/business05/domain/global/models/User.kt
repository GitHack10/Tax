package ru.dagdelo.business05.domain.global.models

import android.annotation.SuppressLint
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.text.SimpleDateFormat
import java.util.*

@Parcelize
data class User(
    @SerializedName("full_name") var fullName: String,
    @SerializedName("email") var email: String,
    @SerializedName("phone") val phone: String,
    @SerializedName("date") var birth: String,
    @SerializedName("gender") val gender: String
): Parcelable {

    @SuppressLint("SimpleDateFormat")
    fun dateProcessing() {
        val calendar = Calendar.getInstance()
        calendar.time = SimpleDateFormat("yyyy-MM-dd").parse(birth)

        // установка числа
        birth = SimpleDateFormat("d MMMM yyyy").format(calendar.time)
    }
}