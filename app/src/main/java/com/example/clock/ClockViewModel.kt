package com.example.clock

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.clock.calendar.CalendarUtility
import com.example.clock.calendar.CalendarUtilityInterface
import java.util.Timer
import java.util.TimerTask

class ClockViewModel(calendarUtility: CalendarUtilityInterface = CalendarUtility()) : ViewModel() {
    val tensOfHour = MutableLiveData<Int>()
    val unitsOfHour = MutableLiveData<Int>()
    val tensOfMinute = MutableLiveData<Int>()
    val unitsOfMinute = MutableLiveData<Int>()
    private val calendar =  CalendarHelper(calendarUtility)

    init {
        tensOfHour.postValue(calendar.getTensOfHour())
        unitsOfHour.postValue(calendar.getUnitsOfHour())

        tensOfMinute.postValue(calendar.getTensOfMinute())
        unitsOfMinute.postValue(calendar.getUnitsOfMinute())

        val delay = 60_000L - calendarUtility.getSecond() * 1000
        runClock(delay)
    }

    private fun runClock(delay: Long) {
        val myTimer = Timer()
        myTimer.schedule(object : TimerTask() {
            override fun run() {
                updateTime()
            }
        }, delay, 60_000)
    }

    fun updateTime() {
        if (!calendar.isUnitsOfMinuteOverload()) {
            unitsOfMinute.postValue(calendar.getUnitsOfMinute())
            return
        }
        unitsOfMinute.postValue(0)
        if (!calendar.isTensOfMinuteOverload()) {
            tensOfMinute.postValue(calendar.getTensOfMinute())
            return
        }
        tensOfMinute.postValue(0)

        if (!calendar.isUnitsOfHourOverload()) {
            unitsOfHour.postValue(calendar.getUnitsOfHour())
            return
        }
        unitsOfHour.postValue(0)
        if (!calendar.isTensOfHourOverload()) {
            tensOfHour.postValue(calendar.getTensOfHour())
            return
        }
        tensOfHour.postValue(0)
    }
}
