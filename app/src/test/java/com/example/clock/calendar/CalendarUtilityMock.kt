package com.example.clock.calendar

class CalendarUtilityMock(private var hour: Int, private var minute: Int): CalendarUtilityInterface {

    override fun getHour(): Int {
        return hour
    }

    override fun getMinute(): Int {
        return minute
    }

    fun setHour(hour: Int) {
        this.hour = hour
    }

    fun setMinute(minute: Int) {
        this.minute = minute
    }
}
