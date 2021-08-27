package com.example.clock

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.clock.calendar.CalendarUtility
import com.example.clock.calendar.CalendarUtilityInterface
import java.util.Calendar
import java.util.TimeZone
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
    }

    fun runClock() {
        val myTimer = Timer()
        myTimer.schedule(object : TimerTask() {
            override fun run() {
                updateTime()
            }
        }, 0, 1000*60) // TODO adjust delay
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

        calendar.updateTime()
    }
}
