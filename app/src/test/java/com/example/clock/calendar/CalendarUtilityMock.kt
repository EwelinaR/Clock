package com.example.clock.calendar

import java.util.GregorianCalendar

class CalendarUtilityMock(private var hour: Int, private var minute: Int) : GregorianCalendar() {

    override fun get(calendarType: Int): Int {
        return when (calendarType) {
            MINUTE -> minute
            HOUR_OF_DAY -> hour
            else -> super.get(calendarType)
        }
    }

    fun setHour(hour: Int) {
        this.hour = hour
    }

    fun setMinute(minute: Int) {
        this.minute = minute
    }
}
