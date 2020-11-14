package com.depromeet.fragraph.core.ext

import android.annotation.SuppressLint
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

const val SEOUL = "Asia/Seoul"
const val LONDON = "Europe/London"

const val JUST_MINUTES = "mm"
const val JUST_SECONDS = "ss"
const val DF_ISO_8601 = "yyyy-MM-dd'T'HH:mm:ssXXX"
const val DF_SIMPLE_ISO_8601 = "yyyy-MM-dd'T'HH:mm:ss"
const val DF_NORMAL = "yyyy-MM-dd HH:mm:ss"
const val DF_ISO_8601_DATE = "yyyy-MM-dd"

// fragraph 에서 사용하는 format
//const val FRAGRAPH_REMAINING_TIME_FORMAT = "mm : ss"

fun Date.toDatetime(): String {
    return SimpleDateFormat(DF_NORMAL).format(this)
}

fun Date.toDatetimeISO8601(): String {
    return SimpleDateFormat(DF_ISO_8601).format(this)
}

fun String.toDatetimeSimpleISO8601(timezone: String = SEOUL): Date? {
    val dateformater: DateFormat = SimpleDateFormat(DF_SIMPLE_ISO_8601)
    dateformater.timeZone = TimeZone.getTimeZone(timezone)
    return dateformater.parse(this)
}

fun Int.miliSecondsToMinutes(timezone: String = SEOUL): String {
    val dateformater: DateFormat = SimpleDateFormat(JUST_MINUTES)
    dateformater.timeZone = TimeZone.getTimeZone(timezone)
    return dateformater.format(Date(this.toLong()))
}

fun Int.miliSecondsToSeconds(timezone: String = SEOUL): String {
    val dateformater: DateFormat = SimpleDateFormat(JUST_SECONDS)
    dateformater.timeZone = TimeZone.getTimeZone(timezone)
    return dateformater.format(Date(this.toLong()))
}