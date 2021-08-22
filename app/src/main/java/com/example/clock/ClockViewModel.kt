package com.example.clock

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*

class ClockViewModel : ViewModel() {
    val tensOfHour = MutableLiveData<Int>()
    val unitsOfHour = MutableLiveData<Int>()
    val tensOfMinutes = MutableLiveData<Int>()
    val unitsOfMinutes = MutableLiveData<Int>()
    private val calendar =  CalendarHelper(Calendar.getInstance(TimeZone.getTimeZone("GMT+2")))

    init {
        tensOfHour.value = calendar.getTensOfHour()
        unitsOfHour.value = calendar.getUnitsOfHour()

        tensOfMinutes.value = calendar.getTensOfMinute()
        unitsOfMinutes.value = calendar.getUnitsOfMinute()
    }
}