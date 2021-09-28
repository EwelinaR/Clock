package com.example.clock

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.clock.calendar.CalendarExtension.getDate
import com.example.clock.calendar.CalendarExtension.getFullDate
import com.example.clock.calendar.CalendarExtension.getTensOfHour
import com.example.clock.calendar.CalendarExtension.getTensOfMinute
import com.example.clock.calendar.CalendarExtension.getUnitsOfHour
import com.example.clock.calendar.CalendarExtension.getUnitsOfMinute
import com.example.clock.calendar.CalendarInstance
import com.example.clock.weather.Weather
import com.example.clock.weather.WeatherApi
import com.example.clock.weather.WeatherObserver
import java.util.Calendar
import java.util.Timer
import java.util.TimerTask

class ClockViewModel(private var calendarInstance: CalendarInstance = CalendarInstance()) :
    ViewModel(), WeatherObserver {

    val date = MutableLiveData<String>()
    val tensOfHour = MutableLiveData<Int>()
    val unitsOfHour = MutableLiveData<Int>()
    val tensOfMinute = MutableLiveData<Int>()
    val unitsOfMinute = MutableLiveData<Int>()

    val temperature = MutableLiveData<Float>()
    val feelTemperature = MutableLiveData<Float>()
    val weatherIcon = MutableLiveData<String>()

    var lastSyncDate: String = ""
    val isWeatherUpToDate = MutableLiveData<Boolean>()

    init {
        val calendar = calendarInstance.getCalendarInstance()
        date.postValue(calendar.getDate())

        tensOfHour.postValue(calendar.getTensOfHour())
        unitsOfHour.postValue(calendar.getUnitsOfHour())

        tensOfMinute.postValue(calendar.getTensOfMinute())
        unitsOfMinute.postValue(calendar.getUnitsOfMinute())

        val delay = 60_000L - calendar.get(Calendar.SECOND) * 1000
        runClock(delay)
        rutWeatherUpdate()
    }

    private fun runClock(delay: Long) {
        val myTimer = Timer()
        myTimer.schedule(object : TimerTask() {
            override fun run() {
                updateTime()
            }
        }, delay, 60_000)   // 1min
    }

    private fun rutWeatherUpdate() {
        val myTimer = Timer()
        myTimer.schedule(object : TimerTask() {
            override fun run() {
                updateWeather()
            }
        }, 0, 60_000) // 0.5h
    }

    fun updateTime() {
        calendarInstance.getCalendarInstance().run {
            unitsOfMinute.postValue(getUnitsOfMinute())
            tensOfMinute.postValue(getTensOfMinute())
            unitsOfHour.postValue(getUnitsOfHour())
            tensOfHour.postValue(getTensOfHour())
        }
    }

    private fun updateWeather() {
        val api = WeatherApi(this)
        api.getWeather()
    }

    override fun updateWeatherValues(weather: Weather?) {
        weather?.let {
            temperature.postValue(it.temperature)
            feelTemperature.postValue(it.feelTemperature)
            weatherIcon.postValue(it.icon)
            isWeatherUpToDate.postValue(true)
            val calendar = calendarInstance.getCalendarInstance()
            lastSyncDate = calendar.getFullDate()
        } ?: isWeatherUpToDate.postValue(false)
    }
}
