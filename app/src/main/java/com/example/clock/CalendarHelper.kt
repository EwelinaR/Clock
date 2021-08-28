package com.example.clock

import com.example.clock.calendar.CalendarUtilityInterface

class CalendarHelper(private val calendar: CalendarUtilityInterface) {

    fun getHour() : Int {
        return calendar.getHour()
    }

    fun getMinute() : Int {
        return calendar.getMinute()
    }

    fun getTensOfHour() : Int {
        return calendar.getHour() / 10
    }

    fun getUnitsOfHour() : Int {
        return calendar.getHour() % 10
    }

    fun getTensOfMinute() : Int {
        return calendar.getMinute() / 10
    }

    fun getUnitsOfMinute() : Int {
        return calendar.getMinute() % 10
    }

    fun isUnitsOfHourOverload() : Boolean {
        val actualHour = calendar.getHour()
        return actualHour % 10 == 0
    }

    fun isTensOfHourOverload() : Boolean {
        val actualHour = calendar.getHour()
        return actualHour == 0
    }

    fun isUnitsOfMinuteOverload() : Boolean {
        val actualMinute = calendar.getMinute()
        return actualMinute % 10 == 0
    }

    fun isTensOfMinuteOverload() : Boolean {
        val actualMinute = calendar.getMinute()
        return actualMinute == 0
    }
}
