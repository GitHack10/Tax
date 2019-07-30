package com.example.needtechnology.domain.global.models

import android.annotation.SuppressLint
import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.text.SimpleDateFormat
import java.util.*

@Parcelize
data class CheckInfoEntity(
    @SerializedName("document") val document: Document
) : Parcelable {

    @Parcelize
    data class Document(
        @SerializedName("receipt") val receipt: Receipt
    ) : Parcelable {

        @Parcelize
        @Entity(tableName = "check_list")
        data class Receipt(
            @PrimaryKey
            @ColumnInfo(name = "fd_number")
            @SerializedName("fiscalDocumentNumber") val fdNumber: String,

            @ColumnInfo(name = "total_sum")
            @SerializedName("ecashTotalSum") val totalSum: String,

            @ColumnInfo(name = "date")
            @SerializedName("dateTime") var dateTime: String

//            @ColumnInfo(name = "items")
//            @SerializedName("items") val items: List<Item>,

        ) : Parcelable {

//            @Parcelize
//            data class Item(
//                @ColumnInfo(name = "sum")
//                @SerializedName("sum") val sum: String,
//
//                @ColumnInfo(name = "price")
//                @SerializedName("price") val price: String,
//
//                @ColumnInfo(name = "quantity")
//                @SerializedName("quantity") val quantity: String,
//
//                @ColumnInfo(name = "name")
//                @SerializedName("name") val name: String
//            ) : Parcelable

            @SuppressLint("SimpleDateFormat")
            fun dateProcessing() {
                val calendar = Calendar.getInstance()
                calendar.time = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(dateTime)

                val hour =
                    if (calendar.get(Calendar.HOUR_OF_DAY).toString().length == 1) "0${calendar.get(Calendar.HOUR_OF_DAY)}"
                    else "${calendar.get(Calendar.HOUR_OF_DAY)}"

                val minute = if (calendar.get(Calendar.MINUTE).toString().length == 1) "0${calendar.get(Calendar.MINUTE)}"
                else "${calendar.get(Calendar.MINUTE)}"

                // установка числа
                val dayDiff = Calendar.getInstance().get(Calendar.DAY_OF_YEAR) - calendar.get(Calendar.DAY_OF_YEAR)
                dateTime = if (Calendar.getInstance().get(Calendar.YEAR) == calendar.get(Calendar.YEAR)) {
                    when (dayDiff) {
                        0 -> "Сегодня"
                        1 -> "Вчера"
                        else -> SimpleDateFormat("d MMMM").format(calendar.time)
                    }
                } else SimpleDateFormat("d MMMM yyyy").format(calendar.time)
            }
        }
    }
}