package com.example.clock.calendar

import java.util.Calendar
import java.util.TimeZone

class CalendarUtility : CalendarUtilityInterface {

    override fun getHour(): Int {
        return getCalendarInstance().get(Calendar.HOUR_OF_DAY)
    }

    override fun getMinute(): Int {
        return getCalendarInstance().get(Calendar.MINUTE)
    }

    override fun getSecond(): Int {
        return getCalendarInstance().get(Calendar.SECOND)
    }

    private fun getCalendarInstance() : Calendar {
        return Calendar.getInstance(TimeZone.getTimeZone("GMT+2"))
    }
}
