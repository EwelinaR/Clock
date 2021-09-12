package com.example.clock.calendar

import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

class CalendarUtility : CalendarUtilityInterface {

    override fun getDate() : String {
        val calendar = getCalendarInstance()
        val dayOfWeek = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.US)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val month = calendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.US)

        return "$dayOfWeek, $month $day"
    }

    override fun getHour() : Int {
        return getCalendarInstance().get(Calendar.HOUR_OF_DAY)
    }

    override fun getMinute() : Int {
        return getCalendarInstance().get(Calendar.MINUTE)
    }

    override fun getSecond() : Int {
        return getCalendarInstance().get(Calendar.SECOND)
    }

    private fun getCalendarInstance() : Calendar {
        return Calendar.getInstance(TimeZone.getTimeZone("GMT+2"))
    }
}
