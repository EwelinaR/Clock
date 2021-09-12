package com.example.clock.calendar

class CalendarUtilityMock(private var hour: Int, private var minute: Int, private var date: String = ""):
    CalendarUtilityInterface {

    override fun getDate() : String {
        return  date
    }

    override fun getHour() : Int {
        return hour
    }

    override fun getMinute() : Int {
        return minute
    }

    override fun getSecond() : Int {
        return 0
    }

    fun setDate(date: String) {
        this.date = date
    }

    fun setHour(hour: Int) {
        this.hour = hour
    }

    fun setMinute(minute: Int) {
        this.minute = minute
    }
}
