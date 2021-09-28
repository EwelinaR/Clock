package com.example.clock.calendar

import java.util.Calendar
import java.util.TimeZone

open class CalendarInstance {

    open fun getCalendarInstance(): Calendar {
        return Calendar.getInstance(TimeZone.getTimeZone("GMT+2"))
    }
}
