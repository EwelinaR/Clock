package com.example.clock.calendar

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

object CalendarExtension {

    fun Calendar.getDate(): String {
        return SimpleDateFormat("EEEE, MMM dd", Locale.ENGLISH).apply {
            timeZone = TimeZone.getTimeZone("GMT+2")
        }.format(time)
    }

    fun Calendar.getFullDate(): String {
        return SimpleDateFormat("HH:mm dd-MM-yyyy", Locale.ENGLISH).apply {
            timeZone = TimeZone.getTimeZone("GMT+2")
        }.format(time)
    }

    fun Calendar.getTensOfHour(): Int {
        return get(Calendar.HOUR_OF_DAY) / 10
    }

    fun Calendar.getUnitsOfHour(): Int {
        return get(Calendar.HOUR_OF_DAY) % 10
    }

    fun Calendar.getTensOfMinute(): Int {
        return get(Calendar.MINUTE) / 10
    }

    fun Calendar.getUnitsOfMinute(): Int {
        return get(Calendar.MINUTE) % 10
    }

    fun Calendar.isUnitsOfHourOverload(): Boolean {
        return get(Calendar.HOUR_OF_DAY) % 10 == 0
    }

    fun Calendar.isTensOfHourOverload(): Boolean {
        return get(Calendar.HOUR_OF_DAY) == 0
    }

    fun Calendar.isUnitsOfMinuteOverload(): Boolean {
        return get(Calendar.MINUTE) % 10 == 0
    }

    fun Calendar.isTensOfMinuteOverload(): Boolean {
        return get(Calendar.MINUTE) == 0
    }
}
