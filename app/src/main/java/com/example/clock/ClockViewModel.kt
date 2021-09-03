package com.example.clock

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.clock.calendar.CalendarUtility
import com.example.clock.calendar.CalendarUtilityInterface
import com.example.clock.weather.Weather
import com.example.clock.weather.WeatherApi
import com.example.clock.weather.WeatherObserver
import java.util.Timer
import java.util.TimerTask

class ClockViewModel(calendarUtility: CalendarUtilityInterface = CalendarUtility()):
    ViewModel(), WeatherObserver {

    val tensOfHour = MutableLiveData<Int>()
    val unitsOfHour = MutableLiveData<Int>()
    val tensOfMinute = MutableLiveData<Int>()
    val unitsOfMinute = MutableLiveData<Int>()
    private val calendar = CalendarHelper(calendarUtility)

    val temperature = MutableLiveData<Float>()
    val feelTemperature = MutableLiveData<Float>()
    val weatherIcon = MutableLiveData<String>()

    init {
        tensOfHour.postValue(calendar.getTensOfHour())
        unitsOfHour.postValue(calendar.getUnitsOfHour())

        tensOfMinute.postValue(calendar.getTensOfMinute())
        unitsOfMinute.postValue(calendar.getUnitsOfMinute())

        val delay = 60_000L - calendarUtility.getSecond() * 1000
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
        }, 0, 3_000_000) // 0.5h
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

    private fun updateWeather() {
        val api = WeatherApi(this)
        api.getWeather()
    }

    override fun updateWeatherValues(weather: Weather) {
        weather.let {
            temperature.postValue(it.temperature)
            feelTemperature.postValue(it.feelTemperature)
            weatherIcon.postValue(it.icon)
        }
    }
}
