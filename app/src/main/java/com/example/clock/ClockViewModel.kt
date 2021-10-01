package com.example.clock

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clock.calendar.CalendarExtension.getDate
import com.example.clock.calendar.CalendarExtension.getFullDate
import com.example.clock.calendar.CalendarExtension.getTensOfHour
import com.example.clock.calendar.CalendarExtension.getTensOfMinute
import com.example.clock.calendar.CalendarExtension.getUnitsOfHour
import com.example.clock.calendar.CalendarExtension.getUnitsOfMinute
import com.example.clock.calendar.CalendarInstance
import com.example.clock.weather.Response
import com.example.clock.weather.Weather
import com.example.clock.weather.WeatherApi
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Timer
import java.util.TimerTask
import javax.inject.Inject

class ClockViewModel @Inject constructor(private val calendarInstance: CalendarInstance,
                                         private val weatherApi: WeatherApi) : ViewModel() {

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
        Timer().schedule(object : TimerTask() {
            override fun run() {
                updateTime()
            }
        }, delay, 60_000)   // 1min
    }

    private fun rutWeatherUpdate() {
        Timer().schedule(object : TimerTask() {
            override fun run() {
                updateWeather()
            }
        }, 0, 3_000_000) // 0.5h
    }

    fun updateTime() {
        calendarInstance.getCalendarInstance().run {
            unitsOfMinute.postValue(getUnitsOfMinute())
            tensOfMinute.postValue(getTensOfMinute())
            unitsOfHour.postValue(getUnitsOfHour())
            tensOfHour.postValue(getTensOfHour())
        }
    }

    internal fun updateWeather() {
        viewModelScope.launch {
            when (val response = weatherApi.getWeather()) {
                is Response.Success<Weather> -> updateWeatherValues(response.data)
                is Response.Failure<Weather> -> {
                    isWeatherUpToDate.value = false
                    Log.e("API", response.errorMessage)
                }
            }
        }
    }

    private fun updateWeatherValues(weather: Weather) {
        weather.let {
            val calendar = calendarInstance.getCalendarInstance()
            lastSyncDate = calendar.getFullDate()

            temperature.value = it.temperature
            feelTemperature.value = it.feelTemperature
            weatherIcon.value = it.icon
            isWeatherUpToDate.value = true
        }
    }
}
