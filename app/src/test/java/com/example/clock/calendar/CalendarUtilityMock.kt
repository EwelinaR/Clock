package com.example.clock.calendar

import java.util.GregorianCalendar

class CalendarUtilityMock(var hour: Int, var minute: Int) : GregorianCalendar() {

    override fun get(calendarType: Int): Int {
        return when (calendarType) {
            MINUTE -> minute
            HOUR_OF_DAY -> hour
            else -> super.get(calendarType)
        }
    }
}
