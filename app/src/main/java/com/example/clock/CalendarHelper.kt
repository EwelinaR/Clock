package com.example.clock

import java.util.*

class CalendarHelper(calendar: Calendar) {
    private var minute: Int = calendar.get(Calendar.MINUTE)
    private var hour: Int = calendar.get(Calendar.HOUR_OF_DAY)

    fun getTensOfHour() : Int {
        return hour / 10
    }

    fun getUnitsOfHour() : Int {
        return hour % 10
    }

    fun getTensOfMinute() : Int {
        return minute / 10
    }

    fun getUnitsOfMinute() : Int {
        return minute % 10
    }
}