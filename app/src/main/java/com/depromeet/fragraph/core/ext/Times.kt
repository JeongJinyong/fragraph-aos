package com.depromeet.fragraph.core.ext

import timber.log.Timber
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

const val SEOUL = "Asia/Seoul"
const val LONDON = "Europe/London"

const val JUST_YEAR = "yyyy"
const val JUST_MONTH = "MM"
const val JUST_DATE = "dd"
const val JUST_MINUTES = "mm"
const val JUST_SECONDS = "ss"
const val JUST_DAY = "E"

const val YEAR_MONTH = "yyyy-MM"
const val YEAR_MONTH_DATE = "yyyy-MM-dd"

const val DF_ISO_8601 = "yyyy-MM-dd'T'HH:mm:ssXXX"
const val DF_SIMPLE_ISO_8601 = "yyyy-MM-dd'T'HH:mm:ss"
const val DF_NORMAL = "yyyy-MM-dd HH:mm:ss"

// fragraph 에서 사용하는 format
const val FRAGRAPH_HISTORY_FORMAT = "yyyy. MM. dd"
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

fun Long.miliSecondsToStringFormat(format: String, timezone: String = SEOUL): String {
    val dateformater: DateFormat = SimpleDateFormat(format)
    dateformater.timeZone = TimeZone.getTimeZone(timezone)
    return dateformater.format(Date(this))
}

fun Long.miliSecondsToMonth(timezone: String = SEOUL): String {
    val dateformater: DateFormat = SimpleDateFormat(JUST_MONTH)
    dateformater.timeZone = TimeZone.getTimeZone(timezone)
    return dateformater.format(Date(this))
}

fun Long.miliSecondsToDay(timezone: String = SEOUL): String {
    val dateformater: DateFormat = SimpleDateFormat(JUST_DATE)
    dateformater.timeZone = TimeZone.getTimeZone(timezone)
    return dateformater.format(Date(this))
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

fun getLastDayOfMonth(year: Int, month: Int, day: Int, timezone: String = SEOUL): Int {
    val cal = Calendar.getInstance()
    cal.set(year, month - 1, day)
    return cal.getActualMaximum(Calendar.DAY_OF_MONTH)
}

fun getMiliSeconds(year: Int, month: Int, day: Int, timezone: String = SEOUL): Long {
    val dateformater: DateFormat = SimpleDateFormat(YEAR_MONTH_DATE)
    dateformater.timeZone = TimeZone.getTimeZone(timezone)
    val curMonth = if (month / 10 == 0) { "0${month}" } else { "${month}" }
    val curDate = if (day / 10 == 0) { "0${day}" } else { "${day}" }
    return dateformater.parse("$year-$curMonth-$curDate").time
}