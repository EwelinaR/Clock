package com.example.clock.calendar

import java.util.Calendar
import java.util.TimeZone
import javax.inject.Inject

open class CalendarInstance @Inject constructor() {

    open fun getCalendarInstance(): Calendar {
        return Calendar.getInstance(TimeZone.getTimeZone("GMT+2"))
    }
}
