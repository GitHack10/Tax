package ru.dagdelo.business05.presentation.global.utils

import org.threeten.bp.format.DateTimeFormatter

fun toUserDate(date: String): String {
    val accessor = DateTimeFormatter.ofPattern("yyyy-MM-dd").parse(date)
    return DateTimeFormatter.ofPattern("d MMMM yyyy").format(accessor)
}

fun toUserRegDate(date: String): String {
    val accessor = DateTimeFormatter.ofPattern("d MMMM yyyy").parse(date)
    return DateTimeFormatter.ofPattern("dd MM yyyy").format(accessor)
}

fun toHumanDate(date: String): String {
    val accessor = if (date.length > 13) {
        DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss").parse(date)
    } else {
        DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmm").parse(date)
    }
    return DateTimeFormatter.ofPattern("dd.MM.yyyy' 'HH:mm:ss").format(accessor)
}

fun toSendFormatCheckDate(date: String): String {
    val accessor = DateTimeFormatter.ofPattern("dd.MM.yyyy' 'HH:mm:ss").parse(date)
    return DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss").format(accessor)
}

fun fromCalendarToUserRegDate(dayOfMonth: Int, month: Int, year: Int): String {
    val date = "$dayOfMonth.$month.$year"
    val accessor = DateTimeFormatter.ofPattern("d.M.yyyy").parse(date)
    return DateTimeFormatter.ofPattern("d MMMM yyyy").format(accessor)
}

fun fromCalendarToUserRegSendDate(dayOfMonth: Int, month: Int, year: Int): String {
    val date = "$dayOfMonth.$month.$year"
    val accessor = DateTimeFormatter.ofPattern("d.M.yyyy").parse(date)
    return DateTimeFormatter.ofPattern("dd-MM-yyyy").format(accessor)
}

fun fromCalendarDate(dayOfMonth: Int, month: Int, year: Int, hours: Int, minutes: Int): String {
    val date = "$dayOfMonth.$month.$year $hours:$minutes"
    val accessor = DateTimeFormatter.ofPattern("d.M.yyyy' 'H:m").parse(date)
    return DateTimeFormatter.ofPattern("dd.MM.yyyy' 'HH:mm:ss").format(accessor)
}